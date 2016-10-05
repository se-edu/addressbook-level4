package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    //private DateTime openTime;
    //private DateTime closeTime;
    //private boolean isImportant;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags) {
        // open time, urgent, and close time can be null
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        //TODO: set default values
        //this.openTime = openTime;
        //this.closeTime = closeTime;
        //this.isImportant = isImportant;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }
    /**
    @Override
    public DateTime getOpenTime() {
        return openTime;
    }

    @Override
    public DateTime getCloseTime() {
        return closeTime;
    }

    @Override
    public boolean getImportance() {
        return isImportant;
    }
    **/
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        //return Objects.hash(name, openTime, closeTime, isImportant, tags);
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
