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
        value = mapPointToValue(point);
    }

    /**
     * Returns true if point is within [0, 5] and step by 0.5 and not 0.5
     * @param point
     * @return
     */
    private static boolean isValidPoint(double point) {
        double fraction = point - Math.floor(point);
        return point >= 0 && point <= 5 && (fraction == 0 || fraction == 0.5) && point != 0.5;
    }

    private String mapPointToValue(double point) {
        return MAP_POINT_GRADE.get(point);
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
        if (MAP_GRADE_POINT.containsKey(value)) {
            return MAP_GRADE_POINT.get(value).floatValue();
        }
        return 0;
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
