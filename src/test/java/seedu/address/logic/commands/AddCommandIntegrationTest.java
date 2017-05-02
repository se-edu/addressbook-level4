package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TypicalPersons;

/**
 * An integration test class that tests add command's interaction with the Model.
 */
public class AddCommandIntegrationTest {

    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
    private Parser parser = new Parser();


    @Test
    public void execute_validCommand_succeeds() throws Exception {
        Person toAdd = adam();

        String userInput = PersonUtil.getAddCommand(toAdd);
        Command command = prepareCommand(userInput);

        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.addPerson(toAdd);

        assertCommandSuccess(command, expectedMessage, expectedAddressBook, expectedAddressBook.getPersonList());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Person toAdd = adam();

        model.addPerson(toAdd); // person already in internal address book

        String userInput = PersonUtil.getAddCommand(toAdd);
        Command command = prepareCommand(userInput);

        // execute command and verify result
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    private Person adam() throws Exception {
        Name name = new Name("Adam Brown");
        Phone privatePhone = new Phone("111111");
        Email email = new Email("adam@example.com");
        Address privateAddress = new Address("111, alpha street");

        return new Person(name, privatePhone, email, privateAddress,
                getTagSet("tag1", "longertag2"));
    }

    private Command prepareCommand(String userInput) {
        Command command = parser.parseCommand(userInput);
        command.setData(model);
        return command;
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the address book in the model matches {@code expectedAddressBook} <br>
     * - the filtered person list in the model matches {@code expectedFilteredList} <br>
     */
    private void assertCommandSuccess(Command command, String expectedMessage,
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
    private void assertCommandFailure(Command command, String expectedMessage) {
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
