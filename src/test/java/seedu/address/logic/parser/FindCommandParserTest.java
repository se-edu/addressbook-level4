package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.commands.Command;
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
        assertNamePredicateRejectsPerson("James", "John Henry");

        // Multiple keywords
        assertNamePredicateRejectsPerson("James Yu Bernard Tan", "Henry Lee");
    }

    @Test
    public void parse_nameMatchesKeyword_personFound() throws Exception {
        // One keyword
        assertNamePredicateAcceptsPerson("James", "James Henry");

        // Multiple keywords
        assertNamePredicateAcceptsPerson("James Yu", "James Henry");
        assertNamePredicateAcceptsPerson("James Yu", "Yu James");
        assertNamePredicateAcceptsPerson("James Yu Lee", "James Lee");

        // Leading and trailing whitespaces, multiple whitespaces between keywords and name
        assertNamePredicateAcceptsPerson(" \t  James  Yu \nBernard Tan \t ", " \n  Bernard   Ng\t");
    }

    /**
     * Asserts that {@code userInput} is unsuccessfully parsed and the error message is equal to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            fail("An exception should have been thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that {@code userInput} is successfully parsed and {@code name} matches the resulting predicate
     */
    private void assertNamePredicateAcceptsPerson(String userInput, String name) throws Exception {
        assertTrue(getPredicate(userInput).test(new PersonBuilder().withName(name).build()));
    }

    /**
     * Asserts that {@code userInput} is successfully parsed and {@code name} does not match the resulting predicate
     */
    private void assertNamePredicateRejectsPerson(String userInput, String name) throws Exception {
        assertFalse(getPredicate(userInput).test(new PersonBuilder().withName(name).build()));
    }

    /**
     * Returns the predicate from FindCommand given the input {@code userInput}
     */
    private Predicate<ReadOnlyPerson> getPredicate(String userInput) throws Exception {
        Command command = parser.parse(userInput);
        return ((FindCommand) command).predicate;
    }
}
