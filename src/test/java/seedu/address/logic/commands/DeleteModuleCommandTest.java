package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;
import static seedu.address.testutil.TypicalModules.getTypicalTranscript;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Code;
import seedu.address.model.util.ModuleBuilder;
import seedu.address.testutil.TypicalModules;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit
 * tests for {@code DeleteCommand}.
 */
public class DeleteModuleCommandTest {

    private Model model = new ModelManager(getTypicalTranscript(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeValidCodeUnfilteredListSuccess() {
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(DISCRETE_MATH.getCode());

        String expectedMessage = String.format(DeleteModuleCommand.MESSAGE_DELETE_MODULE_SUCCESS,
                deleteModuleCommand);

        ModelManager expectedModel = new ModelManager(model.getTranscript(), new UserPrefs());
        expectedModel.deleteModule(target -> target.getCode().equals(DISCRETE_MATH.getCode()));
        expectedModel.commitTranscript();

        assertCommandSuccess(deleteModuleCommand, model, commandHistory, expectedMessage,
                expectedModel);
    }

    @Test
    public void executeInvalidCodeUnfilteredListThrowsCommandException() {
        Code invalidCode = new Code(TypicalModules.CODE_SOFTWARE_ENGINEERING);
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(invalidCode);

        assertCommandFailure(deleteModuleCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_MODULE);

        model.addModule(new ModuleBuilder(DISCRETE_MATH).withYear(TypicalModules.YEAR_TWO).build());
        deleteModuleCommand = new DeleteModuleCommand(DISCRETE_MATH.getCode());

        assertCommandFailure(deleteModuleCommand, model, commandHistory,
                Messages.MESSAGE_MULTIPLE_INSTANCES_FOUND);
    }
}
