package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class CountCommandTest {

    private Model model;
    private Model expectedModel;
    private CountCommand countCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        countCommand = new CountCommand();
        countCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_countIsReturned_listsAllPersons() {
        assertCommandSuccess(
                countCommand,
                model,
                Integer.toString(model.getAddressBook().getPersonList().size())
                        + " persons in the address book",
                expectedModel);
    }
}
