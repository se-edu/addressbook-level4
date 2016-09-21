package seedu.address.model;


import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    UniqueTagList getUniqueTagList();

    UniquePersonList getUniquePersonList();

    /**
     * @return unmodifiable view of persons list
     */
    List<ReadOnlyPerson> getPersonList();

    /**
     * @return unmodifiable view of tags list
     */
    List<Tag> getTagList();

//// below method implementations are optional; override if functionality is needed.

    /**
     * @return all persons in backing model IN AN UNMODIFIABLE VIEW
     */
    default UnmodifiableObservableList<ReadOnlyPerson> getPersonsAsReadOnlyObservableList() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return all tags in backing model IN AN UNMODIFIABLE VIEW
     */
    default UnmodifiableObservableList<Tag> getTagsAsReadOnlyObservableList() {
        throw new UnsupportedOperationException();
    }

}
