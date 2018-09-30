package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Transcript;
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
            .withGrade("B")
            .build();

    public static final Module DATA_STRUCTURES = new ModuleBuilder().withCode("CS2040")
            .withYear(1718)
            .withSemester(Semester.SEMESTER_SPECIAL_ONE)
            .withCredit(4)
            .withGrade("F")
            .build();

    public static final Module SOFTWARE_ENGINEERING = new ModuleBuilder().withCode("CS2103")
            .withYear(2018)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();

    public static final Module DATABASE_SYSTEMS = new ModuleBuilder().withCode("CS2102")
            .withYear(2018)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();

    public static final Module DATABASE_SYSTEMS_2MC = new ModuleBuilder().withCode("CS2102")
            .withYear(2018)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(2)
            .withGrade("A+")
            .build();

    /**
     * Prevents instantiation
     */
    private TypicalModules() {

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

}
