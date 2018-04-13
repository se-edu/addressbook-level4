package seedu.address.model.tutee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author ChoChihTun
/**
 * Represents a Tutee's subject grade
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */
public class Grade {

    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grade should start with an alphabetic character and followed by any character (ONLY ONE) or blank, "
            + "and it should not be blank";
    public static final String GRADE_VALIDATION_REGEX = "[\\p{Alpha}].??";

    public final String grade;

    /**
     * Constructs a {@code Grade}.
     *
     * @param grade A valid grade.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_GRADE_CONSTRAINTS);
        this.grade = grade;
    }

    /**
     * Returns true if a given string is a valid grade.
     */
    public static boolean isValidGrade(String test) {
        return test.matches(GRADE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return grade;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grade // instanceof handles nulls
                && this.grade.equals(((Grade) other).grade)); // state check
    }

    @Override
    public int hashCode() {
        return grade.hashCode();
    }
}
