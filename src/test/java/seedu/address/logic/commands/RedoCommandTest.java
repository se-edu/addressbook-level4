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

public class RedoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private UndoRedoStack undoRedoStack;

    @Before
    public void setUp() {
        Model setUpModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        AddressBook initialAddressBook = new AddressBook(setUpModel.getAddressBook());

        deleteFirstPerson(setUpModel);
        AddressBook firstAddressBookToRedo = new AddressBook(setUpModel.getAddressBook());

        deleteFirstPerson(setUpModel);
        AddressBook secondAddressBookToRedo = new AddressBook(setUpModel.getAddressBook());

        undoRedoStack = UndoRedoStackUtil.prepareStack(Collections.singletonList(initialAddressBook),
                Arrays.asList(secondAddressBookToRedo, firstAddressBookToRedo));
    }

    @Test
    public void execute() {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // multiple address books in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single address book in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no address book in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
