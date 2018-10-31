package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.commands.AdjustCommand;
import seedu.address.logic.commands.CapCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.logic.commands.GoalCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RedoModuleCommand;
import seedu.address.logic.commands.UndoModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author alexkmj
/**
 * Parses user input.
 */
public class TranscriptParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern
            .compile("(?<commandWord>\\S+)(?<arguments>.*)");

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
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").replaceFirst("c_", "");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case AddModuleCommand.COMMAND_WORD:
            return new AddModuleCommandParser().parse(arguments);
        case AdjustCommand.COMMAND_WORD:
            return new AdjustCommandParser().parse(arguments);
        case CapCommand.COMMAND_WORD:
            return new CapCommand();
        case DeleteModuleCommand.COMMAND_WORD:
            return new DeleteModuleCommandParser().parse(arguments);
        case EditModuleCommand.COMMAND_WORD:
            return new EditModuleCommandParser().parse(arguments);
        case GoalCommand.COMMAND_WORD:
            return new GoalCommandParser().parse(arguments);
        case RedoModuleCommand.COMMAND_WORD:
            return new RedoModuleCommand();
        case UndoModuleCommand.COMMAND_WORD:
            return new UndoModuleCommand();
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
