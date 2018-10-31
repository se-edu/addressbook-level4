package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Module's grade in the transcript.
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */
public class Grade {

    //@@author alexkmj
    /**
     * Describes the requirements for grade value.
     */
    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grade can be A+, A, A-, B+, B, B-, C+, C, D+, D, F, CS, CU";

    public static final String MESSAGE_POINT_CONSTRAINTS =
            "Score must be between [0, 5] with increments of 0.5 and not 0.5";

    //@@author alexkmj
    /**
     * Default value's value
     */
    private static final String EMPTY_VALUE = "NIL";

    /**
     * No whitespace allowed.
     */
    public static final String GRADE_VALIDATION_REGEX =
            "A\\+|A\\-|A|B\\+|B\\-|B|C\\+|C|D\\+|D|F|CS|CU|" + EMPTY_VALUE;

    //@@author jeremiah-ang
    /**
     * Static Unchangeable Mapping between Grade and Point
     */
    private static final Map<String, Double> MAP_GRADE_POINT;
    private static final Map<Double, String> MAP_POINT_GRADE;

    static {
        Map<String, Double> tempGradePointMap = new HashMap<>();
        Map<Double, String> tempPointGradeMap = new HashMap<>();
        tempGradePointMap.put("A+", 5.0);
        tempGradePointMap.put("A", 5.0);
        tempGradePointMap.put("A-", 4.5);
        tempGradePointMap.put("B+", 4.0);
        tempGradePointMap.put("B", 3.5);
        tempGradePointMap.put("B-", 3.0);
        tempGradePointMap.put("C+", 2.5);
        tempGradePointMap.put("C", 2.0);
        tempGradePointMap.put("D+", 1.5);
        tempGradePointMap.put("D", 1.0);
        tempGradePointMap.put("F", 0.0);

        for (Map.Entry<String, Double> entry : tempGradePointMap.entrySet()) {
            tempPointGradeMap.put(entry.getValue(), entry.getKey());
        }
        tempPointGradeMap.put(5.0, "A");

        MAP_GRADE_POINT = Collections.unmodifiableMap(tempGradePointMap);
        MAP_POINT_GRADE = Collections.unmodifiableMap(tempPointGradeMap);
    }

    //@@author alexkmj
    /**
     * Immutable grade value.
     */
    public final String value;

    //@@author alexkmj
    /**
     * State of the grade
     */
    public final State state;

    /**
     * Creates a new {@code Grade} object with State INCOMPLETE
     */
    public Grade() {
        this(EMPTY_VALUE, State.INCOMPLETE);
    }

    /**
     * Creates a new {@code Grade} object with value grade and State COMPLETE
     */
    public Grade(String grade) {
        this(grade.toUpperCase(), (EMPTY_VALUE.equals(grade)) ? State.INCOMPLETE : State.COMPLETE);
    }

    /**
     * Constructs an {@code Grade} with letter grade and state of it.
     * @param grade
     * @param state
     */
    private Grade(String grade, State state) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_GRADE_CONSTRAINTS);
        value = grade.toUpperCase();
        this.state = state;
    }

    /**
     * Constructs an {@code Grade} from point with state COMPLETE
     * @param point
     */
    public Grade(double point) {
        this(point, State.COMPLETE);
    }

    /**
     * Constructs an {@code Grade} from point and given state
     * @param point
     * @param state
     */
    private Grade(double point, State state) {
        requireNonNull(point);
        checkArgument(isValidPoint(point), MESSAGE_POINT_CONSTRAINTS);
        value = mapPointToValue(point);
        this.state = state;
    }

    /**
     * Constructs an {@code Grade} from String values of value and state
     * @param value
     * @param state
     */
    public Grade(String value, String state) {
        this(value, State.valueOf(state));
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

    /**
     * Returns the letter grade the point should be mapped to.
     * @param point
     * @return
     */
    private String mapPointToValue(double point) {
        return MAP_POINT_GRADE.get(point);
    }

    //@@author alexkmj
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
        if (MAP_GRADE_POINT.containsKey(value)) {
            return MAP_GRADE_POINT.get(value).floatValue();
        }
        return 0;
    }

    //@@author alexkmj
    /**
     * @return true if grade is complete
     */
    public boolean isComplete() {
        return State.COMPLETE.equals(state);
    }

    /**
     * @return true if grade is incomplete
     */
    public boolean isIncomplete() {
        return State.INCOMPLETE.equals(state);
    }

    /**
     * @return true if grade is adjusted
     */
    public boolean isAdjust() {
        return State.ADJUST.equals(state);
    }

    /**
     * @return true if is target grades
     */
    public boolean isTarget() {
        return State.TARGET.equals(state);
    }

    /**
     * Creates a new Grade that is adjusted
     * @param grade
     * @return new Grade object
     */
    public Grade adjustGrade(String grade) {
        return new Grade(grade, State.ADJUST);
    }

    /**
     * Creates a new Grade that is adjusted
     * @param point
     * @return new Grade object
     */
    public Grade adjustGrade(double point) {
        return new Grade(point, State.ADJUST);
    }

    /**
     * Creates a new Grade that is targeted
     * @param grade
     * @return new Grade object
     */
    public Grade targetGrade(String grade) {
        return new Grade(grade, State.TARGET);
    }

    /**
     * Creates a new Grade that is targeted
     * @param point
     * @return new Grade object
     */
    public Grade targetGrade(double point) {
        return new Grade(point, State.TARGET);
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

    //@@author alexkmj
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
                && value.equals(((Grade) other).value))
                && state.equals(((Grade) other).state);
    }

    //@@author alexkmj
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Different states of a grade
     */
    private enum State {
        COMPLETE,
        INCOMPLETE,
        TARGET,
        ADJUST
    }
}
