package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestPerson;

public class EditCommandTest extends AddressBookGuiTest {

    // list is mutated with every successful call to assertEditSuccess().
    TestPerson[] expectedPersonsList = td.getTypicalPersons();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby p/91234567 e/bobby@gmail.com a/Block 123, Bobby Street 3 t/husband";
        int targetIndex = 1;

        TestPerson editedPerson = new PersonBuilder().withName("Bobby").withPhone("91234567")
                .withEmail("bobby@gmail.com").withAddress("Block 123, Bobby Street 3").withTags("husband").build();

        assertEditSuccess(targetIndex, targetIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int targetIndex = 2;

        TestPerson personToEdit = expectedPersonsList[targetIndex - 1];
        TestPerson editedPerson = new PersonBuilder(personToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(targetIndex, targetIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int targetIndex = 2;

        TestPerson personToEdit = expectedPersonsList[targetIndex - 1];
        TestPerson editedPerson = new PersonBuilder(personToEdit).withTags().build();

        assertEditSuccess(targetIndex, targetIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Elle");

        String detailsToEdit = "Belle";
        int targetIndexInFilteredList = 1;
        int targetIndexInUnfilteredList = 5;

        TestPerson personToEdit = expectedPersonsList[targetIndexInUnfilteredList - 1];
        TestPerson editedPerson = new PersonBuilder(personToEdit).withName("Belle").build();

        assertEditSuccess(targetIndexInFilteredList, targetIndexInUnfilteredList, detailsToEdit, editedPerson);
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
        commandBox.runCommand("edit 3 Alice Pauline p/85355255 e/alice@gmail.com "
                                + "a/123, Jurong West Ave 6, #08-111 t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Checks whether the edited person has the correct updated details.
     *
     * @param targetIndexInFilteredList index of person to edit in filtered list e.g after a {@code FindCommand}
     * @param targetIndexInUnfilteredList index of person to edit in unfiltered list
     * @param detailsToEdit details of the person to edit as input to the edit command
     * @param editedPerson the expected person after editing the person's details
     */
    private void assertEditSuccess(int targetIndexInFilteredList, int targetIndexInUnfilteredList,
                                    String detailsToEdit, TestPerson editedPerson) {
        commandBox.runCommand("edit " + targetIndexInFilteredList + " " + detailsToEdit);

        // confirm the new card contains the right data
        PersonCardHandle editedCard = personListPanel.navigateToPerson(editedPerson.getName().fullName);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        expectedPersonsList[targetIndexInUnfilteredList - 1] = editedPerson;
        assertTrue(personListPanel.isListMatching(expectedPersonsList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }
}
