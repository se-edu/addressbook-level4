package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * class Time
 */
public class Time {


    public static final String MESSAGE_CONSTRAINTS =
            "Time should only contain numbers, in minutes";
    public static final String VALIDATION_REGEX = "\\d";
    public final String value;


    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        value = time;
    }
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }


}
