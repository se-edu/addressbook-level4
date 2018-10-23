package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstModule;
import static seedu.address.testutil.TypicalModules.getTypicalTranscript;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoModuleCommandTest {

    private final Model model = new ModelManager(getTypicalTranscript(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalTranscript(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstModule(model);
        deleteFirstModule(model);
        model.undoTranscript();
        model.undoTranscript();

        deleteFirstModule(expectedModel);
        deleteFirstModule(expectedModel);
        expectedModel.undoTranscript();
        expectedModel.undoTranscript();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoTranscript();
        assertCommandSuccess(new RedoModuleCommand(), model, commandHistory,
                RedoModuleCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoTranscript();
        assertCommandSuccess(new RedoModuleCommand(), model, commandHistory,
                RedoModuleCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoModuleCommand(), model, commandHistory, RedoModuleCommand.MESSAGE_FAILURE);
    }
}
