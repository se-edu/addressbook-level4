package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PredicateBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandIntegrationTest {
    private Model model;
    private TypicalPersons persons = new TypicalPersons();

    @Before
    public void setUp() {
        model = new ModelManager(persons.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_personFound_success() throws Exception {
        Predicate<ReadOnlyPerson> predicate = new PredicateBuilder().withNamePredicate("Alice").build();
        FindCommand command = prepareCommand(predicate, model);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        assertCommandSuccess(command, expectedMessage, Collections.singletonList(persons.alice));
    }

    @Test
    public void execute_multiplePersonsFound_success() throws Exception {
        Predicate<ReadOnlyPerson> predicate = new PredicateBuilder().withNamePredicate("Meier").build();
        FindCommand command = prepareCommand(predicate, model);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        assertCommandSuccess(command, expectedMessage, Arrays.asList(persons.benson, persons.daniel));
    }

    @Test
    public void execute_noPersonFound_success() throws Exception {
        Predicate<ReadOnlyPerson> predicate = new PredicateBuilder().withNamePredicate("Bob").build();
        FindCommand command = prepareCommand(predicate, model);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        assertCommandSuccess(command, expectedMessage, new ArrayList<>());
    }

    /**
     * Generates a new {@code FindCommand} which upon execution, tests {@code predicate} in {@code model}.
     */
    private FindCommand prepareCommand(Predicate<ReadOnlyPerson> predicate, Model model) {
        FindCommand command = new FindCommand(predicate);
        command.setData(model, new CommandHistory());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}
     */
    private void assertCommandSuccess(Command command, String expectedMessage, List<ReadOnlyPerson> expectedList)
            throws Exception {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
    }
}
