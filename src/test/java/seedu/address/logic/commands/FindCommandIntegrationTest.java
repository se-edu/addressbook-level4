package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
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
    private Model model;
    private TypicalPersons persons = new TypicalPersons();
    private FindCommandParser parser = new FindCommandParser();

    @Before
    public void setUp() {
        model = new ModelManager(persons.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_personFound_success() throws Exception {
        FindCommand command = prepareCommand("Alice", model);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        assertCommandSuccess(command, expectedMessage, Collections.singletonList(persons.alice));
    }

    @Test
    public void execute_multiplePersonsFound_success() throws Exception {
        FindCommand command = prepareCommand("Meier", model);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        assertCommandSuccess(command, expectedMessage, Arrays.asList(persons.benson, persons.daniel));
    }

    @Test
    public void execute_noPersonFound_success() throws Exception {
        FindCommand command = prepareCommand("Bob", model);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Generates a new {@code FindCommand} by parsing the {@code userInput} and updating {@code model}
     */
    private FindCommand prepareCommand(String userInput, Model model) throws Exception {
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
