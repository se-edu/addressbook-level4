package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.UndoRedoStackUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UndoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private UndoRedoStack undoRedoStack;

    @Before
    public void setUp() {
        Model setUpModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        AddressBook initialAddressBook = new AddressBook(setUpModel.getAddressBook());

        deleteFirstPerson(setUpModel);
        AddressBook firstAddressBook = new AddressBook(setUpModel.getAddressBook());

        deleteFirstPerson(setUpModel);
        AddressBook secondAddressBook = new AddressBook(setUpModel.getAddressBook());

        undoRedoStack = UndoRedoStackUtil.prepareStack(
                Arrays.asList(initialAddressBook, firstAddressBook, secondAddressBook), Collections.emptyList());
    }

    @Test
    public void execute() {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);

        // multiple address books in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single address book in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no address book in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
