package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TestUtil;

public class EditCommandTest extends AddressBookGuiTest {
    @Test
    public void edit() throws IllegalValueException {
        // edits all the details of the first person in the list
        TestPerson[] currentList = td.getTypicalPersons();
        int targetIndex = 1;
        PersonBuilder personCopy = copyPersonAsPersonBuilder(currentList, targetIndex, false);
        String detailsToEdit = "Bobby p/91234567 e/bobby@gmail.com a/Block 123, Bobby Street 3 t/husband";
        TestPerson editedPerson = personCopy.withName("Bobby").withPhone("91234567").withEmail("bobby@gmail.com")
                .withAddress("Block 123, Bobby Street 3").withTags("husband").build();

        assertEditSuccess(detailsToEdit, editedPerson, targetIndex, currentList);
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));

        // edit one field of the second person in the list
        targetIndex = 2;
        personCopy = copyPersonAsPersonBuilder(currentList, targetIndex, false);
        detailsToEdit = "t/sweetie";
        editedPerson = personCopy.withTags("sweetie").build();

        assertEditSuccess(detailsToEdit, editedPerson, targetIndex, currentList);
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));

        // reset tags
        targetIndex = 2;
        detailsToEdit = "t/";

        TestPerson personToEdit = currentList[targetIndex - 1];
        String name = personToEdit.getName().fullName;
        String phone = personToEdit.getPhone().value;
        String email = personToEdit.getEmail().value;
        String address = personToEdit.getAddress().value;
        editedPerson = new PersonBuilder().withName(name).withPhone(phone).withEmail(email).withAddress(address)
                .build();

        assertEditSuccess(detailsToEdit, editedPerson, targetIndex, currentList);
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));

        // edit into existing person
        commandBox.runCommand("edit 3 Bobby p/91234567 e/bobby@gmail.com a/Block 123, Bobby Street 3 t/husband");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_PERSON);

        // missing index
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // invalid index
        commandBox.runCommand("edit 100 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no fields edited
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NO_ARGUMENT);

        // edit using invalid values
        commandBox.runCommand("edit 1 p/abcd");
        assertResultMessage(Phone.MESSAGE_PHONE_CONSTRAINTS);
    }

    /**
     * Copies the details of the person to edit, into a PersonBuilder object.
     * Depending on test cases, there may not be a need to copy tags as
     * {@code builder.withTags(tagNames)} is additive and there is no way to
     * remove tags that have already been added to the builder.
     *
     * @param currentList
     *            list of persons in address book
     * @param targetIndex
     *            index of person to edit
     * @param shouldCopyTags
     *            whether the person's original tags should be copied
     * @return PersonBuilder object containing the details of the person to be
     *         edited
     * @throws IllegalValueException
     *             if any of the details of the person to edit is invalid
     *             (should not happen)
     */
    private PersonBuilder copyPersonAsPersonBuilder(TestPerson[] currentList, int targetIndex, boolean shouldCopyTags)
            throws IllegalValueException {
        TestPerson personToEdit = currentList[targetIndex - 1];
        String name = personToEdit.getName().fullName;
        String phone = personToEdit.getPhone().value;
        String email = personToEdit.getEmail().value;
        String address = personToEdit.getAddress().value;

        PersonBuilder builder = new PersonBuilder().withName(name).withPhone(phone).withEmail(email)
                .withAddress(address);

        if (shouldCopyTags) {
            Set<Tag> tagSet = personToEdit.getTags().toSet();
            String[] tagNames = tagSet.stream().map(tag -> tag.tagName).toArray(size -> new String[size]);
            builder.withTags(tagNames);
        }

        return builder;
    }

    /**
     * Check whether the edited person has the correct updated details.
     *
     * @param detailsToEdit
     *            details of the person to edit as an input in the command box
     * @param editedPerson
     *            the expected person after editing the person's details
     * @param index
     *            index of person to edit
     * @param currentList
     *            list of persons in address book
     */
    private void assertEditSuccess(String detailsToEdit, TestPerson editedPerson, int index,
            TestPerson... currentList) {
        commandBox.runCommand("edit " + index + " " + detailsToEdit);

        // confirm the new card contains the right data
        PersonCardHandle editedCard = personListPanel.navigateToPerson(editedPerson.getName().fullName);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person
        // with updated details
        TestPerson[] expectedList = TestUtil.replacePersonFromList(currentList, editedPerson, index - 1);
        assertTrue(personListPanel.isListMatching(expectedList));
    }
}
