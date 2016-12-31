package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.ListUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TestUtil;

public class DeleteCommandTest extends AddressBookGuiTest {

    public final int size = td.getTypicalPersons().length;

    @Test
    public void delete_nonRangedIndices() {
        Integer[] expectedTargetIndices = { 0, size / 2 - 1, size - 1 };
        // deleting first, middle and last item
        assertDeleteSuccess("1 " + (size / 2) + " " + size, expectedTargetIndices);
    }

    @Test
    public void delete_rangedIndicesAscending() {
        assertDeleteSuccess("1-3", 0, 1, 2);
    }

    @Test
    public void delete_rangedIndicesDescending() {
        assertDeleteSuccess("4-2", 1, 2, 3);
    }

    @Test
    public void delete_multipleIndicesOrdered() {
        assertDeleteSuccess("1-3 5 7", 0, 1, 2, 4, 6);
    }

    @Test
    public void delete_multipleIndicesUnordered() {
        assertDeleteSuccess("5 1-3 7", 0, 1, 2, 4, 6);
    }

    @Test
    public void delete_duplicateIndices_duplicatesIgnored() {
        assertDeleteSuccess("1 1 1 1 1", 0);
    }

    @Test
    public void delete_overlappingRanges() {
        assertDeleteSuccess("4-5 4-6 5-6 4-4 5-5 6-6", 3, 4, 5);
    }

    @Test
    public void delete_excessiveWhitespaces() {
        assertDeleteSuccess("         2      3      ", 1, 2);
    }

    @Test
    public void delete_invalidIndex() {
        TestPerson[] currentList = td.getTypicalPersons();
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The person index provided is invalid");
    }

    /*
     * =========================================================================
     *                             Helper Methods
     * =========================================================================
     */

    /**
     * Runs the delete command to delete persons specified by {@code deleteArgs} and confirms the result
     * is correct i.e. list deleted persons specified by {@code expectedTargetIndices}.
     */
    private void assertDeleteSuccess(String deleteArgs, Integer... expectedTargetIndices) {
        TestPerson[] currentList = td.getTypicalPersons();
        List<TestPerson> expectedPersonsDeleted = ListUtil.subList(Arrays.asList(currentList), expectedTargetIndices);
        TestPerson[] expectedRemainder = getRemainder(currentList, expectedPersonsDeleted);

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

    /** Returns a copy of {@code currentList} but without {@code personsToDelete}. */
    private TestPerson[] getRemainder(TestPerson[] currentList, List<TestPerson> personsToDelete) {
        return TestUtil.removePersonsFromList(
                currentList, personsToDelete.toArray(new TestPerson[personsToDelete.size()]));
    }
}
