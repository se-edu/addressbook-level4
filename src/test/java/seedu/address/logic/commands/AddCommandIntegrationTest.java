package seedu.address.logic.commands;

import org.junit.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.IntegrationTestUtil;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TypicalPersons;

/**
 * An integration test class that tests add command's interaction with the Model.
 */
public class AddCommandIntegrationTest {

    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
    private IntegrationTestUtil testUtil = new IntegrationTestUtil(model);

    @Test
    public void execute_validCommand_succeeds() throws Exception {
        Person toAdd = new PersonUtil().adam();

        String userInput = PersonUtil.getAddCommand(toAdd);
        Command command = testUtil.prepareCommand(userInput);

        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.addPerson(toAdd);

        testUtil.assertCommandSuccess(command, expectedMessage, expectedAddressBook,
                expectedAddressBook.getPersonList());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Person toAdd = new PersonUtil().adam();

        model.addPerson(toAdd); // person already in internal address book

        String userInput = PersonUtil.getAddCommand(toAdd);
        Command command = testUtil.prepareCommand(userInput);

        testUtil.assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }
}
