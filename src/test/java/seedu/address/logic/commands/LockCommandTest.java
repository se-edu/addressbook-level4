package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code LockCommand}.
 */
public class LockCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        LockCommand firstLockCommand = new LockCommand();
        LockCommand secondLockCommand = new LockCommand("1234");
        LockCommand thirdLockCommand = new LockCommand("123456");

        // same object -> returns true
        assertTrue(firstLockCommand.equals(firstLockCommand));

        // same values -> returns true
        LockCommand secondLockCommandcopy = new LockCommand("1234");
        assertTrue(secondLockCommand.equals(secondLockCommandcopy));

        // different types -> returns false
        assertFalse(firstLockCommand.equals(1));

        // null -> returns false
        assertFalse(firstLockCommand.equals(null));

        // different value -> returns false
        assertFalse(thirdLockCommand.equals(secondLockCommand));
    }

    @Test
    public void lockSuccess() {

        LockCommand testLockCommand = new LockCommand("1234");
        testLockCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = LockCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = testLockCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }



}
