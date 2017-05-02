package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonUtil;

/**
 * An integration test class that tests edit command's interaction with the Model.
 */
public class EditCommandIntegrationTest extends CommandIntegrationTest {

    private static final int ZERO_BASED_INDEX_FIRST_PERSON = 0;
    private static final int ZERO_BASED_INDEX_SECOND_PERSON =  ZERO_BASED_INDEX_FIRST_PERSON + 1;

    @Test
    public void execute_validCommand_succeeds() throws Exception {
        Person editedPerson = new PersonUtil().adam();

        String userInput = PersonUtil.getEditCommand(ZERO_BASED_INDEX_FIRST_PERSON, editedPerson);
        Command command = prepareCommand(userInput);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.updatePerson(ZERO_BASED_INDEX_FIRST_PERSON, editedPerson);

        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(model.getFilteredPersonList());

        assertCommandSuccess(command, expectedMessage, expectedAddressBook, expectedFilteredList);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person firstPerson = new Person(model.getFilteredPersonList().get(ZERO_BASED_INDEX_FIRST_PERSON));
        String userInput = PersonUtil.getEditCommand(ZERO_BASED_INDEX_SECOND_PERSON, firstPerson);
        Command command = prepareCommand(userInput);
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        int oneBasedOutOfBoundIndex = model.getFilteredPersonList().size() + 1;
        String userInput = "edit " + oneBasedOutOfBoundIndex + " Bobby";
        Command command = prepareCommand(userInput);
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
