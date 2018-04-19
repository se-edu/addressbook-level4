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

import seedu.address.testutil.AddressBookBuilder;

public class VersionedAddressBookTest {

    private final ReadOnlyAddressBook addressBookWithAmy = new AddressBookBuilder().withPerson(AMY).build();
    private final ReadOnlyAddressBook addressBookWithBob = new AddressBookBuilder().withPerson(BOB).build();
    private final ReadOnlyAddressBook addressBookWithCarl = new AddressBookBuilder().withPerson(CARL).build();
    private final ReadOnlyAddressBook emptyAddressBook = new AddressBookBuilder().build();

    @Test
    public void commit() {
        VersionedAddressBook versionedAddressBook = prepareAddressBookList(
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob));
        assertAddressBookListStatus(
                versionedAddressBook,
                Arrays.asList(emptyAddressBook, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        // all states after current state pointer removed, current state added to end of list
        versionedAddressBook.resetData(addressBookWithCarl);
        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithCarl,
                Collections.emptyList());

        // current state pointer at end of list -> no state removed, current state added to end of list
        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(emptyAddressBook, addressBookWithCarl),
                addressBookWithCarl,
                Collections.emptyList());
    }

    @Test
    public void canUndo() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(emptyAddressBook);

        // single address book in state list
        assertFalse(versionedAddressBook.canUndo());

        // multiple address book in state list, current pointer not at index 0
        versionedAddressBook.commit();
        assertTrue(versionedAddressBook.canUndo());

        // multiple address book in state list, current pointer at index 0, start of list
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);
        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canRedo() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(emptyAddressBook);

        // single address book in state list
        assertFalse(versionedAddressBook.canRedo());

        // multiple address book in state list, current pointer at end of list
        versionedAddressBook.commit();
        assertFalse(versionedAddressBook.canRedo());

        // multiple address book in state list, current pointer not at end of list
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);
        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void undo() {
        VersionedAddressBook versionedAddressBook = prepareAddressBookList(
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob));

        //current state pointer at index 2
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(emptyAddressBook, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());

        // shift state pointer once to index 1
        versionedAddressBook.undo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));

        // shift state pointer once to index 0
        versionedAddressBook.undo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.emptyList(),
                emptyAddressBook,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // current state pointer at index 0, start of list -> fail
        assertThrows(VersionedAddressBook.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void redo() {
        VersionedAddressBook versionedAddressBook = prepareAddressBookList(
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob));
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        // current state pointer at index 0
        assertAddressBookListStatus(versionedAddressBook,
                Collections.emptyList(),
                emptyAddressBook,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // shift state pointer once to index 1
        versionedAddressBook.redo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));

        // shift state pointer once to index 2
        versionedAddressBook.redo();
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(emptyAddressBook, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());

        // current state pointer at index 2, end of list -> fail
        assertThrows(VersionedAddressBook.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void equals() {
        VersionedAddressBook versionedAddressBook = prepareAddressBookList(
                Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // same values -> returns true
        VersionedAddressBook copy = prepareAddressBookList(Arrays.asList(addressBookWithAmy, addressBookWithBob));
        assertTrue(versionedAddressBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedAddressBook.equals(versionedAddressBook));

        // null -> returns false
        assertFalse(versionedAddressBook.equals(null));

        // different types -> returns false
        assertFalse(versionedAddressBook.equals(1));

        // different state list -> returns false
        VersionedAddressBook differentAddressBookList = prepareAddressBookList(
                Arrays.asList(addressBookWithBob, addressBookWithCarl));
        assertFalse(versionedAddressBook.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedAddressBook differentCurrentStatePointer = prepareAddressBookList(
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);
        assertFalse(versionedAddressBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedAddressBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedAddressBook versionedAddressBook,
                                             List<ReadOnlyAddressBook> expectedStatesBeforePointer,
                                             ReadOnlyAddressBook expectedCurrentState,
                                             List<ReadOnlyAddressBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new AddressBook(versionedAddressBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedAddressBook.canUndo()) {
            versionedAddressBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyAddressBook expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new AddressBook(versionedAddressBook));
            versionedAddressBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyAddressBook expectedAddressBook : expectedStatesAfterPointer) {
            versionedAddressBook.redo();
            assertEquals(expectedAddressBook, new AddressBook(versionedAddressBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedAddressBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach((unused) -> versionedAddressBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedAddressBook} with the {@code addressBookStates} added into it, and the
     * {@code VersionedAddressBook#currentStatePointer} at the end of list.
     */
    private VersionedAddressBook prepareAddressBookList(List<ReadOnlyAddressBook> addressBookStates) {
        assertFalse(addressBookStates.isEmpty());

        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(addressBookStates.get(0));
        for (int i = 1; i < addressBookStates.size(); i++) {
            versionedAddressBook.resetData(addressBookStates.get(i));
            versionedAddressBook.commit();
        }

        return versionedAddressBook;
    }

    /**
     * Shifts the {@code versionedAddressBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedAddressBook versionedAddressBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedAddressBook.undo();
        }
    }
}
