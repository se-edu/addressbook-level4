package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

public class ReversibleCommandTest {
    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());

    @Test
    public void saveAddressBookSnapshotAndUndo() throws Exception {
        Model expectedModel = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        deleteCommand.saveAddressBookSnapshot();
        showFirstPersonOnly();

        ReadOnlyPerson toRemove = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.deletePerson(toRemove);

        deleteCommand.undo();
        assertEquals(expectedModel, model);

        deleteCommand.redo();
        expectedModel.deletePerson(toRemove);
        assertEquals(expectedModel, model);
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person from the address book.
     */
    private void showFirstPersonOnly() {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new HashSet<>(Collections.singletonList(splitName[0])));
        assertTrue(model.getFilteredPersonList().size() == 1);
    }
}
