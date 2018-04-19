package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.Assert;

public class UndoRedoCareTakerTest {
    private final ReadOnlyAddressBook addressBookWithAmy = new AddressBookBuilder().withPerson(AMY).build();
    private final ReadOnlyAddressBook addressBookWithBob = new AddressBookBuilder().withPerson(BOB).build();
    private final ReadOnlyAddressBook addressBookWithCarl = new AddressBookBuilder().withPerson(CARL).build();
    private final ReadOnlyAddressBook emptyAddressBook = new AddressBookBuilder().build();

    @Test
    public void addNewState() {
        UndoRedoCareTaker undoRedoCareTaker = prepareCareTakerList(
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
        shiftCurrentStatePointerLeftwards(undoRedoCareTaker, 2);

        // all states after current state pointer removed, new state added to end of list
        undoRedoCareTaker.addNewState(addressBookWithCarl);
        assertCareTakerStatus(undoRedoCareTaker, Collections.singletonList(addressBookWithCarl));

        // current state pointer at end of list -> no state removed, new state added to end of list
        undoRedoCareTaker.addNewState(addressBookWithBob);
        assertCareTakerStatus(undoRedoCareTaker, Arrays.asList(addressBookWithCarl, addressBookWithBob));
    }

    @Test
    public void canUndo() {
        UndoRedoCareTaker undoRedoCareTaker = new UndoRedoCareTaker(emptyAddressBook);

        // single address book in care taker list
        assertFalse(undoRedoCareTaker.canUndo());

        // multiple address book in care taker list, current pointer not at index 0
        undoRedoCareTaker.addNewState(addressBookWithAmy);
        assertTrue(undoRedoCareTaker.canUndo());

        // multiple address book in care taker list, current pointer at index 0, start of list
        shiftCurrentStatePointerLeftwards(undoRedoCareTaker, 1);
        assertFalse(undoRedoCareTaker.canUndo());
    }

    @Test
    public void canRedo() {
        UndoRedoCareTaker undoRedoCareTaker = new UndoRedoCareTaker(emptyAddressBook);

        // single address book in care taker list
        assertFalse(undoRedoCareTaker.canRedo());

        // multiple address book in care taker list, current pointer at end of list
        undoRedoCareTaker.addNewState(addressBookWithAmy);
        assertFalse(undoRedoCareTaker.canRedo());

        // multiple address book in care taker list, current pointer not at end of list
        shiftCurrentStatePointerLeftwards(undoRedoCareTaker, 1);
        assertTrue(undoRedoCareTaker.canRedo());
    }

    @Test
    public void undo() {
        UndoRedoCareTaker undoRedoCareTaker = prepareCareTakerList(
                Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // current state pointer at index 2 -> return address book at index 1
        assertEquals(addressBookWithAmy, undoRedoCareTaker.undo());

        // current state pointer at index 1 -> return address book at index 0
        assertEquals(emptyAddressBook, undoRedoCareTaker.undo());

        // current state pointer at index 0, start of list -> fail
        Assert.assertThrows(UndoRedoCareTaker.NoUndoableStateException.class, undoRedoCareTaker::undo);
    }

    @Test
    public void redo() {
        UndoRedoCareTaker undoRedoCareTaker = prepareCareTakerList(
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
        shiftCurrentStatePointerLeftwards(undoRedoCareTaker, 2);

        // current state pointer at index 0 -> return address book at index 1
        assertEquals(addressBookWithAmy, undoRedoCareTaker.redo());

        // current state pointer at index 1 -> return address book at index 2
        assertEquals(addressBookWithBob, undoRedoCareTaker.redo());

        // current state pointer at index 2, end of list -> fail
        Assert.assertThrows(UndoRedoCareTaker.NoRedoableStateException.class, undoRedoCareTaker::redo);
    }

    @Test
    public void equals() {
        UndoRedoCareTaker undoRedoCareTaker = prepareCareTakerList(
                Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // same values -> returns true
        UndoRedoCareTaker copy = prepareCareTakerList(Arrays.asList(addressBookWithAmy, addressBookWithBob));
        assertTrue(undoRedoCareTaker.equals(copy));

        // same object -> returns true
        assertTrue(undoRedoCareTaker.equals(undoRedoCareTaker));

        // null -> returns false
        assertFalse(undoRedoCareTaker.equals(null));

        // different types -> returns false
        assertFalse(undoRedoCareTaker.equals(1));

        // different care taker list -> returns false
        UndoRedoCareTaker differentCareTakerList = prepareCareTakerList(
                Arrays.asList(addressBookWithBob, addressBookWithCarl));
        assertFalse(undoRedoCareTaker.equals(differentCareTakerList));

        // different current pointer index -> returns false
        UndoRedoCareTaker differentCurrentStatePointer = prepareCareTakerList(
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
        shiftCurrentStatePointerLeftwards(undoRedoCareTaker, 1);
        assertFalse(undoRedoCareTaker.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code undoRedoCareTaker} list content equals {@code expectedAddressBookStates}.
     */
    private void assertCareTakerStatus(UndoRedoCareTaker undoRedoCareTaker,
                                       List<ReadOnlyAddressBook> expectedAddressBookStates) {
        // shift currentStatePointer to the start of list
        while (undoRedoCareTaker.canUndo()) {
            undoRedoCareTaker.undo();
        }

        expectedAddressBookStates.forEach(expectedAddressBook ->
                assertEquals(expectedAddressBook, undoRedoCareTaker.redo()));
    }

    /**
     * Creates and returns an {@code UndoRedoCareTaker} with an empty address book and then {@code addressBookStates}
     * added into it, with the {@code undoRedoCareTaker#currentStatePointer} at the end of list.
     */
    private UndoRedoCareTaker prepareCareTakerList(List<ReadOnlyAddressBook> addressBookStates) {
        UndoRedoCareTaker undoRedoCt = new UndoRedoCareTaker(new AddressBook());
        addressBookStates.forEach(undoRedoCt::addNewState);
        return undoRedoCt;
    }

    /**
     * Shifts the {@code undoRedoCareTaker#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(UndoRedoCareTaker undoRedoCareTaker, int count) {
        for (int i = 0; i < count; i++) {
            undoRedoCareTaker.undo();
        }
    }
}
