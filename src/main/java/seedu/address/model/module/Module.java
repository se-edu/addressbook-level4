package seedu.address.model.module;

import java.util.Objects;

/**
 * Represents a Module in the transcript.
 * <p>
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    /**
     * Constant for completed.
     */
    public static final boolean MODULE_COMPLETED = true;

    /**
     * Constant for not completed.
     */
    public static final boolean MODULE_NOT_COMPLETED = false;

    /**
     * Code for the module.
     */
    private final Code code;

    /**
     * Year the module was taken.
     */
    private final Year year;

    /**
     * Semester the module was taken.
     */
    private final Semester semester;

    /**
     * Module credits awarded for completion this module.
     */
    private final Credit credits;

    /**
     * Module grade awarded for completion this module.
     */
    private final Grade grade;

    /**
     * True if module has been completed. False if module has not been taken yet.
     */
    private final boolean completed;

    /**
     * Every field must be present and not null.
     *
     * @param code code for the module
     * @param year year the module was taken
     * @param semester semester the module was taken
     * @param credits credits awarded for completion of this module
     * @param completed true if module has been completed and false if module has not been taken
     */
    public Module(String code, int year, String semester, int credits, boolean completed) {
        this.code = new Code(code);
        this.year = new Year(year);
        this.semester = new Semester(semester);
        this.credits = new Credit(credits);
        this.grade = null;
        this.completed = completed;
    }

    /**
     * Every field must be present and not null.
     *
     * @param code code for the module
     * @param year year the module was taken
     * @param semester semester the module was taken
     * @param credits credits awarded for completion of this module
     * @param grade grade awarded for completion of this module
     * @param completed true if module has been completed and false if module has not been taken
     */
    public Module(String code, int year, String semester, int credits, String grade,
            boolean completed) {
        this.code = new Code(code);
        this.year = new Year(year);
        this.semester = new Semester(semester);
        this.credits = new Credit(credits);
        this.grade = new Grade(grade);
        this.completed = completed;
    }

    /**
     * Returns the module code.
     *
     * @return module code
     */
    public Code getCode() {
        return code;
    }

    /**
     * Returns the module credits awarded.
     *
     * @return module credits
     */
    public Credit getCredits() {
        return credits;
    }

    /**
     * Returns the year in which the module was taken.
     *
     * @return year in which module was taken
     */
    public Year getYear() {
        return year;
    }

    /**
     * Returns the semester in which the module was taken.
     *
     * @return semester in which module was taken
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Returns the module grade awarded.
     *
     * @return module grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Returns true if module has been completed and false if module has not been taken.
     *
     * @return true if module has been completed and false if module has not been taken
     */
    public boolean hasCompleted() {
        return completed;
    }

    /**
     * Returns true if both modules are of the same object or contains the same set of data fields.
     * <p>
     * This defines a notion of equality between two modules.
     *
     * @param other other module to be compared with this Module object
     * @return true if both objects contains the same data fields
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getCode().equals(getCode())
                && otherModule.getYear() == getYear()
                && otherModule.getSemester() == getSemester()
                && otherModule.getCredits() == getCredits()
                && otherModule.getGrade() == getGrade()
                && otherModule.hasCompleted() == hasCompleted();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(code, year, semester, credits, grade, completed);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        return builder.append("Code: ")
                .append(getCode())
                .append(" Year: ")
                .append(getYear())
                .append(" Semester: ")
                .append(getSemester())
                .append(" Credits: ")
                .append(getCredits())
                .append(" Grade: ")
                .append(getGrade())
                .append(" Completed: ")
                .append(hasCompleted())
                .toString();
    }
}
