package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Transcript;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.util.ModuleBuilder;

//@@author alexkmj
/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {
    // Manually added
    public static final Double MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP = 3.0;

    public static final String CODE_ASKING_QUESTIONS = "GEQ1000";
    public static final String CODE_DATABASE_SYSTEMS = "CS2102";
    public static final String CODE_DATABASE_SYSTEMS_REDUCED = "CS2102B";
    public static final String CODE_DATA_STRUCTURES = "CS2040";
    public static final String CODE_DISCRETE_MATH = "CS1231";
    public static final String CODE_SOFTWARE_ENGINEERING = "CS2103";
    public static final String CODE_PROGRAMMING_METHODOLOGY = "CS2030";

    public static final int YEAR_ONE = 1;
    public static final int YEAR_TWO = 2;
    public static final int YEAR_THREE = 3;
    public static final int YEAR_FOUR = 4;
    public static final int YEAR_FIVE = 5;

    public static final String GRADE_A_PLUS = "A+";
    public static final String GRADE_B_PLUS = "B+";
    public static final String GRADE_F = "F";
    public static final String GRADE_CS = "CS";

    public static final int CREDIT_TWO = 2;
    public static final int CREDIT_FOUR = 4;

    public static final Module DISCRETE_MATH = new ModuleBuilder()
            .withCode(CODE_DISCRETE_MATH)
            .withYear(YEAR_ONE)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(CREDIT_FOUR)
            .withGrade(GRADE_A_PLUS)
            .build();

    public static final Module PROGRAMMING_METHODOLOGY_TWO = new ModuleBuilder()
            .withCode(CODE_PROGRAMMING_METHODOLOGY)
            .withYear(YEAR_TWO)
            .withSemester(Semester.SEMESTER_TWO)
            .withCredit(CREDIT_FOUR)
            .withGrade(GRADE_B_PLUS)
            .build();

    public static final Module DATA_STRUCTURES = new ModuleBuilder()
            .withCode(CODE_DATA_STRUCTURES)
            .withYear(YEAR_THREE)
            .withSemester(Semester.SEMESTER_SPECIAL_ONE)
            .withCredit(CREDIT_FOUR)
            .withGrade(GRADE_F)
            .build();

    public static final Module ASKING_QUESTIONS = new ModuleBuilder()
            .withCode(CODE_ASKING_QUESTIONS)
            .withYear(YEAR_ONE)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(CREDIT_FOUR)
            .withGrade(GRADE_CS)
            .build();

    public static final Module SOFTWARE_ENGINEERING = new ModuleBuilder()
            .withCode(CODE_SOFTWARE_ENGINEERING)
            .withYear(YEAR_THREE)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(CREDIT_FOUR)
            .withGrade(GRADE_A_PLUS)
            .build();

    public static final Module DATABASE_SYSTEMS = new ModuleBuilder()
            .withCode(CODE_DATABASE_SYSTEMS)
            .withYear(YEAR_TWO)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(CREDIT_FOUR)
            .withGrade(GRADE_A_PLUS)
            .build();

    public static final Module DATABASE_SYSTEMS_2MC = new ModuleBuilder()
            .withCode(CODE_DATABASE_SYSTEMS_REDUCED)
            .withYear(YEAR_TWO)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(CREDIT_TWO)
            .withGrade(GRADE_A_PLUS)
            .build();

    /**
     * Prevents instantiation
     */
    private TypicalModules() {

    }

    /**
     * Returns an {@code Transcript} given modules as arguments.
     */
    public static Transcript getTranscriptWithModules(Module... modules) {
        Transcript tr = new Transcript();
        for (Module module : modules) {
            tr.addModule(module);
        }
        return tr;
    }

    /**
     * Returns an {@code Transcript} with all the typical persons.
     */
    public static Transcript getTypicalTranscript() {
        Transcript tr = new Transcript();
        for (Module module : getTypicalModules()) {
            tr.addModule(module);
        }
        return tr;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(DISCRETE_MATH,
                PROGRAMMING_METHODOLOGY_TWO,
                DATA_STRUCTURES));
    }


    /**
     * A list of modules that affects the cap
     *
     * @return
     */
    public static List<Module> getModulesWithoutNonGradeAffectingModules() {
        return new ArrayList<>(Arrays.asList(DISCRETE_MATH,
                PROGRAMMING_METHODOLOGY_TWO,
                DATA_STRUCTURES));
    }

    /**
     * A list of modules that might not affect the cap
     *
     * @return
     */
    public static List<Module> getModulesWithNonGradeAffectingModules() {
        List<Module> affectingModules = getModulesWithoutNonGradeAffectingModules();
        List<Module> nonAffectingModules = new ArrayList<>(Arrays.asList(ASKING_QUESTIONS));
        affectingModules.addAll(nonAffectingModules);
        return affectingModules;
    }
    // TODO: getTypicalAddressBook()

    //@@author jeremiah-ang
    /**
     * Duplicates Module with different Year
     * @param module
     * @return Module with different Year
     */
    public static Module duplicateWithDifferentYear(Module module) {
        Year option1 = new Year(YEAR_ONE);
        Year option2 = new Year(YEAR_TWO);
        Year target = (option1.equals(module.getYear())) ? option2 : option1;
        return duplicateWithDifferentYear(module, target);
    }

    /**
     * Duplicates Module with different Year
     * @param module
     * @param year
     * @return Module with different Year
     */
    public static Module duplicateWithDifferentYear(Module module, Year year) {
        return new ModuleBuilder(module).withYear(year.value).build();
    }

    /**
     * Duplicates Module with Grade adjusted
     * @param module
     * @return Module with Grade adjusted
     */
    public static Module duplicateWithGradesAdjusted(Module module) {
        return module.adjustGrade(module.getGrade());
    }
    //@@ author
}
