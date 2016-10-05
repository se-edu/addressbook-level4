package seedu.address.model.datetimepair;

import org.joda.time.DateTime;
import seedu.address.model.tag.UniqueTagList;

/**
 * Created by haliq on 5/10/16.
 */
public interface ReadOnlyDateTimePair {

    DateTime getOpen();
    DateTime getClose();
    String getName();
    boolean getFloat();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    default boolean isSameStateAs(ReadOnlyDateTimePair other){
        return other == this
                || (other != null
                && other.getName().equals(this.getName())
                && other.getOpen().isEqual(this.getOpen())
                && other.getClose().isEqual(this.getClose())
                && other.getFloat() == this.getFloat());
    }

    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Open: ")
                .append(DateTimePair.dateTimeToString(getOpen()))
                .append(" Close: ")
                .append(DateTimePair.dateTimeToString(getClose()))
                .append(" Float : ")
                .append(getFloat())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Person's tags
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
}
