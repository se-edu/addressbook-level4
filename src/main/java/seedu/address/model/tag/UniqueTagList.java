package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Tag#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTagList implements Iterable<Tag> {

    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TagList.
     */
    public UniqueTagList() {}

    /**
     * Creates a UniqueTagList using given String tags.
     * Enforces no nulls or duplicates.
     */
    public UniqueTagList(String... tags) throws DuplicateTagException, IllegalValueException {
        final List<Tag> tagList = new ArrayList<Tag>();
        for (String tag : tags) {
            tagList.add(new Tag(tag));
        }
        setTags(tagList);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no nulls or duplicates.
     */
    public UniqueTagList(Tag... tags) throws DuplicateTagException {
        requireAllNonNull((Object[]) tags);
        final List<Tag> initialTags = Arrays.asList(tags);
        if (!CollectionUtil.elementsAreUnique(initialTags)) {
            throw new DuplicateTagException();
        }
        internalList.addAll(initialTags);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no null or duplicate elements.
     */
    public UniqueTagList(Collection<Tag> tags) throws DuplicateTagException {
        this();
        setTags(tags);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no nulls.
     */
    public UniqueTagList(Set<Tag> tags) {
        requireAllNonNull(tags);
        internalList.addAll(tags);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Creates a copy of the given list.
     * Insulates from changes in source.
     */
    public UniqueTagList(UniqueTagList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Tag> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        this.internalList.setAll(replacement.internalList);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    public void setTags(Collection<Tag> tags) throws DuplicateTagException {
        requireAllNonNull(tags);
        if (!CollectionUtil.elementsAreUnique(tags)) {
            throw new DuplicateTagException();
        }
        internalList.setAll(tags);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueTagList from) {
        final Set<Tag> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(tag -> !alreadyInside.contains(tag))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Tag to the list.
     *
     * @throws DuplicateTagException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(Tag toAdd) throws DuplicateTagException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Tag> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    public UnmodifiableObservableList<Tag> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueTagList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTagList) other).internalList));
    }

    public boolean equalsOrderInsensitive(UniqueTagList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException() {
            super("Operation would result in duplicate tags");
        }
    }

}
