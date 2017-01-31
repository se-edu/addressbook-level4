package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.BASIC_COMMAND_FORMAT;

import java.util.regex.Matcher;

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

/**
 * Parses user input.
 */
public class Parser {

    private ParserRegistry commandParserRegistry;

    public Parser() {
        commandParserRegistry = new ParserRegistry();
        registerCommandParsers();
    }

    private void registerCommandParsers() {
        commandParserRegistry
            .registerCommand(AddCommand.COMMAND_WORD, AddCommandParser.class)
            .registerCommand(FindCommand.COMMAND_WORD, FindCommandParser.class)
            .registerCommand(DeleteCommand.COMMAND_WORD, DeleteCommandParser.class)
            .registerCommand(SelectCommand.COMMAND_WORD, SelectCommandParser.class)
            .registerCommand(EditCommand.COMMAND_WORD, EditCommandParser.class)
            .registerCommand(ListCommand.COMMAND_WORD, ListCommandParser.class)
            .registerCommand(ClearCommand.COMMAND_WORD, ClearCommandParser.class)
            .registerCommand(HelpCommand.COMMAND_WORD, HelpCommandParser.class)
            .registerCommand(ExitCommand.COMMAND_WORD, ExitCommandParser.class);
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

        return commandParserRegistry.getCommandParserForCommandWord(commandWord).prepareCommand(arguments);
    }
}
