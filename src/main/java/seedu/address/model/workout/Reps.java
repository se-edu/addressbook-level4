package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Reps {


    public static final String MESSAGE_CONSTRAINTS =
            "Reps should only contain numbers";
    public static final String VALIDATION_REGEX = "\\d";
    public final String value;


    public Reps(String reps) {
        requireNonNull(reps);
        checkArgument(isValidReps(reps), MESSAGE_CONSTRAINTS);
        value = reps;
    }
    public static boolean isValidReps(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }


}
