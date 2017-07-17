package seedu.address.logic.commands;

import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;

public class RedoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
    private final RedoCommand redoCommand = new RedoCommand();
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_FIRST_PERSON);

    private UndoRedoStack undoRedoStack = new UndoRedoStack();

    @Before
    public void setUp() throws Exception {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute_noCommandInRedoStack_throwsCommandException() throws Exception {
        undoRedoStack = prepareStack(Collections.singletonList(deleteCommandOne), Collections.emptyList());
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        thrown.expect(CommandException.class);
        thrown.expectMessage(RedoCommand.MESSAGE_FAILURE);

        redoCommand.execute();
    }

    @Test
    public void execute_twoCommandInRedoStack_success() throws Exception {
        undoRedoStack = prepareStack(Collections.emptyList(), Arrays.asList(deleteCommandTwo, deleteCommandOne));
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());

        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
