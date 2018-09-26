package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's semester in the transcript.
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidSemester(String)}
 * <p>
 * Legal values: 1, 2, s1, s2. 1
 * <p>
 * (Semester 1), 2 (Semester 2), s1 (Special Semester 1), s2 (Special Semester 2).
 */
public class Semester {

    /**
     * Describes the requirements for semester value.
     */
    public static final String MESSAGE_SEMESTER_CONSTRAINTS = "Semester can be 1, 2, s1 or s2";

    /**
     * No whitespace allowed.
     */
    public static final String SEMESTER_VALIDATION_REGEX = "1|2|s1|s2";

    /**
     * Constant for semester one.
     */
    public static final String SEMESTER_ONE = "1";

    /**
     * Constant for semester two.
     */
    public static final String SEMESTER_TWO = "2";

    /**
     * Constant for special semester one.
     */
    public static final String SEMESTER_SPECIAL_ONE = "s1";

    /**
     * Constant for special semester two.
     */
    public static final String SEMESTER_SPECIAL_TWO = "s2";

    /**
     * Immutable semester value.
     */
    public final String value;

    /**
     * Constructs an {@code Code}.
     *
     * @param semester A valid semester.
     */
    public Semester(String semester) {
        requireNonNull(semester);
        checkArgument(isValidSemester(semester), MESSAGE_SEMESTER_CONSTRAINTS);
        value = semester;
    }

    /**
     * Returns true if a given string is a valid semester.
     *
     * @param semester string to be tested for validity
     * @return true if given string is a valid semester
     */
    public static boolean isValidSemester(String semester) {
        return semester.matches(SEMESTER_VALIDATION_REGEX);
    }

    /**
     * Returns the semester value.
     *
     * @return grade
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares the semester value of both Semester object.
     * <p>
     * This defines a notion of equality between two semester objects.
     *
     * @param other Semester object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Semester
                && value.equals(((Semester) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
