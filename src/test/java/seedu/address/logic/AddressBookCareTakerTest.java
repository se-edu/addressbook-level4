package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.AddressBookCareTakerUtil.prepareCareTakerList;
import static seedu.address.logic.AddressBookCareTakerUtil.shiftCurrentStatePointer;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.AddressBook;
import seedu.address.model.AddressBookCareTaker;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.testutil.AddressBookBuilder;

public class AddressBookCareTakerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ReadOnlyAddressBook addressBookWithAmy = new AddressBookBuilder().withPerson(AMY).build();
    private final ReadOnlyAddressBook addressBookWithBob = new AddressBookBuilder().withPerson(BOB).build();
    private final ReadOnlyAddressBook addressBookWithCarl = new AddressBookBuilder().withPerson(CARL).build();
    private final ReadOnlyAddressBook emptyAddressBook = new AddressBookBuilder().build();

    private AddressBookCareTaker addressBookCareTaker = new AddressBookCareTaker(new AddressBook());

    @Test
    public void addNewState_differentFromCurrentPointedAddressBook_addressBookAdded() {
        // add to end of care taker list
        addressBookCareTaker = prepareCareTakerList(Arrays.asList(addressBookWithBob, addressBookWithAmy));
        addressBookCareTaker.addNewState(addressBookWithCarl);
        assertCareTakerStatus(Arrays.asList(addressBookWithBob, addressBookWithAmy, addressBookWithCarl));

        // states after current pointer removed, add to end of care take list
        shiftCurrentStatePointer(addressBookCareTaker, 1);
        addressBookCareTaker.addNewState(addressBookWithBob);
        assertCareTakerStatus(Arrays.asList(addressBookWithBob, addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void addNewState_equalToCurrentPointedAddressBook_addressBookNotAdded() {
        addressBookCareTaker = prepareCareTakerList(Arrays.asList(addressBookWithBob, addressBookWithAmy));
        addressBookCareTaker.addNewState(addressBookWithAmy);
        assertCareTakerStatus(Arrays.asList(addressBookWithBob, addressBookWithAmy));
    }

    @Test
    public void canUndo() {
        // initial care taker list
        assertFalse(addressBookCareTaker.canUndo());

        // non-empty care taker, current pointer not 0
        addressBookCareTaker = prepareCareTakerList(Collections.singletonList(addressBookWithAmy));
        assertTrue(addressBookCareTaker.canUndo());
    }

    @Test
    public void canRedo() {
        // initial care taker list
        assertFalse(addressBookCareTaker.canRedo());

        // current pointer at end of care taker list
        addressBookCareTaker = prepareCareTakerList(Collections.singletonList(addressBookWithAmy));
        assertFalse(addressBookCareTaker.canRedo());

        // current pointer before end of care taker list
        shiftCurrentStatePointer(addressBookCareTaker, 1);
        assertTrue(addressBookCareTaker.canRedo());
    }

    @Test
    public void getUndoState() {
        addressBookCareTaker = prepareCareTakerList(Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // current pointer at index 2
        assertPopUndoSuccess(addressBookWithAmy);

        // current pointer at index 1
        assertPopUndoSuccess(emptyAddressBook);

        // current pointer at index 0
        assertPopUndoFailure();
    }

    @Test
    public void getRedoState() {
        addressBookCareTaker = prepareCareTakerList(Arrays.asList(addressBookWithAmy, addressBookWithBob));
        shiftCurrentStatePointer(addressBookCareTaker, 2);

        // current pointer at index 0, redoable pointer at index 2
        assertPopRedoSuccess(addressBookWithAmy);

        // current pointer at index 1, redoable pointer at index 2
        assertPopRedoSuccess(addressBookWithBob);

        // current pointer equal to redoable pointer
        assertPopRedoFailure();
    }

    @Test
    public void equals() {
        addressBookCareTaker = prepareCareTakerList(Arrays.asList(addressBookWithAmy, addressBookWithBob));

        // same values -> returns true
        AddressBookCareTaker copy = prepareCareTakerList(Arrays.asList(addressBookWithAmy, addressBookWithBob));
        assertTrue(addressBookCareTaker.equals(copy));

        // same object -> returns true
        assertTrue(addressBookCareTaker.equals(addressBookCareTaker));

        // null -> returns false
        assertFalse(addressBookCareTaker.equals(null));

        // different types -> returns false
        assertFalse(addressBookCareTaker.equals(1));

        // different care taker list -> returns false
        AddressBookCareTaker differentCareTakerList = prepareCareTakerList(
                Arrays.asList(addressBookWithBob, addressBookWithCarl));
        assertFalse(addressBookCareTaker.equals(differentCareTakerList));

        // different current pointer index -> returns false
        AddressBookCareTaker differentCurrentStatePointer = prepareCareTakerList(
                Arrays.asList(addressBookWithBob, addressBookWithAmy));
        shiftCurrentStatePointer(differentCurrentStatePointer, 1);
        assertFalse(addressBookCareTaker.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that the result of {@code addressBookCareTaker#getUndoState(ReadOnlyAddressBook)} equals
     * {@code expectedAddressBook}.
     */
    private void assertPopUndoSuccess(ReadOnlyAddressBook expectedAddressBook) {
        assertEquals(expectedAddressBook, addressBookCareTaker.getUndoState());
    }

    /**
     * Asserts that the result of {@code addressBookCareTaker#getRedoState(ReadOnlyAddressBook)} equals
     * {@code expectedAddressBook}.
     */
    private void assertPopRedoSuccess(ReadOnlyAddressBook expectedAddressBook) {
        assertEquals(expectedAddressBook, addressBookCareTaker.getRedoState());
    }

    /**
     * Asserts that the execution of {@code addressBookCareTaker#getUndoState(ReadOnlyAddressBook)} fails and
     * {@code IndexOutOfBoundsException} is thrown.
     */
    private void assertPopUndoFailure() {
        thrown.expect(IndexOutOfBoundsException.class);
        addressBookCareTaker.getUndoState();
    }

    /**
     * Asserts that the execution of {@code addressBookCareTaker#getRedoState(ReadOnlyAddressBook)} fails and
     * {@code IndexOutOfBoundsException} is thrown.
     */
    private void assertPopRedoFailure() {
        thrown.expect(IndexOutOfBoundsException.class);
        addressBookCareTaker.getRedoState();

    }

    /**
     * Asserts that {@code addressBookCareTaker} list equals {@code addressBookStates}.
     */
    private void assertCareTakerStatus(List<ReadOnlyAddressBook> addressBookStates) {
        assertEquals(prepareCareTakerList(addressBookStates), addressBookCareTaker);
    }
}
