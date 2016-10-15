package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the SmartyDo.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Time time;
    private Period period;
    private Description description;
    private Location location;
    private boolean isCompleted;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Time time, Period period, Description description, Location location, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, time, period, description, location, tags);

        this.name = name;
        this.time = time;
        this.period = period;
        this.description = description;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isCompleted = false;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTime(), source.getPeriod(), source.getDescription(),
             source.getLocation(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public Period getPeriod() {
        return period;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Location getLocation() {
        return location;
    }

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
        return Objects.hash(name, time, description, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
