package seedu.address.model;

import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        UndoRedoCareTaker undoRedoCareTaker = new UndoRedoCareTaker(emptyAddressBook);
        prepareCareTakerList(undoRedoCareTaker, Arrays.asList(
                addressBookWithAmy, addressBookWithBob));

        // add new state, current state pointer at end of list -> new state added, no states removed
        undoRedoCareTaker.addNewState(addressBookWithAmy);
        assertCareTakerStatus(undoRedoCareTaker,
                Arrays.asList(addressBookWithAmy, addressBookWithBob, addressBookWithAmy));

        // add new state, current state pointer not at end of list -> new state added, states after current
        // state pointer removed
        shiftCurrentStatePointerLeftWards(undoRedoCareTaker, 2);
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
        shiftCurrentStatePointerLeftWards(undoRedoCareTaker, 1);
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
        shiftCurrentStatePointerLeftWards(undoRedoCareTaker, 1);
        assertTrue(undoRedoCareTaker.canRedo());
    }

    @Test
    public void undo() {
        UndoRedoCareTaker undoRedoCareTaker = new UndoRedoCareTaker(emptyAddressBook);
        prepareCareTakerList(undoRedoCareTaker, Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // current pointer at index 2
        assertPopUndoSuccess(undoRedoCareTaker, addressBookWithAmy,
                Arrays.asList(addressBookWithAmy, addressBookWithBob), 1);

        // current pointer at index 1
        assertPopUndoSuccess(undoRedoCareTaker, emptyAddressBook,
                Arrays.asList(addressBookWithAmy, addressBookWithBob), 2);

        // current pointer at index 0, start of list
        assertPopUndoFailure(undoRedoCareTaker, Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void redo() {
        UndoRedoCareTaker undoRedoCareTaker = new UndoRedoCareTaker(emptyAddressBook);
        prepareCareTakerList(undoRedoCareTaker, Arrays.asList(addressBookWithAmy, addressBookWithBob));
        shiftCurrentStatePointerLeftWards(undoRedoCareTaker, 2);

        // current pointer at index 0
        assertPopRedoSuccess(undoRedoCareTaker, addressBookWithAmy,
                Arrays.asList(addressBookWithAmy, addressBookWithBob), 1);

        // current pointer at index 1
        assertPopRedoSuccess(undoRedoCareTaker, addressBookWithBob,
                Arrays.asList(addressBookWithAmy, addressBookWithBob), 0);

        // current pointer at index 2, end of list
        assertPopRedoFailure(undoRedoCareTaker, Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void equals() {
        UndoRedoCareTaker undoRedoCareTaker = new UndoRedoCareTaker(emptyAddressBook);
        prepareCareTakerList(undoRedoCareTaker, Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // same values -> returns true
        UndoRedoCareTaker copy = new UndoRedoCareTaker(emptyAddressBook);
        prepareCareTakerList(copy, Arrays.asList(addressBookWithAmy, addressBookWithBob));
        assertTrue(undoRedoCareTaker.equals(copy));

        // same object -> returns true
        assertTrue(undoRedoCareTaker.equals(undoRedoCareTaker));

        // null -> returns false
        assertFalse(undoRedoCareTaker.equals(null));

        // different types -> returns false
        assertFalse(undoRedoCareTaker.equals(1));

        // different care taker list -> returns false
        UndoRedoCareTaker differentCareTakerList = new UndoRedoCareTaker(emptyAddressBook);
        prepareCareTakerList(differentCareTakerList, Arrays.asList(addressBookWithBob, addressBookWithCarl));
        assertFalse(undoRedoCareTaker.equals(differentCareTakerList));

        // different current pointer index -> returns false
        UndoRedoCareTaker differentCurrentStatePointer = new UndoRedoCareTaker(emptyAddressBook);
        prepareCareTakerList(differentCurrentStatePointer, Arrays.asList(addressBookWithBob, addressBookWithAmy));
        shiftCurrentStatePointerLeftWards(differentCurrentStatePointer, 1);
        assertFalse(undoRedoCareTaker.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that the result of {@code undoRedoCareTaker#undo(ReadOnlyAddressBook)} equals
     * {@code expectedAddressBook}.
     * Also asserts that the content of the
     * {@code undoRedoCareTaker#addressBookStateList} equals {@code expectedAddressBookStates} and
     * {@code undoRedoCareTaker#currentStatePointer} is {@code count} to the left of the end of list.
     */
    private void assertPopUndoSuccess(UndoRedoCareTaker undoRedoCareTaker,
                                      ReadOnlyAddressBook expectedAddressBook,
                                      List<ReadOnlyAddressBook> expectedAddressBookStates,
                                      int count) {
        assertEquals(expectedAddressBook, undoRedoCareTaker.undo());
        assertCareTakerStatus(undoRedoCareTaker, expectedAddressBookStates, count);
    }

    /**
     * Asserts that the result of {@code undoRedoCareTaker#redo(ReadOnlyAddressBook)} equals
     * {@code expectedAddressBook}.
     * Also asserts that the content of the
     * {@code undoRedoCareTaker#addressBookStateList} equals {@code expectedAddressBookStates} and
     * {@code undoRedoCareTaker#currentStatePointer} is {@code count} to the left of the end of list.
     */
    private void assertPopRedoSuccess(UndoRedoCareTaker undoRedoCareTaker,
                                      ReadOnlyAddressBook expectedAddressBook,
                                      List<ReadOnlyAddressBook> expectedAddressBookStates,
                                      int count) {
        assertEquals(expectedAddressBook, undoRedoCareTaker.redo());
        assertCareTakerStatus(undoRedoCareTaker, expectedAddressBookStates, count);
    }

    /**
     * Asserts that the execution of {@code undoRedoCareTaker#undo(ReadOnlyAddressBook)} fails and
     * {@code UndoRedoCareTaker#NoUndoableStateException} is thrown.
     * Also asserts that the content of the
     * {@code undoRedoCareTaker#addressBookStateList} equals {@code expectedAddressBookStates} and
     * {@code undoRedoCareTaker#currentStatePointer} is at the start of the list.
     */
    private void assertPopUndoFailure(UndoRedoCareTaker undoRedoCareTaker,
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
    private void assertPopRedoFailure(UndoRedoCareTaker undoRedoCareTaker,
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
        UndoRedoCareTaker expectedCareTaker = new UndoRedoCareTaker(new AddressBook());
        prepareCareTakerList(expectedCareTaker, addressBookStates);
        shiftCurrentStatePointerLeftWards(expectedCareTaker, count);
        assertEquals(expectedCareTaker, undoRedoCareTaker);
    }

    /**
     * Adds {@code addressBookStates} to the care taker list in {@code undoRedoCareTaker}.
     */
    private void prepareCareTakerList(UndoRedoCareTaker undoRedoCareTaker,
                                      List<ReadOnlyAddressBook> addressBookStates) {
        addressBookStates.forEach(undoRedoCareTaker::addNewState);
    }

    /**
     * Shifts the {@code undoRedoCareTaker#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftWards(UndoRedoCareTaker undoRedoCareTaker, int count) {
        for (int i = 0; i < count; i++) {
            undoRedoCareTaker.undo();
        }
    }
}
