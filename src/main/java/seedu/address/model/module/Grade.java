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

    /**
     * No whitespace allowed.
     */
    public static final String GRADE_VALIDATION_REGEX =
            "A\\+|A\\-|A|B\\+|B\\-|B|C\\+|C|D\\+|D|F|CS|CU";


    private static final String EMPTY_VALUE = "NIL";

    /**
     * State of the grade
     */
    public final GradeState gradeState;

    /**
     * Immutable grade value.
     */
    public final String value;

    public Grade() {
        value = EMPTY_VALUE;
        gradeState = GradeState.INCOMPLETE;
    }

    /**
     * Constructs an {@code Grade}.
     *
     * @param grade A valid grade.
     */
    public Grade(String grade) {
        this(grade, GradeState.COMPLETE);
    }

    public Grade(String grade, GradeState gradeState) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_GRADE_CONSTRAINTS);
        value = grade;
        this.gradeState = gradeState;
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
        return !EMPTY_VALUE.equals(value) && !value.contentEquals("CS") && !value.contentEquals("CU");
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
     * @return true if grade is complete
     */
    public boolean isComplete() {
        return GradeState.COMPLETE.equals(gradeState);
    }

    /**
     * @return true if grade is incomplete
     */
    public boolean isIncomplete() {
        return GradeState.INCOMPLETE.equals(gradeState);
    }

    /**
     * @return true if grade is adjusted
     */
    public boolean isAdjust() {
        return GradeState.ADJUST.equals(gradeState);
    }

    /**
     * @return true if is target grades
     */
    public boolean isTarget() {
        return GradeState.TARGET.equals(gradeState);
    }

    /**
     * Creates a new Grade that is adjusted
     * @param grade
     * @return new Grade object
     */
    public Grade adjustGrade(String grade) {
        return new Grade(grade, GradeState.ADJUST);
    }

    /**
     * Creates a new Grade that is targeted
     * @param grade
     * @return new Grade object
     */
    public Grade targetGrade(String grade) {
        return new Grade(grade, GradeState.TARGET);
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

    /**
     * Different states of a grade
     */
    private enum GradeState {
        COMPLETE,
        INCOMPLETE,
        TARGET,
        ADJUST
    }
}
