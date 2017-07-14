package systemtests;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.testfx.api.FxToolkit;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.StatusBarFooter;

/**
 * A system test class for AddressBook, which sets up the {@code TestApp}, provides access
 * to handles of GUI components and other tools for system testing purposes.
 */
public abstract class AddressBookSystemTest {
    public static final Clock INJECTED_CLOCK = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private static final Clock ORIGINAL_CLOCK = StatusBarFooter.getClock();

    protected Stage stage;

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;

    @BeforeClass
    public static void setupUpBeforeClass() {
        initializeFxToolkit();
        injectFixedClock();
    }

    private static void initializeFxToolkit() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    private static void injectFixedClock() {
        StatusBarFooter.setClock(INJECTED_CLOCK);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        restoreOriginalClock();
    }

    private static void restoreOriginalClock() {
        StatusBarFooter.setClock(ORIGINAL_CLOCK);
    }

    @Before
    public void setUp() throws Exception {
        setupStage();

        mainWindowHandle = new MainWindowHandle(stage);
        mainWindowHandle.focus();

        preconditionCheck(getTypicalAddressBook());
    }

    private void setupStage() throws TimeoutException {
        FxToolkit.setupStage((stage) -> {
            this.stage = stage;
        });
        FxToolkit.setupApplication(() -> testApp = new TestApp(TypicalPersons::getTypicalAddressBook,
                getDataFileLocation()));
        FxToolkit.showStage();
    }

    @After
    public void tearDown() throws Exception {
        EventsCenter.clearSubscribers();
        FxToolkit.cleanupStages();
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public PersonListPanelHandle getPersonListPanel() {
        return mainWindowHandle.getPersonListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Runs {@code command} in the application's {@code CommandBox}.
     */
    public void runCommand(String command) {
        mainWindowHandle.getCommandBox().run(command);
    }

    private String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    private URL getDefaultBrowserUrl() {
        return MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
    }

    public TestApp getTestApp() {
        return testApp;
    }

    /**
     * Checks that the starting state of the application is correct.
     */
    private void preconditionCheck(ReadOnlyAddressBook expectedAddressBook) {
        try {
            assert getCommandBox().getInput().equals("");
            assert getResultDisplay().getText().equals("");
            assert getPersonListPanel().isListMatching(
                    expectedAddressBook.getPersonList().toArray(new ReadOnlyPerson[0]));
            assert getBrowserPanel().getLoadedUrl().equals(getDefaultBrowserUrl());
            assert getStatusBarFooter().getSaveLocation().equals("./" + testApp.getStorageSaveLocation());
        } catch (Exception e) {
            throw new AssertionError("Starting state is wrong.", e);
        }
    }
}
