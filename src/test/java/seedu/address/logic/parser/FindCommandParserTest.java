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
        assertNamePredicateRejectsPerson(createPredicate("James"), "John Henry");

        // Multiple keywords
        assertNamePredicateRejectsPerson(createPredicate("James Yu Bernard Tan"), "Henry Lee");
    }

    @Test
    public void parse_nameMatchesKeyword_personFound() throws Exception {
        // One keyword
        assertNamePredicateAcceptsPerson(createPredicate("James"), "James Henry");

        // Repeated keywords
        assertNamePredicateAcceptsPerson(createPredicate("James James Lee"), "James");

        // Multiple keywords
        assertNamePredicateAcceptsPerson(createPredicate("James Yu"), "James Henry");
        assertNamePredicateAcceptsPerson(createPredicate("James Yu"), "Yu Feng James");
        assertNamePredicateAcceptsPerson(createPredicate("James Yu Lee"), "James Lee");

        // Leading and trailing whitespaces, multiple whitespaces between keywords and name
        assertNamePredicateAcceptsPerson(createPredicate(" \t  James  Yu \nBernard Tan \t "), " \n  Bernard   Ng\t");
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
     * Asserts that {@code predicate} accepts a Person with the given {@code name}
     */
    private void assertNamePredicateAcceptsPerson(Predicate<ReadOnlyPerson> predicate, String name) throws Exception {
        assertTrue(predicate.test(new PersonBuilder().withName(name).build()));
    }

    /**
     * Asserts that {@code predicate} rejects a Person with the given {@code name}
     */
    private void assertNamePredicateRejectsPerson(Predicate<ReadOnlyPerson> predicate, String name) throws Exception {
        assertFalse(predicate.test(new PersonBuilder().withName(name).build()));
    }

    /**
     * Parses the {@code userInput} as a FindCommand and returns the predicate created by the FindCommand
     */
    private Predicate<ReadOnlyPerson> createPredicate(String userInput) throws Exception {
        FindCommand command = parser.parse(userInput);
        return command.predicate;
    }
}
