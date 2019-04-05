package seedu.address.model.task;

import seedu.address.model.ModelManager;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.ModelManager.isValidTime;


/**
 * DeadlineTime class
 */
public class DeadlineTime {

    public static final String MESSAGE_CONSTRAINTS = "Deadline Time should contain 4 Integers and be in " +
            "the 24HRS format";
    /* The input time should contain exactly 4 digits */
    public static final String VALIDATION_REGEX = "\\d{4}";
    public final String value;

    /**
     * Constructs an {@code DeadlineTime}.
     * @param deadlineTime a valid deadlineTime
     */
    public DeadlineTime(String deadlineTime) {
        requireNonNull(deadlineTime);
        checkArgument(isValidDeadlineTime(deadlineTime), MESSAGE_CONSTRAINTS);
        value = deadlineTime;
    }

    /**
     * Returns true if a given string is a valid time
     */
    public static boolean isValidDeadlineTime(String test) {
        return test.matches(VALIDATION_REGEX) && isValidTime(test);
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
