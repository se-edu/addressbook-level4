package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASKONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASKTWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINEDATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINETIME_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASKNAME_TWO;

import org.junit.Test;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_TASKONE);
        assertTrue(DESC_TASKONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_TASKONE.equals(DESC_TASKONE));

        // null -> returns false
        assertFalse(DESC_TASKONE.equals(null));

        // different types -> returns false
        assertFalse(DESC_TASKONE.equals(5));

        // different values -> returns false
        assertFalse(DESC_TASKONE.equals(DESC_TASKTWO));

        // different name -> returns false
        EditTaskDescriptor editedTaskOne = new EditTaskDescriptorBuilder(DESC_TASKONE)
                .withTaskName(VALID_TASKNAME_TWO).build();
        assertFalse(DESC_TASKONE.equals(editedTaskOne));

        // different deadline time -> returns false
        editedTaskOne = new EditTaskDescriptorBuilder(DESC_TASKONE).withDeadlineTime(VALID_DEADLINETIME_TWO).build();
        assertFalse(DESC_TASKONE.equals(editedTaskOne));

        // different deadline date -> returns false
        editedTaskOne = new EditTaskDescriptorBuilder(DESC_TASKONE).withDeadlineDate(VALID_DEADLINEDATE_TWO).build();
        assertFalse(DESC_TASKONE.equals(editedTaskOne));

        // different tags -> returns false
        editedTaskOne = new EditTaskDescriptorBuilder(DESC_TASKONE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_TASKONE.equals(editedTaskOne));
    }
}
