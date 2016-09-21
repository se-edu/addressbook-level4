package seedu.address.controller;

import com.google.common.eventbus.Subscribe;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import seedu.address.events.EventManager;
import seedu.address.events.model.LocalModelChangedEvent;
import seedu.address.model.*;
import seedu.address.model.datatypes.*;
import seedu.address.commands.*;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.parser.Parser;

import java.util.Collections;
import java.util.List;

import static seedu.address.commons.Messages.*;
import static org.junit.Assert.*;

public class CommandBoxTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private ModelManager model;
    private ResultDisplay resultDisplay;
    private CommandBox inputBox;

    private ReadOnlyAddressBook latestSavedAddressBook;

    @Subscribe
    private void handleLocalModelChangedEvent(LocalModelChangedEvent lmce) {
        latestSavedAddressBook = new AddressBook(lmce.data);
    }

    @Before
    public void setup() {
        model = new ModelManager(null); // ignore config
        resultDisplay = new ResultDisplay();
        inputBox = new CommandBox();
        inputBox.configure(new Parser(), resultDisplay, model);
        EventManager.getInstance().registerHandler(this);
        latestSavedAddressBook = new AddressBook(model.getAddressBook()); // last saved assumed to be up to date before.
    }

    @After
    public void teardown() {
        EventManager.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, AddressBook, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new AddressBook(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand,
                                       String expectedMessage,
                                       AddressBook expectedAddressBook,
                                       List<? extends ReadOnlyPerson> shownList) throws Exception {

        //Execute the command
        inputBox.processCommandInput(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, resultDisplay.getDisplayed());
        assertEquals(shownList, model.getFilteredPersonList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedAddressBook, latestSavedAddressBook);
    }

}
