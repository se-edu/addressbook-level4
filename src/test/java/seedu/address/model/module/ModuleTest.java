package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalModules.DATA_STRUCTURES;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;
import seedu.address.testutil.ModuleBuilder;

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

        // different code -> returns false
        Module editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withCode(DISCRETE_MATH.getCode().value)
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
                .withCredit(DISCRETE_MATH.getCredits().value)
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
                + "s1 Credits: 4 Grade: F Completed: true"));
    }
}
