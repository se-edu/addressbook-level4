package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
public class FindCommandIntegrationTest {
    private TypicalPersons persons = new TypicalPersons();
    private Model model = new ModelManager(persons.getTypicalAddressBook(), new UserPrefs());
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void execute_personFound_success() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        // name matches all keywords
        FindCommand command = prepareCommand("Pauline Alice");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(persons.alice));

        // name matches only one keyword
        command = prepareCommand("Alice NonMatchingKeyword");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(persons.alice));

        // repeated matching keywords
        command = prepareCommand("Alice Alice");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(persons.alice));
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
     * Generates a new {@code FindCommand} by parsing the {@code userInput} and updating {@code model}
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
