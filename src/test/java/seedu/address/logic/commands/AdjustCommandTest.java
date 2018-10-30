package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Transcript;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.testutil.TypicalModules;

public class AdjustCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Module moduleA = TypicalModules.DATA_STRUCTURES;
    private Module moduleADiffYear = TypicalModules.duplicateWithDifferentYear(moduleA);
    private Module moduleB = TypicalModules.DISCRETE_MATH;
    private Module moduleAAdjusted = TypicalModules.duplicateWithGradesAdjusted(moduleA);
    private Module moduleNotInTranscript = TypicalModules.DATABASE_SYSTEMS;

    @Test
    public void constructorNullModuleThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AdjustCommand(null, null, null, null);
    }

    @Test
    public void assertAdjustMultipleInstanceSuccess() {
        assertAdjustInstanceSuccess(moduleA, moduleADiffYear, moduleB);
    }

    @Test
    public void assertAdjustSingleInstanceSuccess() {
        assertAdjustInstanceSuccess(moduleB, moduleA, moduleADiffYear);
    }

    /**
     * Asserts adjusting a module is successful
     */
    private void assertAdjustInstanceSuccess(Module moduleAbc, Module moduleAbcDuplicate, Module moduleDef) {
        Model model = getSampleModel();
        Grade expectedGradeAbc = moduleAAdjusted.getGrade();
        Module expectedModuleAbc = new Module(moduleAbc, expectedGradeAbc);
        Transcript expectedTranscript = new Transcript();
        expectedTranscript.addModule(moduleAbcDuplicate);
        expectedTranscript.addModule(moduleDef);
        expectedTranscript.addModule(expectedModuleAbc);
        Model expectedModel = new ModelManager(expectedTranscript, new UserPrefs());
        AdjustCommand adjustCommand = new AdjustCommand(
                moduleAbc.getCode(), moduleAbc.getYear(), moduleAbc.getSemester(), expectedGradeAbc);
        String expectedMessage = String.format(AdjustCommand.MESSAGE_SUCCESS, expectedModuleAbc);
        assertCommandSuccess(adjustCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void assertAdjustModuleNotExistFailure() throws CommandException {
        Model model = getSampleModel();
        AdjustCommand adjustCommand = new AdjustCommand(
                moduleNotInTranscript.getCode(), moduleNotInTranscript.getYear(),
                moduleNotInTranscript.getSemester(), moduleNotInTranscript.getGrade());
        String expectedMessage = AdjustCommand.MESSAGE_MODULE_NOT_FOUND;
        assertCommandFailure(adjustCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void assertAdjustCodeOnlyMultipleInstanceFailure() {
        Model model = getSampleModel();
        AdjustCommand adjustCommand = new AdjustCommand(
                moduleA.getCode(), null, null, moduleAAdjusted.getGrade());
        String expectedMessage = AdjustCommand.MESSAGE_MULTIPLE_INSTANCE;
        assertCommandFailure(adjustCommand, model, commandHistory, expectedMessage);
    }

    /**
     * Returns a sample model with 3 modules
     * @return
     */
    private Model getSampleModel() {
        Model model = new ModelManager(new Transcript(), new UserPrefs());
        model.addModule(moduleA);
        model.addModule(moduleADiffYear);
        model.addModule(moduleB);
        return model;
    }
}
