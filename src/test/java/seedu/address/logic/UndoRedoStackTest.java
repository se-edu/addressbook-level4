package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;

import org.junit.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.testutil.AddressBookBuilder;

public class UndoRedoStackTest {
    private final ReadOnlyAddressBook addressBookWithAmy = new AddressBookBuilder().withPerson(AMY).build();
    private final ReadOnlyAddressBook addressBookWithBob = new AddressBookBuilder().withPerson(BOB).build();
    private final ReadOnlyAddressBook emptyAddressBook = new AddressBookBuilder().build();

    private UndoRedoStack undoRedoStack = new UndoRedoStack();

    @Test
    public void push() {
        // empty undoRedoStack
        undoRedoStack = prepareStack(Collections.emptyList(), Collections.emptyList());
        undoRedoStack.push(addressBookWithAmy);
        assertStackStatus(Collections.singletonList(addressBookWithAmy), Collections.emptyList());

        // non-empty undoRedoStack
        undoRedoStack.push(addressBookWithBob);
        assertStackStatus(Arrays.asList(addressBookWithAmy, addressBookWithBob), Collections.emptyList());
    }

    @Test
    public void canUndo() {
        // empty undo stack
        assertFalse(undoRedoStack.canUndo());

        // non-empty undo stack
        undoRedoStack = prepareStack(Collections.singletonList(addressBookWithAmy), Collections.emptyList());
        assertTrue(undoRedoStack.canUndo());
    }

    @Test
    public void canRedo() {
        // empty redo stack
        assertFalse(undoRedoStack.canRedo());

        // non-empty redo stack
        undoRedoStack = prepareStack(Collections.emptyList(), Collections.singletonList(addressBookWithAmy));
        assertTrue(undoRedoStack.canRedo());
    }

    @Test
    public void popUndo() {
        undoRedoStack = prepareStack(Arrays.asList(addressBookWithAmy, addressBookWithBob),
                Collections.emptyList());

        // multiple address books in undoStack
        assertPopUndoSuccess(addressBookWithAmy, Collections.singletonList(addressBookWithAmy),
                Collections.singletonList(addressBookWithBob));

        // single address book in undoStack
        assertPopUndoSuccess(emptyAddressBook, Collections.emptyList(),
                Arrays.asList(addressBookWithBob, addressBookWithAmy));

        // no address book in undoStack
        assertPopUndoFailure(Collections.emptyList(), Arrays.asList(addressBookWithBob, addressBookWithAmy));
    }

    @Test
    public void popRedo() {
        undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // multiple address books in redoStack
        assertPopRedoSuccess(addressBookWithBob, Collections.singletonList(addressBookWithBob),
                Collections.singletonList(addressBookWithAmy));

        // single address book in redoStack
        assertPopRedoSuccess(addressBookWithAmy,
                Arrays.asList(addressBookWithBob, addressBookWithAmy), Collections.emptyList());

        // no address book in redoStack
        assertPopRedoFailure(Arrays.asList(addressBookWithBob, addressBookWithAmy), Collections.emptyList());
    }

    @Test
    public void clearRedoStack() {
        undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
        assertTrue(undoRedoStack.canRedo());

        undoRedoStack.clearRedoStack();
        assertFalse(undoRedoStack.canRedo());
    }

    @Test
    public void equals() {
        undoRedoStack = prepareStack(Arrays.asList(addressBookWithAmy, addressBookWithBob),
                Arrays.asList(addressBookWithBob, addressBookWithAmy));

        // same values -> returns true
        UndoRedoStack copy = prepareStack(Arrays.asList(addressBookWithAmy, addressBookWithBob),
                Arrays.asList(addressBookWithBob, addressBookWithAmy));
        assertTrue(undoRedoStack.equals(copy));

        // same object -> returns true
        assertTrue(undoRedoStack.equals(undoRedoStack));

        // null -> returns false
        assertFalse(undoRedoStack.equals(null));

        // different types -> returns false
        assertFalse(undoRedoStack.equals(1));

        // different undoStack -> returns false
        UndoRedoStack differentUndoStack = prepareStack(Collections.singletonList(addressBookWithBob),
                Arrays.asList(addressBookWithBob, addressBookWithAmy));
        assertFalse(undoRedoStack.equals(differentUndoStack));

        // different redoStack -> returns false
        UndoRedoStack differentRedoStack =
                prepareStack(Arrays.asList(addressBookWithAmy, addressBookWithBob),
                Collections.singletonList(addressBookWithBob));
        assertFalse(undoRedoStack.equals(differentRedoStack));
    }

