package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import seedu.address.model.tag.Tag;

public class FindCommandSystemTest extends AddressBookSystemTest {

    private static final Predicate<ReadOnlyPerson> PREDICATE_SHOW_NO_PERSONS = unused -> false;

    @Test
    public void find() throws Exception {
        /* Case: find multiple persons in list, command with leading spaces and trailing spaces -> 2 persons found */
        String command = "   " + FindCommand.COMMAND_WORD + " Meier   ";
        Model expectedModel = prepareModelFilteredList(BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where list is displaying the persons we are finding -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Meier";
        expectedModel = prepareModelFilteredList(BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " Carl";
        expectedModel = prepareModelFilteredList(CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in list, command with two keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Benson Daniel";
        expectedModel = prepareModelFilteredList(BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find person in list after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getTestApp().getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindCommand.COMMAND_WORD + " Meier";
        expectedModel = prepareModelFilteredList(DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in list, command with name of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in list, command with part of name -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Mei";
        expectedModel = prepareModelFilteredList();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in list -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in list -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in list -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in list -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in list -> 0 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: selects first person in list and finds another person in list -> 1 person found and card deselected */
        executeCommand(ListCommand.COMMAND_WORD);
        assert getPersonListPanel().getListSize() == 6;
        executeCommand(SelectCommand.COMMAND_WORD + " 1");
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindCommand.COMMAND_WORD + " Daniel";
        expectedModel = prepareModelFilteredList(DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertCardDeselected();

        /* Case: selects and finds the same person in list -> 1 person found and card deselected */
        executeCommand(SelectCommand.COMMAND_WORD + " 1");
        assert getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        assertCommandSuccess(command, expectedModel);
        assertCardDeselected();

        /* Case: find person in empty list -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getTestApp().getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " Meier";
        expectedModel = prepareModelFilteredList();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        expectedResultMessage = MESSAGE_UNKNOWN_COMMAND;
        assertCommandFailure(command, expectedResultMessage);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) throws Exception {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage)
            throws Exception {
        Model expectedModel =
                prepareModelFilteredList(
                        getTestApp().getModel().getFilteredPersonList().toArray(new ReadOnlyPerson[0]));

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
    private Model prepareModelFilteredList(ReadOnlyPerson... displayedPersons) {
        ModelManager model = new ModelManager(getTestApp().getModel().getAddressBook(), new UserPrefs());
        Optional<Predicate<ReadOnlyPerson>> predicate =
                Arrays.stream(displayedPersons).map(this::personEquals).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_SHOW_NO_PERSONS));
        return model;
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyPerson} equals to {@code other}.
     */
    private Predicate<ReadOnlyPerson> personEquals(ReadOnlyPerson other) {
        return person -> person.equals(other);
    }
}
