package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.commons.util.IndexUtil;
import seedu.address.logic.commands.EditCommand;
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
        String detailsToEdit = "Bobby p/91234567 e/bobby@example.com a/Block 123, Bobby Street 3 t/husband";
        int addressBookIndex = 1;

        Person editedPerson = new PersonBuilder().withName("Bobby").withPhone("91234567")
                .withEmail("bobby@example.com").withAddress("Block 123, Bobby Street 3").withTags("husband").build();

        assertEditSuccessNoWarning(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int addressBookIndex = 2;

        Person personToEdit = expectedPersonsList[IndexUtil.oneToZeroIndex(addressBookIndex)];
        Person editedPerson = new PersonBuilder(personToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccessNoWarning(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int addressBookIndex = 2;

        Person personToEdit = expectedPersonsList[IndexUtil.oneToZeroIndex(addressBookIndex)];
        Person editedPerson = new PersonBuilder(personToEdit).withTags().build();

        assertEditSuccessNoWarning(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Elle");

        String detailsToEdit = "Belle";
        int filteredPersonListIndex = 1;
        int addressBookIndex = 5;

        Person personToEdit = expectedPersonsList[IndexUtil.oneToZeroIndex(addressBookIndex)];
        Person editedPerson = new PersonBuilder(personToEdit).withName("Belle").build();

        assertEditSuccessNoWarning(filteredPersonListIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_multipleValuesOneField_success() throws Exception {
        String phoneNumber = "92139131";
        String detailsToEdit = "p/911 p/" + phoneNumber;
        int addressBookIndex = 2;

        Person personToEdit = expectedPersonsList[IndexUtil.oneToZeroIndex(addressBookIndex)];
        Person editedPerson = new PersonBuilder(personToEdit).withPhone(phoneNumber).build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson,
                String.format(EditCommand.MESSAGE_MULTIPLE_VALUES_WARNING, "Phones", "Phone")
                        + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    @Test
    public void edit_multipleValuesTwoFields_success() throws Exception {
        String emailAddress = "bob@yahoo.com";
        String address = "Block 1 Bob Lane";
        String detailsToEdit = "e/alice@yahoo.com a/Alice House e/" + emailAddress + " a/" + address;
        int addressBookIndex = 2;

        Person personToEdit = expectedPersonsList[IndexUtil.oneToZeroIndex(addressBookIndex)];
        Person editedPerson = new PersonBuilder(personToEdit).withEmail(emailAddress).withAddress(address).build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson,
                String.format(EditCommand.MESSAGE_MULTIPLE_VALUES_WARNING, "Emails and Addresses", "Email and Address")
                        + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    @Test
    public void edit_multipleValuesThreeFields_success() throws Exception {
        String phoneNumber = "92139131";
        String emailAddress = "bob@yahoo.com";
        String address = "Block 1 Bob Lane";
        String detailsToEdit = "e/alice@yahoo.com a/Alice House e/" + emailAddress + " a/" + address + " p/911 p/"
                + phoneNumber;
        int addressBookIndex = 2;

        Person personToEdit = expectedPersonsList[IndexUtil.oneToZeroIndex(addressBookIndex)];
        Person editedPerson = new PersonBuilder(personToEdit).withEmail(emailAddress).withAddress(address)
                .withPhone(phoneNumber).build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson,
                String.format(EditCommand.MESSAGE_MULTIPLE_VALUES_WARNING, "Phones, Emails and Addresses",
                        "Phone, Email and Address")
                        + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    @Test
    public void edit_missingPersonIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidPersonIndex_failure() {
        commandBox.runCommand("edit 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit 1 p/abcd");
        assertResultMessage(Phone.MESSAGE_PHONE_CONSTRAINTS);

        commandBox.runCommand("edit 1 e/yahoo!!!");
        assertResultMessage(Email.MESSAGE_EMAIL_CONSTRAINTS);

        commandBox.runCommand("edit 1 a/");
        assertResultMessage(Address.MESSAGE_ADDRESS_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicatePerson_failure() {
        commandBox.runCommand("edit 3 Alice Pauline p/85355255 e/alice@example.com "
                                + "a/123, Jurong West Ave 6, #08-111 t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Checks whether the edited person has the correct updated details. Also
     * checks that the result message contains the edit success message.
     */
    private void assertEditSuccessNoWarning(int filteredPersonListIndex, int addressBookIndex, String detailsToEdit,
            Person editedPerson) {
        assertEditSuccess(filteredPersonListIndex, addressBookIndex, detailsToEdit, editedPerson,
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
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
    private void assertEditSuccess(int filteredPersonListIndex, int addressBookIndex, String detailsToEdit,
            Person editedPerson, String expectedMessage) {
        commandBox.runCommand("edit " + filteredPersonListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        PersonCardHandle editedCard = personListPanel.navigateToPerson(editedPerson.getName().fullName);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        expectedPersonsList[IndexUtil.oneToZeroIndex(addressBookIndex)] = editedPerson;
        assertTrue(personListPanel.isListMatching(expectedPersonsList));
        assertResultMessage(expectedMessage);
    }
}
