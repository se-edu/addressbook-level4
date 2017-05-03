package seedu.address.logic.commands;

import org.junit.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonUtil;

/**
 * An integration test class that tests add command's interaction with the Model.
 */
public class AddCommandIntegrationTest extends CommandIntegrationTest {

    @Test
    public void execute_validCommand_succeeds() throws Exception {
        Person toAdd = new PersonUtil().adam();

        String userInput = PersonUtil.getAddCommand(toAdd);
        Command command = prepareCommand(userInput);

        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.addPerson(toAdd);

        assertCommandSuccess(command, expectedMessage, expectedAddressBook,
                expectedAddressBook.getPersonList());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Person toAdd = new PersonUtil().adam();

        model.addPerson(toAdd); // person already in internal address book

        String userInput = PersonUtil.getAddCommand(toAdd);
        Command command = prepareCommand(userInput);

        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonData_throwsCommandException() throws Exception {
        Command command = prepareCommand("add []\\[;] p/12345 e/valid@e.mail a/valid, address");

        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
