package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


public class Exercise {

    public static final String MESSAGE_CONSTRAINTS =
            "Exercise should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String exerciseName;


    public Exercise(String exercise) {
        requireNonNull(exercise);
        checkArgument(isValidExercise(exercise), MESSAGE_CONSTRAINTS);
        exerciseName = exercise;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidExercise(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return exerciseName;
    }

}