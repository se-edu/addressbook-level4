package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_THIRD_PERSON;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for SelectCommand.
 */
public class SelectCommandTest {

    private Model model;

    private Index eventTargetedJumpIndex;

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        eventTargetedJumpIndex = Index.fromZeroBased(je.targetIndex);
    }

    @Before
    public void setUp() {
        EventsCenter.getInstance().registerHandler(this);
        model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Index lastPersonIndex = Index.fromOneBased(model.getAddressBook().getPersonList().size());

        assertIndexInBoundsSuccess(INDEX_FIRST_PERSON);
        assertIndexInBoundsSuccess(INDEX_THIRD_PERSON);
        assertIndexInBoundsSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outsideListIndex = Index.fromOneBased(model.getAddressBook().getPersonList().size() + 1);

        assertInvalidIndexFailure(outsideListIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        assertIndexInBoundsSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_success() {
        showFirstPersonOnly(model);

        Index outsideListIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        assertTrue(outsideListIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertInvalidIndexFailure(outsideListIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person from the address book.
     */
    private void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new HashSet<>(Arrays.asList(splitName)));

        assertTrue(model.getFilteredPersonList().size() == 1);
    }

    /**
     * Executes a select command with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertIndexInBoundsSuccess(Index index) throws CommandException {
        eventTargetedJumpIndex = null;

        SelectCommand selectCommand = new SelectCommand(index);
        selectCommand.setData(model, new CommandHistory());
        CommandResult commandResult = selectCommand.execute();

        assertEquals(String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased()),
                commandResult.feedbackToUser);
        assertEquals(index, eventTargetedJumpIndex);
    }

    /**
     * Executes a select command with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertInvalidIndexFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        selectCommand.setData(model, new CommandHistory());

        try {
            selectCommand.execute();
            fail("CommandException was not thrown.");
        }
        catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }
}
