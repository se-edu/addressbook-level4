package seedu.address.model;

import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.AddressBookBuilder;

public class UndoRedoCareTakerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ReadOnlyAddressBook addressBookWithAmy = new AddressBookBuilder().withPerson(AMY).build();
    private final ReadOnlyAddressBook addressBookWithBob = new AddressBookBuilder().withPerson(BOB).build();
    private final ReadOnlyAddressBook addressBookWithCarl = new AddressBookBuilder().withPerson(CARL).build();
    private final ReadOnlyAddressBook emptyAddressBook = new AddressBookBuilder().build();

    private UndoRedoCareTaker undoRedoCareTaker = new UndoRedoCareTaker(new AddressBook());

    @Test
    public void addNewState() {
        // add to end of empty list
        undoRedoCareTaker = prepareCareTakerList(Collections.emptyList());
        undoRedoCareTaker.addNewState(addressBookWithAmy);
        assertCareTakerStatus(Collections.singletonList(addressBookWithAmy));

        // add to end of non-empty list
        undoRedoCareTaker.addNewState(addressBookWithBob);
        assertCareTakerStatus(Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void canUndo() {
        // initial care taker list
        assertFalse(undoRedoCareTaker.canUndo());

        // non-empty care taker, current pointer not 0
        undoRedoCareTaker = prepareCareTakerList(Collections.singletonList(addressBookWithAmy));
        assertTrue(undoRedoCareTaker.canUndo());
    }

    @Test
    public void canRedo() {
        // initial care taker list
        assertFalse(undoRedoCareTaker.canRedo());

        // current pointer at end of care taker list
        undoRedoCareTaker = prepareCareTakerList(Collections.singletonList(addressBookWithAmy));
        assertFalse(undoRedoCareTaker.canRedo());

        // current pointer before end of care taker list
        shiftCurrentStatePointerLeftWards(undoRedoCareTaker, 1);
        assertTrue(undoRedoCareTaker.canRedo());
    }

    @Test
    public void undo() {
        undoRedoCareTaker = prepareCareTakerList(Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // current pointer at index 2
        assertPopUndoSuccess(addressBookWithAmy, Arrays.asList(addressBookWithAmy, addressBookWithBob), 1);

        // current pointer at index 1
        assertPopUndoSuccess(emptyAddressBook, Arrays.asList(addressBookWithAmy, addressBookWithBob), 2);

        // current pointer at index 0, start of care taker list
        assertPopUndoFailure(Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void redo() {
        undoRedoCareTaker = prepareCareTakerList(Arrays.asList(addressBookWithAmy, addressBookWithBob));
        shiftCurrentStatePointerLeftWards(undoRedoCareTaker, 2);

        // current pointer at index 0
        assertPopRedoSuccess(addressBookWithAmy, Arrays.asList(addressBookWithAmy, addressBookWithBob), 1);

        // current pointer at index 1
        assertPopRedoSuccess(addressBookWithBob, Arrays.asList(addressBookWithAmy, addressBookWithBob), 0);

        // current pointer at index 2, end of care taker list
        assertPopRedoFailure(Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void equals() {
        undoRedoCareTaker = prepareCareTakerList(Arrays.asList(addressBookWithAmy, addressBookWithBob));

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
        shiftCurrentStatePointerLeftWards(differentCurrentStatePointer, 1);
        assertFalse(undoRedoCareTaker.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that the result of {@code undoRedoCareTaker#undo(ReadOnlyAddressBook)} equals
     * {@code expectedAddressBook}.
     */
    private void assertPopUndoSuccess(ReadOnlyAddressBook expectedAddressBook,
                                      List<ReadOnlyAddressBook> expectedAddressBookStates,
                                      int count) {
        assertEquals(expectedAddressBook, undoRedoCareTaker.undo());
        assertCareTakerStatus(expectedAddressBookStates, count);
    }

    /**
     * Asserts that the result of {@code undoRedoCareTaker#redo(ReadOnlyAddressBook)} equals
     * {@code expectedAddressBook}.
     */
    private void assertPopRedoSuccess(ReadOnlyAddressBook expectedAddressBook,
                                      List<ReadOnlyAddressBook> expectedAddressBookStates,
                                      int count) {
        assertEquals(expectedAddressBook, undoRedoCareTaker.redo());
        assertCareTakerStatus(expectedAddressBookStates, count);
    }

    /**
     * Asserts that the execution of {@code undoRedoCareTaker#undo(ReadOnlyAddressBook)} fails and
     * {@code IndexOutOfBoundsException} is thrown.
     * Also asserts that the content of the
     * {@code undoRedoCareTaker#addressBookStateList} equals {@code expectedAddressBookStates} and
     * {@code undoRedoCareTaker#currentStatePointer} is at the start of the list.
     */
    private void assertPopUndoFailure(List<ReadOnlyAddressBook> expectedAddressBookStates) {
        try {
            undoRedoCareTaker.undo();
            fail("The expected IndexOutOfBoundsException was not thrown.");
        } catch (IndexOutOfBoundsException iobe) {
            // shift current index pointer back to the valid position
            undoRedoCareTaker.redo();
            assertCareTakerStatus(expectedAddressBookStates, expectedAddressBookStates.size());
        }
    }

    /**
     * Asserts that the execution of {@code undoRedoCareTaker#redo(ReadOnlyAddressBook)} fails and
     * {@code IndexOutOfBoundsException} is thrown.
     *
     * Also asserts that the content of the
     * {@code undoRedoCareTaker#addressBookStateList} equals {@code expectedAddressBookStates} and
     * {@code undoRedoCareTaker#currentStatePointer} is at the end of the list.
     */
    private void assertPopRedoFailure(List<ReadOnlyAddressBook> expectedAddressBookStates) {
        try {
            undoRedoCareTaker.redo();
            fail("The expected IndexOutOfBoundsException was not thrown.");
        } catch (IndexOutOfBoundsException iobe) {
            // shift current index pointer back to the valid position
            undoRedoCareTaker.undo();
            assertCareTakerStatus(expectedAddressBookStates);
        }
    }

    /**
     * Asserts that {@code undoRedoCareTaker} list content equals {@code addressBookStates}.
     * {@code undoRedoCareTaker#currentStatePointer} should be at the end of the care taker list.
     */
    private void assertCareTakerStatus(List<ReadOnlyAddressBook> addressBookStates) {
        assertCareTakerStatus(addressBookStates, 0);
    }

    /**
     * Asserts that {@code undoRedoCareTaker} list content equals {@code addressBookStates}, and that
     * {@code undoRedoCareTaker#currentStatePointer} is {@code count} to the left of the end of list.
     */
    private void assertCareTakerStatus(List<ReadOnlyAddressBook> addressBookStates, int count) {
        UndoRedoCareTaker expectedCareTaker = prepareCareTakerList(addressBookStates);
        shiftCurrentStatePointerLeftWards(expectedCareTaker, count);
        assertEquals(expectedCareTaker, undoRedoCareTaker);
    }

    /**
     * Creates and returns an {@code UndoRedoCareTaker} with an empty address book and then {@code addressBookStates}
     * added into it.
     */
    private static UndoRedoCareTaker prepareCareTakerList(List<ReadOnlyAddressBook> addressBookStates) {
        UndoRedoCareTaker undoRedoCt = new UndoRedoCareTaker(new AddressBook());
        addressBookStates.forEach(undoRedoCt::addNewState);
        return undoRedoCt;
    }

    /**
     * Shifts the {@code undoRedoCt#currentStatePointer} by {@code count} to the left of its list.
     */
    private static void shiftCurrentStatePointerLeftWards(UndoRedoCareTaker undoRedoCt, int count) {
        for (int i = 0; i < count; i++) {
            undoRedoCt.undo();
        }
    }
}
