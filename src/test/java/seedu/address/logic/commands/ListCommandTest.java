package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        ListCommand listCommand = prepareCommand();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        CommandResult result = listCommand.execute();
        assertEquals(model, expectedModel);
        assertEquals(result.feedbackToUser, ListCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);

        ListCommand listCommand = prepareCommand();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        CommandResult result = listCommand.execute();
        assertEquals(model, expectedModel);
        assertEquals(result.feedbackToUser, ListCommand.MESSAGE_SUCCESS);
    }

    /**
     * Updates the filtered list to show only the first person in the {@code model}'s address book.
     */
    private void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new HashSet<>(Arrays.asList(splitName)));

        assertTrue(model.getFilteredPersonList().size() == 1);
    }

    /**
     * Returns a {@code ListCommand} with its dependencies set up.
     */
    private ListCommand prepareCommand() {
        ListCommand listCommand = new ListCommand();
        listCommand.setData(model, new CommandHistory());
        return listCommand;
    }
}
