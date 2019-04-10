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
    private final ReadOnlyContactList addressBookWithCarl = new ContactListBuilder().withPerson(CARL).build();
    private final ReadOnlyContactList emptyContactList = new ContactListBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedContactList versionedContactList = prepareAddressBookList(emptyContactList);

        versionedContactList.commit();
        assertAddressBookListStatus(versionedContactList,
                Collections.singletonList(emptyContactList),
                emptyContactList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);

        versionedContactList.commit();
        assertAddressBookListStatus(versionedContactList,
                Arrays.asList(emptyContactList, contactListWithAmy, contactListWithBob),
                contactListWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 2);

        versionedContactList.commit();
        assertAddressBookListStatus(versionedContactList,
                Collections.singletonList(emptyContactList),
                emptyContactList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);

        assertTrue(versionedContactList.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 1);

        assertTrue(versionedContactList.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedContactList versionedContactList = prepareAddressBookList(emptyContactList);

        assertFalse(versionedContactList.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 2);

        assertFalse(versionedContactList.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 1);

        assertTrue(versionedContactList.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 2);

        assertTrue(versionedContactList.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedContactList versionedContactList = prepareAddressBookList(emptyContactList);

        assertFalse(versionedContactList.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);

        assertFalse(versionedContactList.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);

        versionedContactList.undo();
        assertAddressBookListStatus(versionedContactList,
                Collections.singletonList(emptyContactList),
                contactListWithAmy,
                Collections.singletonList(contactListWithBob));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 1);

        versionedContactList.undo();
        assertAddressBookListStatus(versionedContactList,
                Collections.emptyList(),
                emptyContactList,
                Arrays.asList(contactListWithAmy, contactListWithBob));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedContactList versionedContactList = prepareAddressBookList(emptyContactList);

        assertThrows(VersionedContactList.NoUndoableStateException.class, versionedContactList::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 2);

        assertThrows(VersionedContactList.NoUndoableStateException.class, versionedContactList::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 1);

        versionedContactList.redo();
        assertAddressBookListStatus(versionedContactList,
                Arrays.asList(emptyContactList, contactListWithAmy),
                contactListWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 2);

        versionedContactList.redo();
        assertAddressBookListStatus(versionedContactList,
                Collections.singletonList(emptyContactList),
                contactListWithAmy,
                Collections.singletonList(contactListWithBob));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedContactList versionedContactList = prepareAddressBookList(emptyContactList);

        assertThrows(VersionedContactList.NoRedoableStateException.class, versionedContactList::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedContactList versionedContactList = prepareAddressBookList(
                emptyContactList, contactListWithAmy, contactListWithBob);

        assertThrows(VersionedContactList.NoRedoableStateException.class, versionedContactList::redo);
    }

    @Test
    public void equals() {
        VersionedContactList versionedContactList = prepareAddressBookList(contactListWithAmy, contactListWithBob);

        // same values -> returns true
        VersionedContactList copy = prepareAddressBookList(contactListWithAmy, contactListWithBob);
        assertTrue(versionedContactList.equals(copy));

        // same object -> returns true
        assertTrue(versionedContactList.equals(versionedContactList));

        // null -> returns false
        assertFalse(versionedContactList.equals(null));

        // different types -> returns false
        assertFalse(versionedContactList.equals(1));

        // different state list -> returns false
        VersionedContactList differentAddressBookList = prepareAddressBookList(contactListWithBob, addressBookWithCarl);
        assertFalse(versionedContactList.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedContactList differentCurrentStatePointer = prepareAddressBookList(
                contactListWithAmy, contactListWithBob);
        shiftCurrentStatePointerLeftwards(versionedContactList, 1);
        assertFalse(versionedContactList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedContactList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedContactList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedContactList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedContactList versionedContactList,
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
        for (ReadOnlyContactList expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new ContactList(versionedContactList));
            versionedContactList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyContactList expectedAddressBook : expectedStatesAfterPointer) {
            versionedContactList.redo();
            assertEquals(expectedAddressBook, new ContactList(versionedContactList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedContactList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedContactList.undo());
    }

    /**
     * Creates and returns a {@code VersionedContactList} with the {@code addressBookStates} added into it, and the
     * {@code VersionedContactList#currentStatePointer} at the end of list.
     */
    private VersionedContactList prepareAddressBookList(ReadOnlyContactList... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedContactList versionedContactList = new VersionedContactList(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedContactList.resetData(addressBookStates[i]);
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
