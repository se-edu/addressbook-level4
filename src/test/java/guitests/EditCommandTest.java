package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TestUtil;

public class EditCommandTest extends AddressBookGuiTest {

    TestPerson[] currentList = td.getTypicalPersons();

    @Test
    public void edit_allFields() throws Exception {
        String detailsToEdit = "Bobby p/91234567 e/bobby@gmail.com a/Block 123, Bobby Street 3 t/husband";
        int targetIndex = 1;

        TestPerson editedPerson = new PersonBuilder().withName("Bobby").withPhone("91234567")
                .withEmail("bobby@gmail.com").withAddress("Block 123, Bobby Street 3").withTags("husband").build();

        String resultMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        assertEditSuccess(targetIndex, detailsToEdit, editedPerson, resultMessage);
    }

    @Test
    public void edit_oneField() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int targetIndex = 2;

        TestPerson personToEdit = currentList[targetIndex - 1];
        TestPerson editedPerson = new PersonBuilder(personToEdit).withTags("sweetie", "bestie").build();

        String resultMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        assertEditSuccess(targetIndex, detailsToEdit, editedPerson, resultMessage);
    }

    @Test
    public void edit_clearTags() throws Exception {
        String detailsToEdit = "t/";
        int targetIndex = 2;

        TestPerson personToEdit = currentList[targetIndex - 1];
        TestPerson editedPerson = new PersonBuilder(personToEdit).withTags().build();

        String resultMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        assertEditSuccess(targetIndex, detailsToEdit, editedPerson, resultMessage);
    }

    @Test
    public void edit_missingPersonIndex() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidPersonIndex() {
        commandBox.runCommand("edit 100 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsEdited() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues() {
        commandBox.runCommand("edit 1 p/abcd");
        assertResultMessage(Phone.MESSAGE_PHONE_CONSTRAINTS);

        commandBox.runCommand("edit 1 e/yahoo!!!");
        assertResultMessage(Email.MESSAGE_EMAIL_CONSTRAINTS);

        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void edit_duplicatePerson() {
        commandBox.runCommand("edit 3 Alice Pauline p/85355255 e/alice@gmail.com "
                                + "a/123, Jurong West Ave 6, #08-111 t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Checks whether the edited person has the correct updated details.
     *
     * @param index of person to edit
     * @param detailsToEdit details of the person to edit as an input to enter into the command box
     * @param editedPerson the expected person after editing the person's details
     * @param resultMessage the expected message shown to user
     */
    private void assertEditSuccess(int index, String detailsToEdit, TestPerson editedPerson, String resultMessage) {
        commandBox.runCommand("edit " + index + " " + detailsToEdit);

        // confirm the new card contains the right data
        PersonCardHandle editedCard = personListPanel.navigateToPerson(editedPerson.getName().fullName);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        TestPerson[] expectedList = TestUtil.replacePersonFromList(currentList, editedPerson, index - 1);
        assertTrue(personListPanel.isListMatching(expectedList));
        assertResultMessage(resultMessage);
    }
}
