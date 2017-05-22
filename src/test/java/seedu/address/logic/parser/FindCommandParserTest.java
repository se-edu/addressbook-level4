package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyString_failure() throws Exception {
        assertParseFailure("", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonMatchingInput_personNotFound() throws Exception {
        // One keyword
        assertPredicateRejectsPerson("James", "John Henry");

        // Multiple keywords
        assertPredicateRejectsPerson("James Yu Bernard Tan", "Henry Lee");
    }

    @Test
    public void parse_matchingInput_personFound() throws Exception {
        // One keyword
        assertPredicateAcceptsPerson("James", "James Henry");

        // Multiple keywords
        assertPredicateAcceptsPerson("James Yu", "James Henry");
        assertPredicateAcceptsPerson("James Yu", "Yu James");
        assertPredicateAcceptsPerson("James Yu Lee", "James Lee");

        // Leading and trailing whitespaces, multiple whitespaces between keywords and name
        assertPredicateAcceptsPerson(" \t  James  Yu \nBernard Tan \t ", " \n  Bernard   Ng\t");
    }

    /**
     * Asserts that {@code userInput} is unsuccessfully parsed and the error message is equal to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        Command command = parser.parse(userInput);

        assertEquals(expectedMessage, ((IncorrectCommand) command).feedbackToUser);
    }

    /**
     * Asserts that {@code userInput} is successfully parsed and {@code name} matches the resulting predicate
     */
    private void assertPredicateAcceptsPerson(String userInput, String name) throws Exception {
        assertTrue(getPredicate(userInput).test(getPersonWithName(name)));
    }

    /**
     * Asserts that {@code userInput} is successfully parsed and {@code name} does not match the resulting predicate
     */
    private void assertPredicateRejectsPerson(String userInput, String name) throws Exception {
        assertFalse(getPredicate(userInput).test(getPersonWithName(name)));
    }

    /**
     * Returns the predicate from FindCommand given the input {@code userInput}
     */
    private Predicate<ReadOnlyPerson> getPredicate(String userInput) {
        Command command = parser.parse(userInput);
        return ((FindCommand) command).predicate;
    }

    /**
     * Returns a ReadOnlyPerson with {@code name}
     */
    private ReadOnlyPerson getPersonWithName(String name) throws Exception {
        return new PersonBuilder().withName(name).build();
    }
}
