package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's grade in the transcript.
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */
public class Grade {

    /**
     * Describes the requirements for grade value.
     */
    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grade can be A+, A, A-, B+, B, B-, C+, C, D+, D, F, CS, CU";

    public static final String MESSAGE_POINT_CONSTRAINTS =
            "Score must be between [0, 5] with increments of 0.5 and not 0.5";

    /**
     * No whitespace allowed.
     */
    public static final String GRADE_VALIDATION_REGEX =
            "A\\+|A\\-|A|B\\+|B\\-|B|C\\+|C|D\\+|D|F|CS|CU";

    /**
     * Immutable grade value.
     */
    public final String value;

    /**
     * Constructs an {@code Grade}.
     *
     * @param grade A valid grade.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_GRADE_CONSTRAINTS);
        value = grade;
    }

    public Grade(double point) {
        requireNonNull(point);
        checkArgument(isValidPoint(point), MESSAGE_POINT_CONSTRAINTS);
        value = getValue(point);
    }

    /**
     * Returns true if point is within [0, 5] and step by 0.5 and not 0.5
     * @param point
     * @return
     */
    public static boolean isValidPoint(double point) {
        double fraction = point - Math.floor(point);
        return point >= 0 && point <= 5 && (fraction == 0 || fraction == 0.5) && point != 0.5;
    }

    public String getValue(double point) {
        if (point == 5.0) {
            return "A";
        } else if (point == 4.5) {
            return "A-";
        } else if (point == 4.0) {
            return "B+";
        } else if (point == 3.5) {
            return "B";
        } else if (point == 3.0) {
            return "B-";
        } else if (point == 2.5) {
            return "C+";
        } else if (point == 2.0) {
            return "C";
        } else if (point == 1.5) {
            return "D+";
        } else if (point == 1.0) {
            return "D";
        } else {
            return "F";
        }
    }

    /**
     * Returns true if a given string is a valid grade.
     *
     * @param grade string to be tested for validity
     * @return true if given string is a valid grade
     */
    public static boolean isValidGrade(String grade) {
        return grade.matches(GRADE_VALIDATION_REGEX);
    }

    /**
     * Returns true if grade affects cap and false if grade does not affect cap.
     *
     * @return true if grade affects cap and false if grade does not affect cap.
     */
    public boolean affectsCap() {
        return !value.contentEquals("CS") && !value.contentEquals("CU");
    }

    /**
     * Returns the point equivalent of the grade or 0 if grade is invalid.
     *
     * @return point equivalent of the grade
     */
    public float getPoint() {
        switch (value) {
        case "A+":
            return 5f;
        case "A":
            return 5f;
        case "A-":
            return 4.5f;
        case "B+":
            return 4f;
        case "B":
            return 3.5f;
        case "B-":
            return 3f;
        case "C+":
            return 2.5f;
        case "C":
            return 2f;
        case "D+":
            return 1.5f;
        case "D":
            return 1f;
        case "F":
        default:
            return 0f;
        }
    }

    /**
     * Returns the grade value.
     *
     * @return grade
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares the grade value of both Grade object.
     * <p>
     * This defines a notion of equality between two grade objects.
     *
     * @param other Grade object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Grade
                && value.equals(((Grade) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
