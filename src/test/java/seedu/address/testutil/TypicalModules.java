package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Transcript;
import seedu.address.model.module.*;
import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;

//@@author alexkmj
/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {
    // Manually added

    public static final Double MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP = 3.0;

    public static final Module DISCRETE_MATH = new ModuleBuilder().withCode("CS1231")
            .withYear(1)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();

    public static final Module PROGRAMMING_METHODOLOGY_TWO = new ModuleBuilder().withCode("CS2030")
            .withYear(2)
            .withSemester(Semester.SEMESTER_TWO)
            .withCredit(4)
            .withGrade("B+")
            .build();

    public static final Module DATA_STRUCTURES = new ModuleBuilder().withCode("CS2040")
            .withYear(3)
            .withSemester(Semester.SEMESTER_SPECIAL_ONE)
            .withCredit(4)
            .withGrade("F")
            .build();

    public static final Module ASKING_QUESTIONS = new ModuleBuilder().withCode("GEQ1000")
            .withYear(1)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("CS")
            .build();

    public static final Module SOFTWARE_ENGINEERING = new ModuleBuilder().withCode("CS2103")
            .withYear(3)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();

    public static final Module DATABASE_SYSTEMS = new ModuleBuilder().withCode("CS2102")
            .withYear(2)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();

    public static final Module DATABASE_SYSTEMS_2MC = new ModuleBuilder().withCode("CS2102B")
            .withYear(2)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(2)
            .withGrade("A+")
            .build();

    //@@author jeremiah-ang
    /**
     * Duplicates Module with different Year
     * @param module
     * @return Module with different Year
     */
    public static Module duplicateWithDifferentYear(Module module) {
        Year option1 = new Year(1);
        Year option2 = new Year(2);
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

    public static Module duplicateWithGradesAdjusted(Module module) {
        return module.adjustGrade(module.getGrade());
    }
    //@@ author

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
}
