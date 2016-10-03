package guitests;

import guitests.guihandles.*;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.model.AddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestPersons;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A GUI Test class for AddressBook.
 */
public abstract class AddressBookGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestPersons td = new TypicalTestPersons();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected PersonListPanelHandle personListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
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
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected AddressBook getInitialData() {
        AddressBook ab = TestUtil.generateEmptyAddressBook();
        TypicalTestPersons.loadAddressBookWithSampleData(ab);
        return ab;
    }

    /**
     * Override this in child classes to set the data file location.
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Asserts the person shown in the card is same as the given person
     */
    public void assertMatching(ReadOnlyPerson person, PersonCardHandle card) {
        assertTrue(TestUtil.compareCardAndPerson(card, person));
    }

    /**
     * Asserts the size of the person list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }
}
