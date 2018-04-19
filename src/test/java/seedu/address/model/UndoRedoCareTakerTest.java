package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.AddressBookBuilder;

public class UndoRedoCareTakerTest {
    private final ReadOnlyAddressBook addressBookWithAmy = new AddressBookBuilder().withPerson(AMY).build();
    private final ReadOnlyAddressBook addressBookWithBob = new AddressBookBuilder().withPerson(BOB).build();
    private final ReadOnlyAddressBook addressBookWithCarl = new AddressBookBuilder().withPerson(CARL).build();
    private final ReadOnlyAddressBook emptyAddressBook = new AddressBookBuilder().build();

    @Test
    public void addNewState() {
        UndoRedoCareTaker undoRedoCareTaker = prepareCareTakerList(
                Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // add new state, current state pointer at end of list -> new state added, no states removed
        undoRedoCareTaker.addNewState(addressBookWithAmy);
        assertCareTakerStatus(undoRedoCareTaker,
                Arrays.asList(addressBookWithAmy, addressBookWithBob, addressBookWithAmy));

        // add new state, current state pointer not at end of list -> new state added, states after current
        // state pointer removed
        shiftCurrentStatePointerLeftwards(undoRedoCareTaker, 2);
        undoRedoCareTaker.addNewState(addressBookWithCarl);
        assertCareTakerStatus(undoRedoCareTaker, Arrays.asList(addressBookWithAmy, addressBookWithCarl));
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
        UndoRedoCareTaker expectedCareTaker = prepareCareTakerList(
                Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // current pointer at index 2
        shiftCurrentStatePointerLeftwards(expectedCareTaker, 1);
        assertPopUndoSuccess(expectedCareTaker, undoRedoCareTaker, addressBookWithAmy);

        // current pointer at index 1
        shiftCurrentStatePointerLeftwards(expectedCareTaker, 1);
        assertPopUndoSuccess(expectedCareTaker, undoRedoCareTaker, emptyAddressBook);

        // current pointer at index 0, start of list
        assertUndoFailure(undoRedoCareTaker, Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void redo() {
        UndoRedoCareTaker undoRedoCareTaker = prepareCareTakerList(
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
        UndoRedoCareTaker expectedCareTaker = prepareCareTakerList(
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
        shiftCurrentStatePointerLeftwards(undoRedoCareTaker, 2);
        shiftCurrentStatePointerLeftwards(expectedCareTaker, 2);

        // current pointer at index 0
        shiftCurrentStatePointerRightwards(expectedCareTaker, 1);
        assertPopRedoSuccess(expectedCareTaker, undoRedoCareTaker, addressBookWithAmy);

        // current pointer at index 1
        shiftCurrentStatePointerRightwards(expectedCareTaker, 1);
        assertPopRedoSuccess(expectedCareTaker, undoRedoCareTaker, addressBookWithBob);

        // current pointer at index 2, end of list
        assertRedoFailure(undoRedoCareTaker, Arrays.asList(addressBookWithAmy, addressBookWithBob));
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
                Arrays.asList(addressBookWithBob, addressBookWithAmy));
        shiftCurrentStatePointerLeftwards(differentCurrentStatePointer, 1);
        assertFalse(undoRedoCareTaker.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that the result of {@code undoRedoCareTaker#undo(ReadOnlyAddressBook)} equals
     * {@code expectedAddressBook}.
     * Also asserts that the {@code expectedCareTaker} equals {@code undoRedoCareTaker}.
     */
    private void assertPopUndoSuccess(UndoRedoCareTaker expectedCareTaker,
                                      UndoRedoCareTaker undoRedoCareTaker,
                                      ReadOnlyAddressBook expectedAddressBook) {
        assertEquals(expectedAddressBook, undoRedoCareTaker.undo());
        assertEquals(expectedCareTaker, undoRedoCareTaker);
    }

    /**
     * Asserts that the result of {@code undoRedoCareTaker#redo(ReadOnlyAddressBook)} equals
     * {@code expectedAddressBook}.
     * Also asserts that the {@code expectedCareTaker} equals {@code undoRedoCareTaker}.
     */
    private void assertPopRedoSuccess(UndoRedoCareTaker expectedCareTaker,
                                      UndoRedoCareTaker undoRedoCareTaker,
                                      ReadOnlyAddressBook expectedAddressBook) {
        assertEquals(expectedAddressBook, undoRedoCareTaker.redo());
        assertEquals(expectedCareTaker, undoRedoCareTaker);
    }

    /**
     * Asserts that the execution of {@code undoRedoCareTaker#undo(ReadOnlyAddressBook)} fails and
     * {@code UndoRedoCareTaker#NoUndoableStateException} is thrown.
     * Also asserts that the content of the
     * {@code undoRedoCareTaker#addressBookStateList} equals {@code expectedAddressBookStates} and
     * {@code undoRedoCareTaker#currentStatePointer} is at the start of the list.
     */
    private void assertUndoFailure(UndoRedoCareTaker undoRedoCareTaker,
                                   List<ReadOnlyAddressBook> expectedAddressBookStates) {
        try {
            undoRedoCareTaker.undo();
            fail("The expected IndexOutOfBoundsException was not thrown.");
        } catch (UndoRedoCareTaker.NoUndoableStateException nuse) {
            assertCareTakerStatus(undoRedoCareTaker, expectedAddressBookStates, expectedAddressBookStates.size());
        }
    }

    /**
     * Asserts that the execution of {@code undoRedoCareTaker#redo(ReadOnlyAddressBook)} fails and
     * {@code UndoRedoCareTaker#NoRedoableStateException} is thrown.
     *
     * Also asserts that the content of the
     * {@code undoRedoCareTaker#addressBookStateList} equals {@code expectedAddressBookStates} and
     * {@code undoRedoCareTaker#currentStatePointer} is at the end of the list.
     */
    private void assertRedoFailure(UndoRedoCareTaker undoRedoCareTaker,
                                   List<ReadOnlyAddressBook> expectedAddressBookStates) {
        try {
            undoRedoCareTaker.redo();
            fail("The expected IndexOutOfBoundsException was not thrown.");
        } catch (UndoRedoCareTaker.NoRedoableStateException nrse) {
            assertCareTakerStatus(undoRedoCareTaker, expectedAddressBookStates);
        }
    }

    /**
     * Asserts that {@code undoRedoCareTaker} list content equals {@code addressBookStates}.
     * {@code undoRedoCareTaker#currentStatePointer} should be at the end of the care taker list.
     */
    private void assertCareTakerStatus(UndoRedoCareTaker undoRedoCareTaker,
                                       List<ReadOnlyAddressBook> addressBookStates) {
        assertCareTakerStatus(undoRedoCareTaker, addressBookStates, 0);
    }

    /**
     * Asserts that {@code undoRedoCareTaker} list content equals {@code addressBookStates}, and that
     * {@code undoRedoCareTaker#currentStatePointer} is {@code count} to the left of the end of list.
     */
    private void assertCareTakerStatus(UndoRedoCareTaker undoRedoCareTaker,
                                       List<ReadOnlyAddressBook> addressBookStates,
                                       int count) {
        UndoRedoCareTaker expectedCareTaker = prepareCareTakerList(addressBookStates);
        shiftCurrentStatePointerLeftwards(expectedCareTaker, count);
        assertEquals(expectedCareTaker, undoRedoCareTaker);
    }

    /**
     * Creates and returns an {@code UndoRedoCareTaker} with an empty address book and then {@code addressBookStates}
     * added into it.
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

    /**
     * Shifts the {@code undoRedoCareTaker#currentStatePointer} by {@code count} to the right of its list.
     */
    private void shiftCurrentStatePointerRightwards(UndoRedoCareTaker undoRedoCareTaker, int count) {
        for (int i = 0; i < count; i++) {
            undoRedoCareTaker.redo();
        }
    }
}
