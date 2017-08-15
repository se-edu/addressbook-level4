package systemtests;

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
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.StatusBarFooter;

/**
 * A system test class for AddressBook, which sets up the {@code TestApp}, provides access
 * to handles of GUI components, provides clock injection and verifies that the starting state of the
 * application is correct.
 */
public abstract class AddressBookSystemTest {
    public static final Clock INJECTED_CLOCK = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private static final Clock ORIGINAL_CLOCK = StatusBarFooter.getClock();

    protected AppStateAsserts asserts;

    private Stage stage;

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;

    @BeforeClass
    public static void setupUpBeforeClass() {
        initializeFxToolkit();

        // provides us a way to predict expected time more easily, to prevent scenarios whereby
        // a 1-second delay causes the verification to be wrong
        StatusBarFooter.setClock(INJECTED_CLOCK);
    }

    private static void initializeFxToolkit() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // restore original clock
        StatusBarFooter.setClock(ORIGINAL_CLOCK);
    }

    @Before
    public void setUp() {
        setupApplication();

        mainWindowHandle = new MainWindowHandle(stage);
        mainWindowHandle.focus();

        asserts = new AppStateAsserts(this);
        asserts.assertApplicationStartingStateIsCorrect();
    }

    private void setupApplication() {
        try {
            FxToolkit.setupStage((stage) -> {
                this.stage = stage;
            });
            FxToolkit.setupApplication(() -> testApp = new TestApp(TypicalPersons::getTypicalAddressBook,
                    TestApp.SAVE_LOCATION_FOR_TESTING));
            FxToolkit.showStage();
        } catch (TimeoutException te) {
            throw new AssertionError("Application takes too long to set up.");
        }
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

    /**
     * Calls {@code BrowserPanelHandle}, {@code PersonListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    protected void rememberStates() throws Exception {
        getBrowserPanel().rememberUrl();
        getStatusBarFooter().rememberSaveLocation();
        getStatusBarFooter().rememberSyncStatus();
        getPersonListPanel().rememberSelectedPersonCard();
    }

    public TestApp getTestApp() {
        return testApp;
    }
}
