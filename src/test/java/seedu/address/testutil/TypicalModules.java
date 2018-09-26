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
            .withYear(1)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();

    public static final Module PROGRAMMING_METHODOLOGY_TWO = new ModuleBuilder().withCode("CS2030")
            .withYear(2)
            .withSemester(Semester.SEMESTER_TWO)
            .withCredit(4)
            .withGrade("B")
            .build();

    public static final Module DATA_STRUCTURES = new ModuleBuilder().withCode("CS2040")
            .withYear(3)
            .withSemester(Semester.SEMESTER_SPECIAL_ONE)
            .withCredit(4)
            .withGrade("F")
            .build();

    /**
     * Prevents instantiation
     */
    private TypicalModules() {

    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(DISCRETE_MATH,
                PROGRAMMING_METHODOLOGY_TWO,
                DATA_STRUCTURES));
    }
    // TODO: getTypicalAddressBook()
}
