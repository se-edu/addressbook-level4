package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.ASKING_QUESTIONS;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;
import static seedu.address.testutil.TypicalModules.getTypicalTranscript;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit
 * tests for {@code EditModuleCommandTest}.
 */
public class EditModuleCommandTest {

    private Model model = new ModelManager(getTypicalTranscript(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeValidCodeUnfilteredListSuccess() {
        EditModuleCommand editModuleCommand = new EditModuleCommand(
                DISCRETE_MATH.getCode(), // targetCode
                null, // targetYear
                null, // targetSemester
                null, // newCode
                null, // newYear
                null, // newSemester
                null, // newCredit
                ASKING_QUESTIONS.getGrade()); // newGrade

        Module editedDiscreteMath = new ModuleBuilder(DISCRETE_MATH)
                .withGrade(ASKING_QUESTIONS.getGrade())
                .build();

        String expectedMessage = String.format(
                EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS,
                editedDiscreteMath);

        ModelManager expectedModel = new ModelManager(model.getTranscript(), new UserPrefs());
        expectedModel.deleteModule(target -> target.getCode().equals(DISCRETE_MATH.getCode()));
        expectedModel.addModule(editedDiscreteMath);
        expectedModel.commitTranscript();

        assertCommandSuccess(editModuleCommand,
                model, commandHistory,
                expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidCodeUnfilteredListSuccess() {
        model.addModule(ASKING_QUESTIONS);
        model.commitTranscript();

        EditModuleCommand editModuleCommand = new EditModuleCommand(
                DISCRETE_MATH.getCode(), // targetCode
                null, // targetYear
                null, // targetSemester
                ASKING_QUESTIONS.getCode(), // newCode
                ASKING_QUESTIONS.getYear(), // newYear
                ASKING_QUESTIONS.getSemester(), // newSemester
                ASKING_QUESTIONS.getCredits(), // newCredit
                ASKING_QUESTIONS.getGrade()); // newGrade

        assertCommandFailure(editModuleCommand,
                model, commandHistory,
                EditModuleCommand.MESSAGE_MODULE_EXIST);
    }
}
