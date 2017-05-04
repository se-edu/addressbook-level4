package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TypicalPersons;

public class ReversibleCommandTest {
    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeUndoRedo() throws Exception {
        Model expectedModel = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());

        DummyCommand dummyCommand = new DummyCommand(model);
        dummyCommand.execute();
        showFirstPersonOnly(); // tests that upon undo, all persons are shown

        dummyCommand.undo();
        assertEquals(expectedModel, model);

        ReadOnlyPerson toRemove = model.getFilteredPersonList().get(0);
        expectedModel.deletePerson(toRemove);
        showFirstPersonOnly(); // tests that upon redo, all persons are shown

        dummyCommand.redo();
        assertEquals(expectedModel, model);
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person from the address book.
     */
    private void showFirstPersonOnly() {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new HashSet<>(Collections.singletonList(splitName[0])));
        assert model.getFilteredPersonList().size() == 1;
    }

    /**
     * Deletes the first person in the model's filtered list.
     */
    class DummyCommand extends ReversibleCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeReversibleCommand() throws CommandException {
            ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(0);
            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                fail("Impossible: personToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
