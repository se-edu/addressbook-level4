package seedu.address.testutil;

import seedu.address.model.module.Code;
import seedu.address.model.module.Credit;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_CODE = "CS2103";
    public static final int DEFAULT_YEAR = 1;
    public static final String DEFAULT_SEMESTER = Semester.SEMESTER_ONE;
    public static final int DEFAULT_CREDIT = 4;
    public static final String DEFAULT_GRADE = "A+";
    public static final boolean DEFAULT_COMPLETED = true;

    private Code code;
    private Year year;
    private Semester semester;
    private Credit credit;
    private Grade grade;
    private boolean completed;

    public ModuleBuilder() {
        code = new Code(DEFAULT_CODE);
        year = new Year(DEFAULT_YEAR);
        semester = new Semester(DEFAULT_SEMESTER);
        credit = new Credit(DEFAULT_CREDIT);
        grade = new Grade(DEFAULT_GRADE);
        completed = DEFAULT_COMPLETED;
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code personToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        code = moduleToCopy.getCode();
        year = moduleToCopy.getYear();
        semester = moduleToCopy.getSemester();
        credit = moduleToCopy.getCredits();
        grade = moduleToCopy.getGrade();
        completed = moduleToCopy.hasCompleted();
    }

    /**
     * Sets the {@code Code} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCode(String code) {
        this.code = new Code(code);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code Module} that we are building.
     */
    public ModuleBuilder withYear(int year) {
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code Module} that we are building.
     */
    public ModuleBuilder withSemester(String semester) {
        this.semester = new Semester(semester);
        return this;
    }

    /**
     * Sets the {@code Credit} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCredit(int credit) {
        this.credit = new Credit(credit);
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code Module} that we are building.
     */
    public ModuleBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
        return this;
    }

    /**
     * Sets the {@code completed} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public Module build() {
        return new Module(code, year, semester, credit, grade, completed);
    }
}
