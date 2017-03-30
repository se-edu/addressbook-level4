package seedu.address.logic.commands;

import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestPerson;

public class EditCommandIntegrationTest extends CommandIntegrationTest {

    @Test
    public void execute_validCommand_succeeds() throws Exception {
        int addressBookIndex = 1;
        String userInput = "edit " + addressBookIndex + " Bobby p/91234567 e/bobby@example.com " +
                           "a/Block 123, Bobby Street 3 t/husband";
        Command command = prepareCommand(userInput);

        TestPerson editedPerson = new PersonBuilder().withName("Bobby")
                                                     .withPhone("91234567")
                                                     .withEmail("bobby@example.com")
                                                     .withAddress("Block 123, Bobby Street 3")
                                                     .withTags("husband")
                                                     .build();
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        expectedAddressBook.updatePerson(addressBookIndex - 1, editedPerson);

        List<ReadOnlyPerson> expectedShownList = expectedAddressBook.getPersonList();

        assertCommandSuccess(command, expectedMessage, expectedAddressBook, expectedShownList);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        String userInput = "edit 3 Alice Pauline p/85355255 e/alice@example.com " +
                           "a/123, Jurong West Ave 6, #08-111 t/friends";
        Command command = prepareCommand(userInput);
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        String userInput = "edit 8 Bobby";
        Command command = prepareCommand(userInput);
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

}
