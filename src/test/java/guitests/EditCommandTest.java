package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_THIRD_PERSON;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends AddressBookGuiTest {

    // The list of persons in the person list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    private Person[] expectedPersonsList = td.getTypicalPersons();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = PREFIX_NAME + "Bobby " + PREFIX_PHONE + "91234567 "
                + PREFIX_EMAIL + "bobby@example.com "
                + PREFIX_ADDRESS + "Block 123, Bobby Street 3 "
                + PREFIX_TAG + "husband";
        Index addressBookIndex = INDEX_FIRST_PERSON;

        Person editedPerson = new PersonBuilder().withName("Bobby").withPhone("91234567")
                .withEmail("bobby@example.com").withAddress("Block 123, Bobby Street 3").withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = PREFIX_TAG + "sweetie "
                + PREFIX_TAG + "bestie";
        Index addressBookIndex = INDEX_SECOND_PERSON;

        Person personToEdit = expectedPersonsList[addressBookIndex.getZeroBased()];
        Person editedPerson = new PersonBuilder(personToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = PREFIX_TAG.getPrefix();
        Index addressBookIndex = INDEX_SECOND_PERSON;

        Person personToEdit = expectedPersonsList[addressBookIndex.getZeroBased()];
        Person editedPerson = new PersonBuilder(personToEdit).withTags().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand(FindCommand.COMMAND_WORD + " Carl");

        String detailsToEdit = PREFIX_NAME + "Carrle";
        Index addressBookIndex = INDEX_THIRD_PERSON;

        Person personToEdit = expectedPersonsList[addressBookIndex.getZeroBased()];
        Person editedPerson = new PersonBuilder(personToEdit).withName("Carrle").build();

        assertEditSuccess(INDEX_FIRST_PERSON, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_missingPersonIndex_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " " + PREFIX_NAME + "Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidPersonIndex_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " 8 " + PREFIX_NAME + "Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " 1 " + PREFIX_NAME + "*&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand(EditCommand.COMMAND_WORD + " 1 " + PREFIX_PHONE + "abcd");
        assertResultMessage(Phone.MESSAGE_PHONE_CONSTRAINTS);

        commandBox.runCommand(EditCommand.COMMAND_WORD + " 1 " + PREFIX_EMAIL + "yahoo!!!");
        assertResultMessage(Email.MESSAGE_EMAIL_CONSTRAINTS);

        commandBox.runCommand(EditCommand.COMMAND_WORD + " 1 " + PREFIX_ADDRESS.getPrefix());
        assertResultMessage(Address.MESSAGE_ADDRESS_CONSTRAINTS);

        commandBox.runCommand(EditCommand.COMMAND_WORD + " 1 " + PREFIX_TAG + "*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicatePerson_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " 3 "
                + PREFIX_PHONE + "85355255 "
                + PREFIX_EMAIL + "alice@example.com "
                + PREFIX_NAME + "Alice Pauline "
                + PREFIX_ADDRESS + "123, Jurong West Ave 6, #08-111 "
                + PREFIX_TAG + "friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Checks whether the edited person has the correct updated details.
     *
     * @param filteredPersonListIndex index of person to edit in filtered list
     * @param addressBookIndex index of person to edit in the address book.
     *      Must refer to the same person as {@code filteredPersonListIndex}
     * @param detailsToEdit details to edit the person with as input to the edit command
     * @param editedPerson the expected person after editing the person's details
     */
    private void assertEditSuccess(Index filteredPersonListIndex, Index addressBookIndex,
                                    String detailsToEdit, Person editedPerson) {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " "
                + filteredPersonListIndex.getOneBased() + " " + detailsToEdit);

        // confirm the new card contains the right data
        PersonCardHandle editedCard = personListPanel.navigateToPerson(editedPerson.getName().fullName);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        expectedPersonsList[addressBookIndex.getZeroBased()] = editedPerson;
        assertTrue(personListPanel.isListMatching(expectedPersonsList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }
}
