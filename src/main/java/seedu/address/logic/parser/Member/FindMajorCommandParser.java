package seedu.address.logic.parser.Member;

import seedu.address.logic.commands.MemberCommand.FindMajorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.MajorContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class FindMajorCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMajorCommand
     * and returns an FindMajorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMajorCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMajorCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindMajorCommand(new MajorContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
