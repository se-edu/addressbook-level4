package seedu.address.model.person;

import seedu.address.model.Tag;
import seedu.address.model.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    Name getName();
    Phone getPhone();
    Email getEmail();
    Address getAddress();
    void update(ReadOnlyPerson person);

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Convenience method for comparing equality via state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
                && other.getAddress().equals(this.getAddress()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        final String detailIsPrivate = "(private) ";
        builder.append(getName())
                .append(" Phone: ");
        if (getPhone().isPrivate()) {
            builder.append(detailIsPrivate);
        }
        builder.append(getPhone())
                .append(" Email: ");
        if (getEmail().isPrivate()) {
            builder.append(detailIsPrivate);
        }
        builder.append(getEmail())
                .append(" Address: ");
        if (getAddress().isPrivate()) {
            builder.append(detailIsPrivate);
        }
        builder.append(getAddress())
                .append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }

    /**
     * Formats a person as text, showing only non-private contact details.
     */
    default String getAsTextHidePrivate() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (getPhone().isVisible()) {
            builder.append(" Phone: ").append(getPhone());
        }
        if (getEmail().isVisible()) {
            builder.append(" Email: ").append(getEmail());
        }
        if (getAddress().isVisible()) {
            builder.append(" Address: ").append(getAddress());
        }
        builder.append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }

    /**
     * @return string representation of this Person's tags
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
