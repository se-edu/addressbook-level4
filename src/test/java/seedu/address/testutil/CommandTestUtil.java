package seedu.address.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Utility class for command tests.
 */
public class CommandTestUtil {
    private Model model;

    public CommandTestUtil(Model model) {
        this.model = model;
    }

    public Command prepareCommand(String userInput) {
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
    public void assertCommandSuccess(Command command, String expectedMessage,
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
    public void assertCommandFailure(Command command, String expectedMessage) {
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
