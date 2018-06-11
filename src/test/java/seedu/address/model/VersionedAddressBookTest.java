package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.AddressBookBuilder;

public class VersionedAddressBookTest {

    private final ReadOnlyAddressBook addressBookWithAmy = new AddressBookBuilder().withPerson(AMY).build();
    private final ReadOnlyAddressBook addressBookWithBob = new AddressBookBuilder().withPerson(BOB).build();
    private final ReadOnlyAddressBook addressBookWithCarl = new AddressBookBuilder().withPerson(CARL).build();
    private final ReadOnlyAddressBook emptyAddressBook = new AddressBookBuilder().build();

    @Test
    public void addNewState() {
        VersionedAddressBook versionedAddressBook = prepareAddressBookList(
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob));
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        // all states after current state pointer removed, new state added to end of list
        versionedAddressBook.addNewState(addressBookWithCarl);
        assertAddressBookListStatus(versionedAddressBook, Arrays.asList(emptyAddressBook, addressBookWithCarl));

        // current state pointer at end of list -> no state removed, new state added to end of list
        versionedAddressBook.addNewState(addressBookWithBob);
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(emptyAddressBook, addressBookWithCarl, addressBookWithBob));
    }

    @Test
    public void canUndo() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(emptyAddressBook);

        // single address book in state list
        assertFalse(versionedAddressBook.canUndo());

        // multiple address book in state list, current pointer not at index 0
        versionedAddressBook.addNewState(addressBookWithAmy);
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
        versionedAddressBook.addNewState(addressBookWithAmy);
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
        assertEquals(addressBookWithBob.getPersonList(), versionedAddressBook.getPersonList());

        // shift state pointer once to index 1
        versionedAddressBook.undo();
        assertEquals(addressBookWithAmy.getPersonList(), versionedAddressBook.getPersonList());

        // shift state pointer once to index 0
        versionedAddressBook.undo();
        assertEquals(emptyAddressBook.getPersonList(), versionedAddressBook.getPersonList());

        // current state pointer at index 0, start of list -> fail
        assertThrows(VersionedAddressBook.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void redo() {
        VersionedAddressBook versionedAddressBook = prepareAddressBookList(
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob));
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        // current state pointer at index 0
        assertEquals(emptyAddressBook.getPersonList(), versionedAddressBook.getPersonList());

        // shift state pointer once to index 1
        versionedAddressBook.redo();
        assertEquals(addressBookWithAmy.getPersonList(), versionedAddressBook.getPersonList());

        // shift state pointer once to index 2
        versionedAddressBook.redo();
        assertEquals(addressBookWithBob.getPersonList(), versionedAddressBook.getPersonList());

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
     * Asserts that {@code versionedAddressBook} list content equals {@code expectedAddressBookStates}.
     */
    private void assertAddressBookListStatus(VersionedAddressBook versionedAddressBook,
                                             List<ReadOnlyAddressBook> expectedAddressBookStates) {
        // shift currentStatePointer to the start of list
        while (versionedAddressBook.canUndo()) {
            versionedAddressBook.undo();
        }

        for (ReadOnlyAddressBook expectedAddressBook: expectedAddressBookStates) {
            assertEquals(expectedAddressBook.getPersonList(), versionedAddressBook.getPersonList());
            if (versionedAddressBook.canRedo()) {
                versionedAddressBook.redo();
            }
        }
    }

    /**
     * Creates and returns a {@code VersionedAddressBook} with the {@code addressBookStates} added into it, and the
     * {@code VersionedAddressBook#currentStatePointer} at the end of list.
     */
    private VersionedAddressBook prepareAddressBookList(List<ReadOnlyAddressBook> addressBookStates) {
        assertFalse(addressBookStates.isEmpty());

        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(addressBookStates.get(0));
        for (int i = 1; i < addressBookStates.size(); i++) {
            versionedAddressBook.addNewState(addressBookStates.get(i));
        }

        versionedAddressBook.resetData(addressBookStates.get(addressBookStates.size() - 1));
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
