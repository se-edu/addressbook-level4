package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Arrays;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_AGE;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_ALPHABETICAL;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String word = args;
        String[] possibleSortWords = {SORTWORD_AGE.toString(), SORTWORD_ALPHABETICAL.toString()};
        if (!areSortWordsPresent(word, possibleSortWords)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(ParserUtil.parseSortWord(args));
    }

    /**
     * Returns true if args is a sortWord.
     */
    private static boolean areSortWordsPresent(String inputWord, String[] sortWords) {
        return Arrays.stream(sortWords).parallel().anyMatch(inputWord::contains);
    }
}