package seedu.address.model.tag;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;


/**
 * A read-only immutable interface for a list of unique tags.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyUniqueTagList extends Iterable<Tag> {

    /**
     * Returns all tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    Set<Tag> toSet();

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    boolean contains(Tag toCheck);

    UnmodifiableObservableList<Tag> asObservableList();

    boolean equalsOrderInsensitive(ReadOnlyUniqueTagList other);

}
