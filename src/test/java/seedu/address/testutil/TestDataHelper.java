package seedu.address.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;

public class TestDataHelper {
    private Model model;

    public TestDataHelper(Model model) {
        this.model = model;
    }

    public Person adam() throws Exception {
        Name name = new Name("Adam Brown");
        Phone privatePhone = new Phone("111111");
        Email email = new Email("adam@example.com");
        Address privateAddress = new Address("111, alpha street");

        return new Person(name, privatePhone, email, privateAddress,
                getTagSet("tag1", "longertag2"));
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

    /**
     * Parses the given {@code userInput} into a {@code Command}, confirms that
     * execution of command failed.
     * @see #assertCommandFailure(Command, String)
     */
    public void assertCommandFailure(String userInput, String expectedMessage) {
        assertCommandFailure(prepareCommand(userInput), expectedMessage);
    }
}
