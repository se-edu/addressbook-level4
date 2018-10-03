package seedu.address.Members.parser;

import seedu.address.Members.commands.*;
import seedu.address.Members.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddMemberCommand.COMMAND_WORD: case AddMemberCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditMemberCommand.COMMAND_WORD: case EditMemberCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD: case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteMemberCommand.COMMAND_WORD: case DeleteMemberCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearMemberCommand.COMMAND_WORD: case ClearMemberCommand.COMMAND_ALIAS:
            return new ClearMemberCommand();

        case FindMemberCommand.COMMAND_WORD: case FindMemberCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListMemberCommand.COMMAND_WORD: case ListMemberCommand.COMMAND_ALIAS:
            return new ListMemberCommand();

        case HistoryCommand.COMMAND_WORD: case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitMemberCommand.COMMAND_WORD:
            return new ExitMemberCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD: case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD: case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
