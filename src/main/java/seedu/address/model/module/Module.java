package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a Module in the transcript.
 * <p>
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    //@@author alexkmj
    /**
     * Constant for completed.
     */
    public static final boolean MODULE_COMPLETED = true;

    //@@author alexkmj
    /**
     * Constant for not completed.
     */
    public static final boolean MODULE_NOT_COMPLETED = false;

    //@@author alexkmj
    /**
     * Code for the module.
     */
    private final Code code;

    //@@author alexkmj
    /**
     * Year the module was taken.
     */
    private final Year year;

    //@@author alexkmj
    /**
     * Semester the module was taken.
     */
    private final Semester semester;

    //@@author alexkmj
    /**
     * Module credits awarded for completion this module.
     */
    private final Credit credits;

    //@@author alexkmj
    /**
     * Module grade awarded for completion this module.
     */
    private final Grade grade;

    /**
     * True if module has been completed. False if module has not been taken yet.
     */
    private final boolean completed;

    //@@author alexkmj
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
        this.completed = completed;

        //TODO require grade Non-null
        this.grade = (grade == null) ? new Grade() : grade;
    }

    public Module(Code code, Year year, Semester semester, Credit credit, Grade grade) {
        //TODO require grade Non-null
        this(code, year, semester, credit, grade, (grade == null) || grade.isComplete());
    }

    //@@author jeremiah-ang
    /**
     * Creates a new Module from an existing module but with a different grade
     * @param module
     * @param grade
     */
    public Module(Module module, Grade grade) {
        this(module.code, module.year, module.semester, module.credits, grade, module.completed);
    }

    /**
     * Tells if this module can be used for target grade calculation
     * @return true if yes false otherwise.
     */
    public boolean isTargetable() {
        return getGrade().isTarget() || getGrade().isIncomplete();
    }

    /**
     * Tells if this module will affect the calculation of CAP
     * @return true if yes false otherwise.
     */
    public boolean isAffectCap() {
        return getGrade().affectsCap();
    }

    /**
     * returns the value of the Credit
     * @return value of Credit
     */
    public int getCreditsValue() {
        return getCredits().value;
    }

    /**
     * Clones current Module into one with a new Grade with state TARGET
     * @param point
     * @return a new Module with Grade with state TARGET
     */
    public Module updateTargetGrade(double point) {
        return new Module(this, grade.targetGrade(point));
    }

    /**
     * Tells if the Module has a Grade with state TARGET
     * @return true if yes, false otherwise
     */
    public boolean isTargetted() {
        return getGrade().isTarget();
    }

    public boolean isAdjusted() {
        return getGrade().isAdjust();
    }

    public Module adjustGrade(Grade grade) {
        return new Module(this, grade.adjustGrade(grade.value));
    }

    //@@author alexkmj
    /**
     * Returns the module code.
     *
     * @return module code
     */
    public Code getCode() {
        return code;
    }

    //@@author alexkmj
    /**
     * Returns the module credits awarded.
     *
     * @return module credits
     */
    public Credit getCredits() {
        return credits;
    }

    //@@author alexkmj
    /**
     * Returns the year in which the module was taken.
     *
     * @return year in which module was taken
     */
    public Year getYear() {
        return year;
    }

    //@@author alexkmj
    /**
     * Returns the semester in which the module was taken.
     *
     * @return semester in which module was taken
     */
    public Semester getSemester() {
        return semester;
    }

    //@@author alexkmj
    /**
     * Returns the module grade awarded.
     *
     * @return module grade
     */
    public Grade getGrade() {
        return grade;
    }

    //@@author alexkmj
    /**
     * Returns true if module has been completed and false if module has not been taken.
     *
     * @return true if module has been completed and false if module has not been taken
     */
    public boolean hasCompleted() {
        return completed;
    }

    //@@author alexkmj
    /**
     * Returns true if module code, year, and semester are the same.
     *
     * @return true if modue code, year, and semester is the same
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getCode().equals(getCode())
                && otherModule.getYear().equals(getYear())
                && otherModule.getSemester().equals(getSemester());
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
                && otherModule.getGrade().equals(getGrade())
                && otherModule.getYear().equals(getYear())
                && otherModule.getSemester().equals(getSemester())
                && otherModule.getCredits().equals(getCredits())
                && otherModule.getGrade().equals(getGrade())
                && otherModule.hasCompleted() == hasCompleted();
    }

    //@@author alexkmj
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
                .append(" Grade State: ")
                .append(getGrade().state)
                .append(" Completed: ")
                .append(hasCompleted())
                .toString();
    }

    //@@author alexkmj
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(code, year, semester, credits, grade, completed);
    }


}
