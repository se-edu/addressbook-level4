package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {
    // Manually added

    public static final Module DISCRETE_MATH = new ModuleBuilder().withCode("CS1231")
            .withYear(1819)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();

    public static final Module PROGRAMMING_METHODOLOGY_TWO = new ModuleBuilder().withCode("CS2030")
            .withYear(1920)
            .withSemester(Semester.SEMESTER_TWO)
            .withCredit(4)
            .withGrade("B+")
            .build();

    public static final Module DATA_STRUCTURES = new ModuleBuilder().withCode("CS2040")
            .withYear(1718)
            .withSemester(Semester.SEMESTER_SPECIAL_ONE)
            .withCredit(4)
            .withGrade("F")
            .build();

    public static final Module ASKING_QUESTIONS = new ModuleBuilder().withCode("GEQ1000")
            .withYear(1819)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("CS")
            .build();

    public static final Double TYPICAL_MODULES_CAP = 3.0;

    /**
     * Prevents instantiation
     */
    private TypicalModules() {

    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(DISCRETE_MATH,
                PROGRAMMING_METHODOLOGY_TWO,
                DATA_STRUCTURES,
                ASKING_QUESTIONS));
    }

    public static List<Module> getModulesWithoutNonGradeAffectingModules() {
        return new ArrayList<>(Arrays.asList(DISCRETE_MATH,
                PROGRAMMING_METHODOLOGY_TWO,
                DATA_STRUCTURES));
    }
    // TODO: getTypicalAddressBook()
}
