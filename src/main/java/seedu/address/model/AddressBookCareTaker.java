package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a list of all previous and current address book state to be restored.
 */
public class AddressBookCareTaker {
    private List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    public AddressBookCareTaker(ReadOnlyAddressBook initialState) {
        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(initialState);
        currentStatePointer = 0;
    }

    /**
     * If there are changes to the address book, increment the {@code currentStatePointer} and remove all address
     * book states at and after {@code currentStatePointer} index, then adds the {@code newState}
     * address book to the end of the list.
     */
    public void addNewState(ReadOnlyAddressBook newState) {
        if (newState.equals(addressBookStateList.get(currentStatePointer))) {
            return;
        }

        currentStatePointer++;
        removeRedundantStates();

        addressBookStateList.add(newState);
    }

    /**
     * Remove all states that can no longer be restored.
     */
    private void removeRedundantStates() {
        addressBookStateList.subList(currentStatePointer, addressBookStateList.size()).clear();
    }

    /**
     * Decrement the {@code currentStatePointer} to the previous address book state and returns that state.
     */
    public ReadOnlyAddressBook getUndoState() {
        currentStatePointer--;
        return addressBookStateList.get(currentStatePointer);
    }

    /**
     * Increments the {@code currentStatePointer} to the previous undone address book state and returns that state.
     */
    public ReadOnlyAddressBook getRedoState() {
        currentStatePointer++;
        return addressBookStateList.get(currentStatePointer);
    }

    /**
     * Returns true if there are more previous address book states to be restored.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if there are more previously undone address book states to be restored.
     */
    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBookCareTaker)) {
            return false;
        }

        AddressBookCareTaker abct = (AddressBookCareTaker) other;

        // state check
        return addressBookStateList.equals(abct.addressBookStateList)
                && currentStatePointer == abct.currentStatePointer;
    }
}
