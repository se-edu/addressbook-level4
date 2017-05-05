package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EditCommandTestUtil.DESC_AMY;
import static seedu.address.testutil.EditCommandTestUtil.DESC_BOB;
import static seedu.address.testutil.EditCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.EditCommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.testutil.EditCommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.EditCommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.EditCommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() throws Exception {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPersonDescriptorBuilder editedAmy = new EditPersonDescriptorBuilder(DESC_AMY);
        editedAmy.withName(VALID_NAME_BOB);
        assertFalse(DESC_AMY.equals(editedAmy.build()));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY);
        editedAmy.withPhone(VALID_PHONE_BOB);
        assertFalse(DESC_AMY.equals(editedAmy.build()));

        // different email -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY);
        editedAmy.withEmail(VALID_EMAIL_BOB);
        assertFalse(DESC_AMY.equals(editedAmy.build()));

        // different address -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY);
        editedAmy.withAddress(VALID_ADDRESS_BOB);
        assertFalse(DESC_AMY.equals(editedAmy.build()));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY);
        editedAmy.withTags(VALID_TAG_HUSBAND);
        assertFalse(DESC_AMY.equals(editedAmy.build()));
    }
}
