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
    public void parse_oneKeyword_success() throws Exception {
        assertPredicateAcceptsPerson("James", "James Henry");
        assertPredicateRejectsPerson("James", "John Henry");
    }

    @Test
    public void parse_multipleKeywords_success() throws Exception {
        assertPredicateAcceptsPerson("James Yu", "James Henry");
        assertPredicateAcceptsPerson("James Yu Lee", "John Yu");
        assertPredicateRejectsPerson("James Yu Bernard Tan", "Henry Lee");
    }

    /**
     * Asserts that {@code userInput} is unsuccessfully parsed and the error message is equal to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        Command command = parser.parse(userInput);

        IncorrectCommand incorrectCommand = (IncorrectCommand) command;

        assertEquals(expectedMessage, incorrectCommand.feedbackToUser);
    }

    /**
     * Asserts that {@code userInput} is successfully parsed and the person with {@code name} is found
     */
    private void assertPredicateAcceptsPerson(String userInput, String name) throws Exception {
        Command command = parser.parse(userInput);
        Predicate<ReadOnlyPerson> actualPredicate = ((FindCommand) command).predicate;

        ReadOnlyPerson personToTest = new PersonBuilder().withName(name).build();
        assertTrue(actualPredicate.test(personToTest));
    }

    /**
     * Asserts that {@code userInput} is successfully parsed and the person with {@code name} is not found
     */
    private void assertPredicateRejectsPerson(String userInput, String name) throws Exception {
        Command command = parser.parse(userInput);
        Predicate<ReadOnlyPerson> actualPredicate = ((FindCommand) command).predicate;

        ReadOnlyPerson personToTest = new PersonBuilder().withName(name).build();
        assertFalse(actualPredicate.test(personToTest));
    }
}
