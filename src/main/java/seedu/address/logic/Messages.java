package seedu.address.logic;

import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append(" Phone: ")
                .append(person.getPhone())
                .append(" Email: ")
                .append(person.getEmail())
                .append(" Address: ")
                .append(person.getAddress())
                .append(" Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
