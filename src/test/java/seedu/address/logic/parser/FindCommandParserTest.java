package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyString_failure() throws Exception {
        assertParseFailure("", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nameDoesNotMatchKeyword_personNotFound() throws Exception {
        // One keyword
        assertNamePredicateRejectsPerson(createPredicate("Alice"), "Alibaba Babu");

        // Multiple keywords
        assertNamePredicateRejectsPerson(createPredicate("Alice Bob Charlie"), "Alibaba Babu");
    }

    @Test
    public void parse_nameMatchesKeyword_personFound() throws Exception {
        // One keyword
        assertNamePredicateAcceptsPerson(createPredicate("Alice"), "Alice Bob");

        // Repeated keywords
        assertNamePredicateAcceptsPerson(createPredicate("Alice Bob Bob"), "Alice");

        // Multiple keywords
        assertNamePredicateAcceptsPerson(createPredicate("Alice Bob"), "Alice Bob");
        assertNamePredicateAcceptsPerson(createPredicate("Alice Bob"), "Charlie Bob");
        assertNamePredicateAcceptsPerson(createPredicate("Alice Bob Charlie"), "Alice Charlie");

        // Leading and trailing whitespaces, multiple whitespaces between keywords and name
        assertNamePredicateAcceptsPerson(createPredicate(" \t  Alice  Bob \nCharlie Dick \t "),
                                                            " \n  Bob   Charlie\t");
    }

    /**
     * Asserts that {@code userInput} is unsuccessfully parsed and the error message is equal to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            fail("expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that {@code predicate} accepts a {@code Person} with the given {@code name}
     */
    private void assertNamePredicateAcceptsPerson(Predicate<ReadOnlyPerson> predicate, String name) throws Exception {
        assertTrue(predicate.test(new PersonBuilder().withName(name).build()));
    }

    /**
     * Asserts that {@code predicate} rejects a {@code Person} with the given {@code name}
     */
    private void assertNamePredicateRejectsPerson(Predicate<ReadOnlyPerson> predicate, String name) throws Exception {
        assertFalse(predicate.test(new PersonBuilder().withName(name).build()));
    }

    /**
     * Parses the {@code userInput} as a {@code FindCommand} and returns the predicate created by the
     * {@code FindCommand}
     */
    private Predicate<ReadOnlyPerson> createPredicate(String userInput) throws Exception {
        FindCommand command = parser.parse(userInput);
        return command.predicate;
    }
}
