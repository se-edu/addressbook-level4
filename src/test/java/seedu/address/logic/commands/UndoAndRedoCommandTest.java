package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import java.util.Collections;
import java.util.HashSet;

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
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

public class UndoAndRedoCommandTest {
    private static final CommandHistory HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
    private UndoRedoStack undoRedoStack = new UndoRedoStack();
    private UndoCommand undoCommand = new UndoCommand();
    private RedoCommand redoCommand = new RedoCommand();

    @Before
    public void setUp() throws Exception {
        undoCommand.setData(model, HISTORY, undoRedoStack);
        redoCommand.setData(model, HISTORY, undoRedoStack);
    }

    @Test
    public void undoCommandExecute_noCommandInHistory_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(UndoCommand.MESSAGE_FAILURE);

        undoCommand.execute();
    }

    @Test
    public void redoCommandExecute_noCommandInHistory_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(RedoCommand.MESSAGE_FAILURE);

        redoCommand.execute();
    }

    @Test
    public void undoAndRedoCommandExecute_oneCommandInHistory_success() throws Exception {
        Model expectedModel = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());

        // deletes first person from AddressBook and lists only the first person
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        deleteCommand.setData(model, new CommandHistory(), undoRedoStack);
        deleteCommand.execute();
        updateFilteredListShowOnlyFirstPersonInAddressBook();

        undoRedoStack.add(deleteCommand);

        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        ReadOnlyPerson toDelete = expectedModel.getFilteredPersonList().get(0);
        expectedModel.deletePerson(toDelete);

        updateFilteredListShowOnlyFirstPersonInAddressBook();

        undoRedoStack.add(new UndoCommand());

        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    private void updateFilteredListShowOnlyFirstPersonInAddressBook() {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new HashSet<>(Collections.singletonList(splitName[0])));
        assertTrue(model.getFilteredPersonList().size() == 1);
    }
}
