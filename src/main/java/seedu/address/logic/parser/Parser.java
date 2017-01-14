package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Parses user input.
 */
public class Parser {

    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private ParserRegistry commandParserRegistry;

    public Parser() {
        commandParserRegistry = new ParserRegistry();
        registerCommandParsers();
    }

    private void registerCommandParsers() {
        commandParserRegistry
            .registerCommand(AddCommandParser.class, AddCommand.COMMAND_WORD)
            .registerCommand(FindCommandParser.class, FindCommand.COMMAND_WORD)
            .registerCommand(DeleteCommandParser.class, DeleteCommand.COMMAND_WORD)
            .registerCommand(SelectCommandParser.class, SelectCommand.COMMAND_WORD)
            .registerCommand(EditCommandParser.class, EditCommand.COMMAND_WORD)
            .registerCommand(ListCommandParser.class, ListCommand.COMMAND_WORD)
            .registerCommand(ClearCommandParser.class, ClearCommand.COMMAND_WORD)
            .registerCommand(HelpCommandParser.class, HelpCommand.COMMAND_WORD)
            .registerCommand(ExitCommandParser.class, ExitCommand.COMMAND_WORD);
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        return commandParserRegistry.getParserFromCommandWord(commandWord).prepareCommand(arguments);
    }
}
