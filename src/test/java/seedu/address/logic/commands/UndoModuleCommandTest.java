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

public class UndoModuleCommandTest {

    private final Model model = new ModelManager(getTypicalTranscript(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalTranscript(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstModule(model);
        deleteFirstModule(model);

        deleteFirstModule(expectedModel);
        deleteFirstModule(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoTranscript();
        assertCommandSuccess(new UndoModuleCommand(), model, commandHistory,
                UndoModuleCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoTranscript();
        assertCommandSuccess(new UndoModuleCommand(), model, commandHistory,
                UndoModuleCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoModuleCommand(), model, commandHistory, UndoModuleCommand.MESSAGE_FAILURE);
    }
}
