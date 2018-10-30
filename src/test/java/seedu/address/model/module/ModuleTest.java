package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalModules.DATABASE_SYSTEMS_2MC;
import static seedu.address.testutil.TypicalModules.DATA_STRUCTURES;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.util.ModuleBuilder;
import seedu.address.testutil.Assert;

//@@author alexkmj
public class ModuleTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Module(null, null, null, null, null, false));
    }

    @Test
    public void isSameModule() {
        // same object -> returns true
        assertTrue(DATA_STRUCTURES.isSameModule(DATA_STRUCTURES));

        // different object -> returns false
        assertFalse(DATA_STRUCTURES.isSameModule(DISCRETE_MATH));

        // same code, year, sem -> returns true
        Module editedDataStructures = new ModuleBuilder(DISCRETE_MATH)
                .withCode(DATA_STRUCTURES.getCode().value)
                .withYear(DATA_STRUCTURES.getYear().value)
                .withSemester(DATA_STRUCTURES.getSemester().value)
                .build();
        assertTrue(DATA_STRUCTURES.isSameModule(editedDataStructures));

        // different code -> returns false
        editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withCode(DISCRETE_MATH.getCode().value)
                .build();
        assertFalse(DATA_STRUCTURES.isSameModule(editedDataStructures));

        // different year -> returns false
        editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withYear(DISCRETE_MATH.getYear().value)
                .build();
        assertFalse(DATA_STRUCTURES.isSameModule(editedDataStructures));

        // different sem -> returns false
        editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withSemester(DISCRETE_MATH.getSemester().value)
                .build();
        assertFalse(DATA_STRUCTURES.isSameModule(editedDataStructures));

        // different code, year -> returns false
        editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withCode(DISCRETE_MATH.getCode().value)
                .withYear(DISCRETE_MATH.getYear().value)
                .build();
        assertFalse(DATA_STRUCTURES.isSameModule(editedDataStructures));

        // different code, semester -> returns false
        editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withCode(DISCRETE_MATH.getCode().value)
                .withSemester(DISCRETE_MATH.getSemester().value)
                .build();
        assertFalse(DATA_STRUCTURES.isSameModule(editedDataStructures));

        // different year, semester -> returns false
        editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withYear(DISCRETE_MATH.getYear().value)
                .withSemester(DISCRETE_MATH.getSemester().value)
                .build();
        assertFalse(DATA_STRUCTURES.isSameModule(editedDataStructures));

        editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withYear(2)
                .build();
        assertFalse(DATA_STRUCTURES.isSameModule(editedDataStructures));

        editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withSemester(Semester.SEMESTER_TWO)
                .build();
        assertFalse(DATA_STRUCTURES.isSameModule(editedDataStructures));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Module dataStructuresCopy = new ModuleBuilder(DATA_STRUCTURES).build();
        assertTrue(DATA_STRUCTURES.equals(dataStructuresCopy));

        // same object -> returns true
        assertTrue(DATA_STRUCTURES.equals(DATA_STRUCTURES));

        // different type -> returns false
        assertFalse(DATA_STRUCTURES.equals(5));

        // different module -> returns false
        assertFalse(DATA_STRUCTURES.equals(DISCRETE_MATH));

        // different code -> returns false
        Module editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withCode(DISCRETE_MATH.getCode().value)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));

        // different year -> returns false
        editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withYear(DISCRETE_MATH.getYear().value)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));

        // different semester -> returns false
        editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withSemester(DISCRETE_MATH.getSemester().value)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));

        // different credit -> returns false
        editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withCredit(DATABASE_SYSTEMS_2MC.getCredits().value)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));

        // different grade -> returns false
        editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withGrade(DISCRETE_MATH.getGrade().value)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));

        // different completed -> returns false
        editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withCompleted(false)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));
    }

    @Test
    public void toStringValid() {
        assertTrue(DATA_STRUCTURES.toString().contentEquals("Code: CS2040 Year: 3 Semester: "
                + "s1 Credits: 4 Grade: F Grade State: COMPLETE Completed: true"));
    }

    //@@author jeremiah-ang
    @Test
    public void autoFillIsCompletedSuccess() {
        assertTrue((new Module(
                new Code("CS2103"), new Year(1), new Semester("1"),
                new Credit(4), new Grade("A"))).hasCompleted());
        assertFalse((new Module(
                new Code("CS2103"), new Year(1), new Semester("1"),
                new Credit(4), new Grade())).hasCompleted());
        assertFalse((new Module(
                new Code("CS2103"), new Year(1), new Semester("1"),
                new Credit(4), new Grade().adjustGrade("A"))).hasCompleted());
        assertFalse((new Module(
                new Code("CS2103"), new Year(1), new Semester("1"),
                new Credit(4), new Grade().targetGrade("A"))).hasCompleted());
    }
}