    /**
     * Asserts that the result of {@code undoRedoStack#popUndo(ReadOnlyAddressBook)} equals {@code expectedAddressBook}.
     * Also asserts that the content of the {@code undoRedoStack#undoStack} equals {@code undoElements},
     * and {@code undoRedoStack#redoStack} equals {@code redoElements}.
     */
    private void assertPopUndoSuccess(ReadOnlyAddressBook expectedAddressBook,
                                      List<ReadOnlyAddressBook> expectedUndoElements,
                                      List<ReadOnlyAddressBook> expectedRedoElements) {
        assertEquals(expectedAddressBook, undoRedoStack.popUndo());
        assertStackStatus(expectedUndoElements, expectedRedoElements);
    }

    /**
     * Asserts that the result of {@code undoRedoStack#popRedo(ReadOnlyAddressBook)} equals {@code expectedAddressBook}.
     * Also asserts that the content of the {@code undoRedoStack#undoStack} equals {@code undoElements},
     * and {@code undoRedoStack#redoStack} equals {@code redoElements}.
     */
    private void assertPopRedoSuccess(ReadOnlyAddressBook expectedAddressBook,
                                      List<ReadOnlyAddressBook> expectedUndoElements,
                                      List<ReadOnlyAddressBook> expectedRedoElements) {
        assertEquals(expectedAddressBook, undoRedoStack.popRedo());
        assertStackStatus(expectedUndoElements, expectedRedoElements);
    }

    /**
     * Asserts that the execution of {@code undoRedoStack#popUndo(ReadOnlyAddressBook)} fails and
     * {@code EmptyStackException} is thrown.
     * Also asserts that the content of the {@code undoRedoStack#undoStack} equals {@code undoElements},
     * and {@code undoRedoStack#redoStack} equals {@code redoElements}.
     */
    private void assertPopUndoFailure(List<ReadOnlyAddressBook> expectedUndoElements,
                                      List<ReadOnlyAddressBook> expectedRedoElements) {
        try {
            undoRedoStack.popUndo();
            fail("The expected EmptyStackException was not thrown.");
        } catch (EmptyStackException ese) {
            // push back initial empty address book back into undo-stack
            undoRedoStack.popRedo();
            assertStackStatus(expectedUndoElements, expectedRedoElements);
        }
    }

    /**
     * Asserts that the execution of {@code undoRedoStack#popRedo(ReadOnlyAddressBook)} fails and
     * {@code EmptyStackException} is thrown.
     * Also asserts that the content of the {@code undoRedoStack#undoStack} equals {@code undoElements},
     * and {@code undoRedoStack#redoStack} equals {@code redoElements}.
     */
    private void assertPopRedoFailure(List<ReadOnlyAddressBook> expectedUndoElements,
                                      List<ReadOnlyAddressBook> expectedRedoElements) {
        try {
            undoRedoStack.popRedo();
            fail("The expected EmptyStackException was not thrown.");
        } catch (EmptyStackException ese) {
            assertStackStatus(expectedUndoElements, expectedRedoElements);
        }
    }

    /**
     * Asserts that {@code undoRedoStack#undoStack} equals {@code undoElements}, and {@code undoRedoStack#redoStack}
     * equals {@code redoElements}.
     */
    private void assertStackStatus(List<ReadOnlyAddressBook> undoElements, List<ReadOnlyAddressBook> redoElements) {
        assertEquals(prepareStack(undoElements, redoElements), undoRedoStack);
    }
}
