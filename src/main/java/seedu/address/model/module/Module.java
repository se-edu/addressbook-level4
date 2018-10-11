package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

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

    public Module(Code code, Year year, Semester semester, Credit credit, Grade grade,
            boolean completed) {
        requireNonNull(code);
        requireNonNull(year);
        requireNonNull(semester);
        requireNonNull(credit);

        this.code = code;
        this.year = year;
        this.semester = semester;
        this.credits = credit;
        this.grade = grade;
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
     * Returns true if module code is the same.
     *
     * @return true if modue code is the same
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null && otherModule.getCode().equals(getCode());
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

    /**
     * Returns the code, year, semester, credits, grade, is module completed.
     * <p>
     * Format: Code: CODE Year: YEAR Semester: SEMESTER Credits: CREDITS Grade: GRADE Completed:
     * COMPLETED
     *
     * @return information of this module
     */
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

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(code, year, semester, credits, grade, completed);
    }
}
