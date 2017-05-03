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

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.ParserUtil;

public class EditPersonDescriptorTest {

    @Test
    public void equals_sameValues_returnsTrue() {
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);

        assertTrue(DESC_AMY.equals(descriptorWithSameValues));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(DESC_AMY.equals(DESC_AMY));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        assertFalse(DESC_AMY.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(DESC_AMY.equals(5));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        assertFalse(DESC_AMY.equals(DESC_BOB));
    }

    @Test
    public void equals_differentName_returnsFalse() throws Exception {
        EditPersonDescriptor editedAmy = new EditPersonDescriptor(DESC_AMY);
        editedAmy.setName(ParserUtil.parseName(Optional.of(VALID_NAME_BOB)));
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void equals_differentPhone_returnsFalse() throws Exception {
        EditPersonDescriptor editedAmy = new EditPersonDescriptor(DESC_AMY);
        editedAmy.setPhone(ParserUtil.parsePhone(Optional.of(VALID_PHONE_BOB)));
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void equals_differentEmail_returnsFalse() throws Exception {
        EditPersonDescriptor editedAmy = new EditPersonDescriptor(DESC_AMY);
        editedAmy.setEmail(ParserUtil.parseEmail(Optional.of(VALID_EMAIL_BOB)));
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void equals_differentAddress_returnsFalse() throws Exception {
        EditPersonDescriptor editedAmy = new EditPersonDescriptor(DESC_AMY);
        editedAmy.setAddress(ParserUtil.parseAddress(Optional.of(VALID_ADDRESS_BOB)));
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void equals_differentTags_returnsFalse() throws Exception {
        EditPersonDescriptor editedAmy = new EditPersonDescriptor(DESC_AMY);
        editedAmy.setTags(Optional.of(ParserUtil.parseTags(Arrays.asList(VALID_TAG_HUSBAND))));
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
