package seedu.address.model.habit;

import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Habit in the Habit Tracker list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Habit {
    private final HabitTitle name;
    private final Progress progress;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Habit(HabitTitle name, Progress progress, Set<Tag> tags) {
        requireAllNonNull(name, progress, tags);
        this.name = name;
        this.progress = progress;
        this.tags.addAll(tags);
    }

    public HabitTitle getHabitTitle() {
        return name;
    }

    public Progress getProgress() {
        return progress;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameHabit(Habit otherHabit) {
        if (otherHabit == this) {
            return true;
        }

        return otherHabit != null
                && otherHabit.getHabitTitle().equals(getHabitTitle())
                && (otherHabit.getProgress().equals(getProgress()));
    }


    /**
     * Returns true if both habits have the same identity and data fields.
     * This defines a stronger notion of equality between two habits.
     */
 /*   @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Habit)) {
            return false;
        }

        Habit otherHabit = (Habit) other;
        return otherHabit.getHabitTitle().equals(getHabitTitle())
                && otherHabit.getProgress().equals(getProgress())
                && otherHabit.getTags().equals(getTags());
    }
*/
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, progress, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getHabitTitle())
                .append(" Progress: ")
                .append(getProgress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}

