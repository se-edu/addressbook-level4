package guitests;

import seedu.address.TestApp;
import seedu.address.events.EventManager;
import seedu.address.model.datatypes.AddressBook;
import seedu.address.model.datatypes.person.Person;
import seedu.address.testutil.ScreenShotRule;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestData;
import seedu.address.util.Config;
import guitests.guihandles.*;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertTrue;

public class GuiTestBase {

    @Rule
    public ScreenShotRule screenShotRule = new ScreenShotRule();

    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    /* Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected PersonListPanelHandle personListPanel;
    protected HeaderStatusBarHandle statusBar;
    protected TypicalTestData td = new TypicalTestData();
    private Stage stage;


    @BeforeClass
    public static void setupSpec() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.setupStage((stage) -> {
            mainGui = new MainGuiHandle(new GuiRobot(), stage);
            mainMenu = mainGui.getMainMenu();
            personListPanel = mainGui.getPersonListPanel();
            statusBar = mainGui.getStatusBar();
            this.stage = stage;
        });
        EventManager.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while(!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected AddressBook getInitialData() {
        return TestUtil.generateSampleAddressBook();
    }

    /**
     * Override this in child classes to set the data file location.
     * @return
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public Config getTestingConfig() {
        return testApp.getTestingConfig();
    }

    @After
    public void cleanup() throws TimeoutException {
        File file = GuiTest.captureScreenshot();
        TestUtil.renameFile(file, this.getClass().getName() + name.getMethodName() + ".png");
        FxToolkit.cleanupStages();
    }

    public void sleep(long duration, TimeUnit timeunit) {
        mainGui.sleep(duration, timeunit);
    }

    public void sleepForGracePeriod() {
        mainGui.sleepForGracePeriod();
    }

    public void sleepUntilNextSync() {
        //TODO: actively check for sync status rather than sleep for a fixed time
        //sleep(getTestingConfig().getUpdateInterval(), TimeUnit.MILLISECONDS);
    }

    public void assertMatching(PersonCardHandle card, Person person) {
        assertTrue(TestUtil.compareCardAndPerson(card, person));
    }

}
