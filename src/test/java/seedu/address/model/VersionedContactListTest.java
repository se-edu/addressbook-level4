package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ContactListBuilder;

public class VersionedContactListTest {

    private final ReadOnlyContactList contactListWithAmy = new ContactListBuilder().withPerson(AMY).build();
    private final ReadOnlyContactList contactListWithBob = new ContactListBuilder().withPerson(BOB).build();
    private final ReadOnlyContactList contactListWithCarl = new ContactListBuilder().withPerson(CARL).build();
    private final ReadOnlyContactList emptyContactList = new ContactListBuilder().build();

    @Test
    public void commit_singleContactList_noStatesRemovedCurrentStateSaved() {
        VersionedContactList versionedContactList = prepareContactListList(emptyContactList);

        versionedContactList.commit();
        assertContactListListStatus(versionedContactList,
                Collections.singletonList(emptyContactList),
                emptyContactList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleContactListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);

        versionedContactList.commit();
        assertContactListListStatus(versionedContactList,
                Arrays.asList(emptyContactList, contactListWithAmy, contactListWithBob),
                contactListWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleContactListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 2);

        versionedContactList.commit();
        assertContactListListStatus(versionedContactList,
                Collections.singletonList(emptyContactList),
                emptyContactList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleContactListPointerAtEndOfStateList_returnsTrue() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);

        assertTrue(versionedContactList.canUndo());
    }

    @Test
    public void canUndo_multipleContactListPointerAtStartOfStateList_returnsTrue() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 1);

        assertTrue(versionedContactList.canUndo());
    }

    @Test
    public void canUndo_singleContactList_returnsFalse() {
        VersionedContactList versionedContactList = prepareContactListList(emptyContactList);

        assertFalse(versionedContactList.canUndo());
    }

    @Test
    public void canUndo_multipleContactListPointerAtStartOfStateList_returnsFalse() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 2);

        assertFalse(versionedContactList.canUndo());
    }

    @Test
    public void canRedo_multipleContactListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 1);

        assertTrue(versionedContactList.canRedo());
    }

    @Test
    public void canRedo_multipleContactListPointerAtStartOfStateList_returnsTrue() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 2);

        assertTrue(versionedContactList.canRedo());
    }

    @Test
    public void canRedo_singleContactList_returnsFalse() {
        VersionedContactList versionedContactList = prepareContactListList(emptyContactList);

        assertFalse(versionedContactList.canRedo());
    }

    @Test
    public void canRedo_multipleContactListPointerAtEndOfStateList_returnsFalse() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);

        assertFalse(versionedContactList.canRedo());
    }

    @Test
    public void undo_multipleContactListPointerAtEndOfStateList_success() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);

        versionedContactList.undo();
        assertContactListListStatus(versionedContactList,
                Collections.singletonList(emptyContactList),
                contactListWithAmy,
                Collections.singletonList(contactListWithBob));
    }

    @Test
    public void undo_multipleContactListPointerNotAtStartOfStateList_success() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 1);

        versionedContactList.undo();
        assertContactListListStatus(versionedContactList,
                Collections.emptyList(),
                emptyContactList,
                Arrays.asList(contactListWithAmy, contactListWithBob));
    }

    @Test
    public void undo_singleContactList_throwsNoUndoableStateException() {
        VersionedContactList versionedContactList = prepareContactListList(emptyContactList);

        assertThrows(VersionedContactList.NoUndoableStateException.class, versionedContactList::undo);
    }

    @Test
    public void undo_multipleContactListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 2);

        assertThrows(VersionedContactList.NoUndoableStateException.class, versionedContactList::undo);
    }

    @Test
    public void redo_multipleContactListPointerNotAtEndOfStateList_success() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 1);

        versionedContactList.redo();
        assertContactListListStatus(versionedContactList,
                Arrays.asList(emptyContactList, contactListWithAmy),
                contactListWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleContactListPointerAtStartOfStateList_success() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 2);

        versionedContactList.redo();
        assertContactListListStatus(versionedContactList,
                Collections.singletonList(emptyContactList),
                contactListWithAmy,
                Collections.singletonList(contactListWithBob));
    }

    @Test
    public void redo_singleContactList_throwsNoRedoableStateException() {
        VersionedContactList versionedContactList = prepareContactListList(emptyContactList);

        assertThrows(VersionedContactList.NoRedoableStateException.class, versionedContactList::redo);
    }

    @Test
    public void redo_multipleContactListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedContactList versionedContactList = prepareContactListList(
                emptyContactList, contactListWithAmy, contactListWithBob);

        assertThrows(VersionedContactList.NoRedoableStateException.class, versionedContactList::redo);
    }

    @Test
    public void equals() {
        VersionedContactList versionedContactList = prepareContactListList(contactListWithAmy, contactListWithBob);

        // same values -> returns true
        VersionedContactList copy = prepareContactListList(contactListWithAmy, contactListWithBob);
        assertTrue(versionedContactList.equals(copy));

        // same object -> returns true
        assertTrue(versionedContactList.equals(versionedContactList));

        // null -> returns false
        assertFalse(versionedContactList.equals(null));

        // different types -> returns false
        assertFalse(versionedContactList.equals(1));

        // different state list -> returns false
        VersionedContactList differentContactListList = prepareContactListList(contactListWithBob, contactListWithCarl);
        assertFalse(versionedContactList.equals(differentContactListList));

        // different current pointer index -> returns false
        VersionedContactList differentCurrentStatePointer = prepareContactListList(
                contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 1);
        assertFalse(versionedContactList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedContactList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedContactList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedContactList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertContactListListStatus(VersionedContactList versionedContactList,
                                             List<ReadOnlyContactList> expectedStatesBeforePointer,
                                             ReadOnlyContactList expectedCurrentState,
                                             List<ReadOnlyContactList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new ContactList(versionedContactList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedContactList.canUndo()) {
            versionedContactList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyContactList expectedContactList : expectedStatesBeforePointer) {
            assertEquals(expectedContactList, new ContactList(versionedContactList));
            versionedContactList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyContactList expectedContactList : expectedStatesAfterPointer) {
            versionedContactList.redo();
            assertEquals(expectedContactList, new ContactList(versionedContactList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedContactList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedContactList.undo());
    }

    /**
     * Creates and returns a {@code VersionedContactList} with the {@code contactListStates} added into it, and the
     * {@code VersionedContactList#currentStatePointer} at the end of list.
     */
    private VersionedContactList prepareContactListList(ReadOnlyContactList... contactListStates) {
        assertFalse(contactListStates.length == 0);

        VersionedContactList versionedContactList = new VersionedContactList(contactListStates[0]);
        for (int i = 1; i < contactListStates.length; i++) {
            versionedContactList.resetData(contactListStates[i]);
            versionedContactList.commit();
        }

        return versionedContactList;
    }

    /**
     * Shifts the {@code versionedContactList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedContactList versionedContactList, int count) {
        for (int i = 0; i < count; i++) {
            versionedContactList.undo();
        }
    }
}
