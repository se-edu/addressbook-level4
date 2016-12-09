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
    public void edit() {
        // edits all the details of the first person in the list
        TestPerson[] currentList = td.getTypicalPersons();
        try {
            int targetIndex = 1;
            PersonBuilder copy = copyPersonAsPersonBuilder(currentList, targetIndex);
            String detailsToEdit = "Bobby p/91234567 e/bobby@gmail.com a/Block 123, Bobby Street 3 t/husband";
            TestPerson editedPerson = copy.withName("Bobby").withPhone("91234567").withEmail("bobby@gmail.com")
                    .withAddress("Block 123, Bobby Street 3").withTags("husband").build();
            
            assertEditSuccess(detailsToEdit, editedPerson, targetIndex, currentList);
        } catch (IllegalValueException e) {
            assert false;
        }
        
        // edit one field of the second person in the list
        try {
            int targetIndex = 2;
            PersonBuilder copy = copyPersonAsPersonBuilder(currentList, targetIndex);
            String detailsToEdit = "t/sweetie";
            TestPerson editedPerson = copy.withTags("sweetie").build();
            
            assertEditSuccess(detailsToEdit, editedPerson, targetIndex, currentList);
        } catch (IllegalValueException e) {
            assert false;
        }
        
        // reset tags
        try {
            int targetIndex = 2;
            String detailsToEdit = "t/";
            
            TestPerson personToEdit = currentList[targetIndex - 1];
            String name = personToEdit.getName().fullName;
            String phone = personToEdit.getPhone().value;
            String email = personToEdit.getEmail().value;
            String address = personToEdit.getAddress().value;
            TestPerson editedPerson = new PersonBuilder().withName(name).withPhone(phone)
                    .withEmail(email).withAddress(address).build();
            
            assertEditSuccess(detailsToEdit, editedPerson, targetIndex, currentList);
        } catch (IllegalValueException e) {
            assert false;
        }
        
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
     */
    private PersonBuilder copyPersonAsPersonBuilder(TestPerson[] currentList, int targetIndex)
            throws IllegalValueException {
        TestPerson personToEdit = currentList[targetIndex - 1];
        String name = personToEdit.getName().fullName;
        String phone = personToEdit.getPhone().value;
        String email = personToEdit.getEmail().value;
        String address = personToEdit.getAddress().value;
        
        Set<Tag> tagSet = personToEdit.getTags().toSet();
        String[] tagNames = new String[tagSet.size()];
        Tag[] tags = new Tag[tagSet.size()];
        tags = tagSet.toArray(tags);
        for (int i = 0; i < tags.length; i++) {
            tagNames[i] = tags[i].tagName;
        }
        return new PersonBuilder().withName(name).withPhone(phone).withEmail(email).withAddress(address).withTags(tagNames);
    }

    private void assertEditSuccess(String detailsToEdit, TestPerson editedPerson, int index, TestPerson... currentList) {
        commandBox.runCommand("edit " + index + " " + detailsToEdit);
        
        //confirm the new card contains the right data
        PersonCardHandle editedCard = personListPanel.navigateToPerson(editedPerson.getName().fullName);
        assertMatching(editedPerson, editedCard);

        //confirm the list now contains all previous persons plus the person with updated details
        TestPerson[] expectedList = TestUtil.replacePersonFromList(currentList, editedPerson, index - 1);
        assertTrue(personListPanel.isListMatching(expectedList));
    }
}
