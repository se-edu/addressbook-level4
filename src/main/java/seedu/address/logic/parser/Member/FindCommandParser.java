package seedu.address.logic.parser.Member;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.MemberCommand.FindMemberCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindMemberCommand object
 */
public class FindCommandParser implements Parser<FindMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMemberCommand
     * and returns an FindMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMemberCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMemberCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindMemberCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
