package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

/**
 * A base test class for all command integration tests.
 */
public abstract class CommandIntegrationTest {
    protected Model model;

    @Before
    public void setup() throws Exception {
        model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
    }

    protected Command prepareCommand(String userInput) {
        Command command = new Parser().parseCommand(userInput);
        command.setData(model);
        return command;
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the address book in the model matches {@code expectedAddressBook} <br>
     * - the filtered person list in the model matches {@code expectedFilteredList} <br>
     */
    protected void assertCommandSuccess(Command command, String expectedMessage,
            ReadOnlyAddressBook expectedAddressBook,
            List<? extends ReadOnlyPerson> expectedFilteredList) throws CommandException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedFilteredList, model.getFilteredPersonList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book in the model remains unchanged <br>
     * - the filtered person list in the model remains unchanged <br>
     */
    protected void assertCommandFailure(Command command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(model.getFilteredPersonList());
        try {
            command.execute();
            fail("expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, model.getAddressBook());
            assertEquals(expectedFilteredList, model.getFilteredPersonList());
        }
    }
}
