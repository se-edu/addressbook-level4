package seedu.address.model.task;

import java.util.Optional;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the todo.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask extends Comparable<ReadOnlyTask>{

    Name getName();
    Optional<Time> getTime();
    Period getPeriod();
    Description getDescription();
    Location getLocation();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getTime().equals(this.getTime())
                && other.getPeriod().equals(this.getPeriod())
                && other.getDescription().equals(this.getDescription())
                && other.getLocation().equals(this.getLocation()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getTime()) //to be refactored to Date
                .append(" Time: ")
                .append(getPeriod())
                .append(" Description: ")
                .append(getDescription())
                .append(" Location: ")
                .append(getLocation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    
    /**
     * Formats the task for display in browser, showing all contact details.
     */
    default String getAsHTML() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<html> <body>")
                .append("<h1>" + getName() + "</h1>")
                .append("<br> Time: ")
                .append(getTime())
                .append("<br> Description: ")
                .append(getDescription())
                .append("<br> Location: ")
                .append(getLocation())
                .append("<br> Tags: ");
        getTags().forEach(builder::append);
        builder.append("</body> </html>");
        return builder.toString();
    }

    /**
     * Returns a string representation of this Task's tags
     */
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getTags().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }
    
    @Override
    public default int compareTo(ReadOnlyTask o) {
        if(!this.getTime().isPresent()){
            return -1;
        }else if(!o.getTime().isPresent()){
            return 1;
        }else{
            return this.getTime().get().compareTo(o.getTime().get());
        }
    }

}
