package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a list of all address book states the app previously was in.
 * This class is based on the `CareTaker`class in the `Memento` design pattern.
 * @see <a href="https://www.tutorialspoint.com/design_pattern/memento_pattern.htm">Memento design pattern tutorial</a>
 */
public class UndoRedoCareTaker {
    private List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    public UndoRedoCareTaker(ReadOnlyAddressBook initialState) {
        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Adds the {@code newState} to end of the list and clears any redundant address book states if there are changes
     * to the address book.
     */
    public void addNewState(ReadOnlyAddressBook newState) {
        removeRedundantStates();
        addressBookStateList.add(new AddressBook(newState));
        currentStatePointer++;
    }

    /**
     * Remove all address book states that are no longer needed.
     */
    public void removeRedundantStates() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    /**
     * Returns the previous address book state in the list.
     */
    public ReadOnlyAddressBook undo() {
        currentStatePointer--;
        return addressBookStateList.get(currentStatePointer);
    }

    /**
     * Returns the next address book state in the list.
     */
    public ReadOnlyAddressBook redo() {
        currentStatePointer++;
        return addressBookStateList.get(currentStatePointer);
    }

    /**
     * Returns true if {@code undo()} has address book states to return.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to return.
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
        if (!(other instanceof UndoRedoCareTaker)) {
            return false;
        }

        UndoRedoCareTaker otherUndoRedoCareTaker = (UndoRedoCareTaker) other;

        // state check
        return addressBookStateList.equals(otherUndoRedoCareTaker.addressBookStateList)
                && currentStatePointer == otherUndoRedoCareTaker.currentStatePointer;
    }
}
