package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private TypicalPersons persons = new TypicalPersons();
    private Model model = new ModelManager(persons.getTypicalAddressBook(), new UserPrefs());
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void equals() {
        Set<String> firstKeyword = new HashSet<String>(Arrays.asList("first"));
        Set<String> secondKeyword = new HashSet<String>(Arrays.asList("second"));

        FindCommand findFirstCommand = new FindCommand(firstKeyword);
        FindCommand findSecondCommand = new FindCommand(secondKeyword);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstKeyword);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_singlePersonFound_success() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<ReadOnlyPerson> expectedList = Collections.singletonList(persons.alice);

        // matches all keywords
        FindCommand command = prepareCommand("Pauline Alice");
        assertCommandSuccess(command, expectedMessage, expectedList);

        // matches only one keyword
        command = prepareCommand("Alice NonMatchingKeyword");
        assertCommandSuccess(command, expectedMessage, expectedList);

        // repeated matching keywords
        command = prepareCommand("Alice Alice");
        assertCommandSuccess(command, expectedMessage, expectedList);
    }

    @Test
    public void execute_multiplePersonsFound_success() throws Exception {
        // multiple persons match one keyword
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareCommand("Meier");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(persons.benson, persons.daniel));

        // multiple persons match multiple keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(persons.carl, persons.elle, persons.fiona));
    }

    @Test
    public void execute_noPersonFound_success() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        // valid name that does not match any person
        FindCommand command = prepareCommand("NoSuchPerson");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());

        // invalid name as keyword
        command = prepareCommand("*** $%^");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}. The {@code FindCommand} executes on {@code model}.
     */
    private FindCommand prepareCommand(String userInput) throws Exception {
        FindCommand command = parser.parse(userInput);
        command.setData(model, new CommandHistory());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList)
            throws Exception {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
