package seedu.address.logic;

import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.AddressBookCareTaker;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Contains helper methods for testing {@code AddressBookCareTaker}.
 */
public class AddressBookCareTakerUtil {

    /**
     * Adds {@code addressBookStates} into {@code AddressBookCareTaker#addressBookStateList}, with an initial empty
     * address book.
     */
    public static AddressBookCareTaker prepareCareTakerList(List<ReadOnlyAddressBook> addressBookStates) {
        AddressBookCareTaker abct = new AddressBookCareTaker(new AddressBook());
        addressBookStates.forEach(abct::addNewState);
        return abct;
    }

    /**
     * Shifts the {@code AddressBookCareTaker#currentStatePointer} by {@code count} to the left of its list.
     */
    public static void shiftCurrentStatePointer(AddressBookCareTaker abct, int count) {
        for (int i = 0; i < count; i++) {
            abct.getUndoState();
        }
    }
}
