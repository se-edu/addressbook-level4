package systemtests;

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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

public class FindCommandSystemTest extends AddressBookSystemTest {

    private static final Predicate<ReadOnlyPerson> PREDICATE_SHOW_NO_PERSONS = unused -> false;

    @Test
    public void find() throws Exception {
        /* Case: find multiple persons in list, command with leading spaces and trailing spaces -> 2 persons found */
        String command = "   " + FindCommand.COMMAND_WORD + " Meier   ";
        Model expectedModel = prepareModelFilteredList(BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person in list after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getTestApp().getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindCommand.COMMAND_WORD + " Meier";
        expectedModel = prepareModelFilteredList(DANIEL);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person not in list -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Mark";
        expectedModel = prepareModelFilteredList();
        assertCommandSuccess(command, expectedModel);

        /* Case: find person in empty list -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getTestApp().getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " Meier";
        expectedModel = prepareModelFilteredList();
        assertCommandSuccess(command, expectedModel);

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        String expectedResultMessage = MESSAGE_UNKNOWN_COMMAND;
        assertCommandFailure(command, expectedResultMessage);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar, browser url and selected card remain unchanged, and the command box has the
     * default style class.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) throws Exception {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
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
