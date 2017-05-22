package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        Predicate<ReadOnlyPerson> predicate = createPredicate(nameKeywords, NameContainsKeywordsPredicate::new);

        return new FindCommand(predicate);
    }

    /**
     * Creates a {@code Predicate<ReadOnlyPerson} using {@code keywords} for {@code func}
     */
    private Predicate<ReadOnlyPerson> createPredicate(String[] keywords,
                    Function<String, ? extends Predicate<ReadOnlyPerson>> function) {
        return Arrays.stream(keywords).map(function).map(predicate -> (Predicate<ReadOnlyPerson>) predicate)
                .reduce(Predicate::or).get();
    }

}
