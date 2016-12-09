package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.util.ListUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TestUtil;

public class DeleteCommandTest extends AddressBookGuiTest {

    public static final int PERSON_LIST_INDEX_OFFSET = -1;

    @Test
    public void delete_nonRangedIndices() {
        TestPerson[] currentList = td.getTypicalPersons();

        // deleting from front
        List<TestPerson> expectedPersonsDeleted =
                ListUtil.sublistFromIndices(Arrays.asList(currentList), PERSON_LIST_INDEX_OFFSET, 1);
        TestPerson[] expectedRemainder = getDeletedRemainder(currentList, expectedPersonsDeleted);
        assertDeleteSuccess("1", expectedRemainder, expectedPersonsDeleted);
        currentList = expectedRemainder;

        // deleting from rear
        expectedPersonsDeleted =
                ListUtil.sublistFromIndices(Arrays.asList(currentList), PERSON_LIST_INDEX_OFFSET, currentList.length);
        expectedRemainder = getDeletedRemainder(currentList, expectedPersonsDeleted);
        assertDeleteSuccess("" + currentList.length, expectedRemainder, expectedPersonsDeleted);
        currentList = expectedRemainder;

        // deleting from middle
        expectedPersonsDeleted = ListUtil.sublistFromIndices(Arrays.asList(currentList), PERSON_LIST_INDEX_OFFSET,
                currentList.length / 2);
        expectedRemainder = getDeletedRemainder(currentList, expectedPersonsDeleted);
        assertDeleteSuccess("" + currentList.length / 2, expectedRemainder, expectedPersonsDeleted);
    }

    @Test
    public void delete_rangedIndices() {
        TestPerson[] currentList = td.getTypicalPersons();

        // deleting ascending range
        List<TestPerson> expectedPersonsDeleted =
                ListUtil.sublistFromIndices(Arrays.asList(currentList), PERSON_LIST_INDEX_OFFSET, 1, 2, 3);
        TestPerson[] expectedRemainder = getDeletedRemainder(currentList, expectedPersonsDeleted);
        assertDeleteSuccess("1-3", expectedRemainder, expectedPersonsDeleted);
        currentList = expectedRemainder;

        // deleting descending range
        expectedPersonsDeleted =
                ListUtil.sublistFromIndices(Arrays.asList(currentList), PERSON_LIST_INDEX_OFFSET, 2, 3, 4);
        expectedRemainder = getDeletedRemainder(currentList, expectedPersonsDeleted);
        assertDeleteSuccess("4-2", expectedRemainder, expectedPersonsDeleted);
    }

    @Test
    public void delete_multipleIndicesOrdered() {
        TestPerson[] currentList = td.getTypicalPersons();
        List<TestPerson> expectedPersonsDeleted =
                ListUtil.sublistFromIndices(Arrays.asList(currentList), PERSON_LIST_INDEX_OFFSET, 1, 2, 3, 5, 7);
        TestPerson[] expectedRemainder = getDeletedRemainder(currentList, expectedPersonsDeleted);
        assertDeleteSuccess("1-3 5 7", expectedRemainder, expectedPersonsDeleted);
    }

    @Test
    public void delete_multipleIndicesUnordered() {
        TestPerson[] currentList = td.getTypicalPersons();
        List<TestPerson> expectedPersonsDeleted =
                ListUtil.sublistFromIndices(Arrays.asList(currentList), PERSON_LIST_INDEX_OFFSET, 1, 2, 3, 5, 7);
        TestPerson[] expectedRemainder = getDeletedRemainder(currentList, expectedPersonsDeleted);
        assertDeleteSuccess("5 1-3 7", expectedRemainder, expectedPersonsDeleted);
    }

    @Test
    public void delete_ignoresDuplicates() {
        TestPerson[] currentList = td.getTypicalPersons();
        List<TestPerson> expectedPersonsDeleted =
                ListUtil.sublistFromIndices(Arrays.asList(currentList), PERSON_LIST_INDEX_OFFSET, 1);
        TestPerson[] expectedRemainder = getDeletedRemainder(currentList, expectedPersonsDeleted);
        assertDeleteSuccess("1 1 1 1 1", expectedRemainder, expectedPersonsDeleted);
    }

    @Test
    public void delete_overlappingRanges() {
        TestPerson[] currentList = td.getTypicalPersons();
        List<TestPerson> expectedPersonsDeleted =
                ListUtil.sublistFromIndices(Arrays.asList(currentList), PERSON_LIST_INDEX_OFFSET, 4, 5, 6);
        TestPerson[] expectedRemainder = getDeletedRemainder(currentList, expectedPersonsDeleted);
        assertDeleteSuccess("4-5 4-6 5-6 4-4 5-5 6-6", expectedRemainder, expectedPersonsDeleted);
    }

    @Test
    public void delete_excessiveWhitespaces() {
        TestPerson[] currentList = td.getTypicalPersons();
        List<TestPerson> expectedPersonsDeleted =
                ListUtil.sublistFromIndices(Arrays.asList(currentList), PERSON_LIST_INDEX_OFFSET, 2, 3);
        TestPerson[] expectedRemainder = getDeletedRemainder(currentList, expectedPersonsDeleted);
        assertDeleteSuccess("         2      3      ", expectedRemainder, expectedPersonsDeleted);
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
     * is correct i.e. list deleted {@code expectedPersonsDeleted} and reflects {@code expectedRemainder} to user.
     */
    private void assertDeleteSuccess(String deleteArgs, TestPerson[] expectedRemainder,
            List<TestPerson> expectedPersonsDeleted) {

        commandBox.runCommand("delete " + deleteArgs);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(personListPanel.isListMatching(expectedRemainder));

        String expectedFeedback = StringUtil.toIndexedListString(expectedPersonsDeleted);

        //confirm the result message is correct
        assertResultMessage(
                String.format(MESSAGE_DELETE_PERSON_SUCCESS, expectedPersonsDeleted.size(), expectedFeedback));
    }

    /** Returns a copy of {@code currentList} without {@code personsToDelete}. */
    private TestPerson[] getDeletedRemainder(TestPerson[] currentList, List<TestPerson> personsToDelete) {
        return TestUtil.removePersonsFromList(currentList,
                personsToDelete.toArray(new TestPerson[personsToDelete.size()]));
    }

}
