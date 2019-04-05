package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * class Sets
 */
public class Sets {


    public static final String MESSAGE_CONSTRAINTS =
            "Sets should only contain numbers";
    public static final String VALIDATION_REGEX = "\\d";
    public final String value;


    public Sets(String sets) {
        requireNonNull(sets);
        checkArgument(isValidSets(sets), MESSAGE_CONSTRAINTS);
        value = sets;
    }
    public static boolean isValidSets(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }


}
