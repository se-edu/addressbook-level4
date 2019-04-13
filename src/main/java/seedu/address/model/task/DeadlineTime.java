package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.ModelManager.isValidTime;


/**
 * DeadlineTime class
 */
public class DeadlineTime {

    public static final String MESSAGE_CONSTRAINTS = "Deadline Time should contain 4 Integers";
    /* The input time should contain exactly 4 digits */
    public static final String MESSAGE_CONSTRAINTS_INVALID_TIME = "Deadline Time is not"
            + " within the range of the 24HR Format";
    public static final String VALIDATION_REGEX = "\\d{4}";
    public final String value;

    /**
     * Constructs an {@code DeadlineTime}.
     * @param deadlineTime a valid deadlineTime
     */
    public DeadlineTime(String deadlineTime) {
        requireNonNull(deadlineTime);
        checkArgument(isValidDeadlineTimeInput(deadlineTime), MESSAGE_CONSTRAINTS);
        checkArgument(isValidDeadlineTime(deadlineTime), MESSAGE_CONSTRAINTS_INVALID_TIME);
        value = deadlineTime;
    }

    /**
     * Returns true if a given string is 4 integers
     */
    public static boolean isValidDeadlineTimeInput(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is a valid time
     */
    public static boolean isValidDeadlineTime(String test) {
        return isValidTime(test);
    }

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
