package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


public class DeadlineTime {

    public static final String MESSAGE_CONSTRAINTS =
            "Time should only be in integers and should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
//    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

//    public final String fullName;
    public final String value;
    /**
     * Constructs a {@code Name}.
     *
     * @para time a valid time.
     */
    public DeadlineTime(String deadlineTime) {
        requireNonNull(deadlineTime);
       // checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        value = deadlineTime;
    }

//    /**
//     * Returns true if a given string is a valid name.
//     */
//    public static boolean isValidName(String test) {
//        return test.matches(VALIDATION_REGEX);
//    }
//
//
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineTime // instanceof handles nulls
                && value.equals(((DeadlineTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
