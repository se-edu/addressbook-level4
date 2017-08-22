package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

public class FindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() throws Exception {
        /* Case: find multiple persons in list, command with leading spaces and trailing spaces -> 2 persons found */
        String command = "   " + FindCommand.COMMAND_WORD + " Meier   ";
        String expectedResultMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        Model expectedModel = prepareModel(BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in list, command with two keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Benson Meier";
        expectedResultMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        expectedModel = prepareModel(BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find person in list after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        command = FindCommand.COMMAND_WORD + " Meier";
        expectedResultMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        expectedModel = prepareModel(DANIEL);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        assertSelectedCardUnchanged();

        /* Case: find person in list, command with name of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " MeIeR";
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        assertSelectedCardUnchanged();

        /* Case: find person in list, command with part of name -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Mei";
        expectedResultMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        expectedModel = prepareModel();
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        assertSelectedCardUnchanged();

        /* Case: find person not in list -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        assertSelectedCardUnchanged();

        /* Case: find phone number -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + BENSON.getPhone().value;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        assertSelectedCardUnchanged();

        /* Case: find address -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + BENSON.getAddress().value;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        assertSelectedCardUnchanged();

        /* Case: find address -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + BENSON.getEmail().value;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        assertSelectedCardUnchanged();

        /* Case: find tags -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + BENSON.getTags();
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        assertSelectedCardUnchanged();

        /* Case: selects first person in list and finds another person in list -> 1 person found and no cards are
        * selected */
        executeCommand(ListCommand.COMMAND_WORD);
        assert getPersonListPanel().getListSize() == 6;
        executeCommand(SelectCommand.COMMAND_WORD + " 1");
        assert getPersonListPanel().getSelectedCardIndex() == 0;
        waitUntilBrowserLoaded(getBrowserPanel());
        command = FindCommand.COMMAND_WORD + " Daniel";
        expectedResultMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        expectedModel = prepareModel(DANIEL);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        assertCardUnselected();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        expectedResultMessage = MESSAGE_UNKNOWN_COMMAND;
        assertCommandFailure(command, expectedResultMessage);
    }

    @Test
    public void find_emptyList() throws Exception {
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getPersonListPanel().getListSize() == 0;

        /* Case: find person not in list -> 0 persons found */
        String command = FindCommand.COMMAND_WORD + " Meier";
        String expectedResultMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        Model expectedModel = prepareModel();
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}. Also verifies that
     * the status bar remains unchanged, and the command box has the default style class.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage)
            throws Exception {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxStyleDefault();
        assertStatusBarUnchanged();

        clockRule.setInjectedClockToCurrentTime();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}. Also verifies that
     * the browser url, selected card and status bar remain unchanged, and the command box has the error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage)
            throws Exception {
        Model expectedModel =
                prepareModel(getTestApp().getModel().getFilteredPersonList().toArray(new ReadOnlyPerson[0]));

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxStyleError();
        assertStatusBarUnchanged();

        clockRule.setInjectedClockToCurrentTime();
    }

    /**
     * Returns a {@code Model} backed by {@code TestApp}'s address book, displaying only {@code displayedPersons}.
     */
    private Model prepareModel(ReadOnlyPerson... displayedPersons) {
        ModelManager model = new ModelManager(getTestApp().getModel().getAddressBook(), new UserPrefs());
        Optional<Predicate<ReadOnlyPerson>> predicate =
                Arrays.stream(displayedPersons).map(this::personEquals).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(unused -> false));
        return model;
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyPerson} equals to {@code other}.
     */
    private Predicate<ReadOnlyPerson> personEquals(ReadOnlyPerson other) {
        return person -> person.equals(other);
    }
}
