package seedu.address.logic.parser;

import java.util.logging.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;

/**
 * The generic command parser class for all commands that do not require any
 * arguments.
 *
 */
public class GenericCommandParser extends CommandParser {

    public static final ImmutableMap<String, Class<? extends Command>> COMMANDS;

    private static final Logger logger = LogsCenter.getLogger(GenericCommandParser.class);

    private String commandWord = "";

    static {
        Builder<String, Class<? extends Command>> builder = new Builder<>();

        builder
            .put(ListCommand.COMMAND_WORD, ListCommand.class)
            .put(ExitCommand.COMMAND_WORD, ExitCommand.class)
            .put(ClearCommand.COMMAND_WORD, ClearCommand.class)
            .put(HelpCommand.COMMAND_WORD, HelpCommand.class);

        COMMANDS = builder.build();
    }

    @Override
    public void setCommandWord(String commandWord) {
        this.commandWord = commandWord;
    }

    @Override
    public Command prepareCommand(String args) {

        Class<? extends Command> commandClass = COMMANDS.get(commandWord);

        if (commandClass == null) {
            return new IncorrectCommand(Messages.MESSAGE_UNKNOWN_COMMAND);
        }

        try {
            return commandClass.newInstance();

        } catch (IllegalAccessException | InstantiationException e) {
            logger.warning("Supplied Command class:" + commandClass.getName() + " does not have an empty constructor.");
            return new IncorrectCommand(Messages.MESSAGE_UNKNOWN_COMMAND);

        } catch (ExceptionInInitializerError | SecurityException e) {
            logger.warning("Supplied Command class:" + commandClass.getName() + " cannot be initalized.");
            return new IncorrectCommand(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
