package guitests;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.ListUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DeleteCommandTest extends AddressBookGuiTest {

    @Test
    public void delete() {
        TestPerson[] currentList = td.getTypicalPersons();
        // unsuccessful deleting
        assertDeleteCommandResult(
                "" + (currentList.length + 1), Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // successful deletion of first person
        assertDeleteSuccess("1", currentList, 0);
        currentList = TestUtil.removePersonFromList(currentList, 1);

        // successful deletion of last person
        assertDeleteSuccess("" + currentList.length, currentList, currentList.length - 1);
        currentList = TestUtil.removePersonFromList(currentList, currentList.length);

        // successful deletion of a range of persons
        assertDeleteSuccess("1-3", currentList, 0, 1, 2);
    }

    /**
     * Runs the delete command to delete persons specified by {@code deleteArgs} and confirms the result
     * is correct i.e. {@currentList} deleted persons specified by {@code expectedTargetIndices}.<br>
     * {@code expectedTargetIndices} are zero-indexed i.e. index 0 refers to the first element.
     */
    private void assertDeleteSuccess(String deleteArgs, TestPerson[] currentList, Integer... expectedTargetIndices) {
        List<TestPerson> expectedPersonsDeleted =
                ListUtil.subList(Arrays.asList(currentList), new HashSet<>(Arrays.asList(expectedTargetIndices)));
        TestPerson[] expectedRemainder = TestUtil.removePersonsFromList(currentList, expectedPersonsDeleted);
        String expectedMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, expectedPersonsDeleted.size(),
                                               StringUtil.toIndexedListString(expectedPersonsDeleted));

        assertDeleteCommandResult(deleteArgs, expectedMessage);

        //confirm the list now contains all previous persons except the deleted
        assertTrue(personListPanel.isListMatching(expectedRemainder));
    }

    /** Runs the delete command specified by {@code deleteArgs} and confirms result matches {@code expectedMessage}. */
    private void assertDeleteCommandResult(String deleteArgs, String expectedMessage) {
        commandBox.runCommand("delete " + deleteArgs);
        assertResultMessage(expectedMessage);
    }
}
