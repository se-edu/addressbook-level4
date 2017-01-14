package seedu.address.logic.parser;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * A container to store all registered command parser classes that are
 * responsible for parsing command strings provided by the user
 *
 */
public class ParserRegistry {

    protected final Map<String, Class<? extends CommandParser>> commandRegistry;

    public ParserRegistry() {
        commandRegistry = Maps.newHashMap();
    }

    /**
     *
     * Registers this command word string with the provided
     * command parser class. One command parser can be associated with
     * multiple command words. However one command word can only be associated with one command parser.
     *
     * @return this ParserRegistry object for daisy chain
     */
    public ParserRegistry registerCommand(Class<? extends CommandParser> parser, String commandWord) {
        commandRegistry.put(commandWord, parser);

        return this;
    }

    /**
    *
    * Registers all associated command word strings with the provided
    * command parser class. One command parser can be associated with
    * multiple command words. However one command word can only be associated with one command parser.
    *
    * @return this ParserRegistry object for daisy chain
    */
    public ParserRegistry registerCommand(Class<? extends CommandParser> parser, Iterable<String> commandWords) {
        for (String commandWord : commandWords) {
            registerCommand(parser, commandWord);
        }

        return this;
    }

    /**
     *
     * Returns a CommandParser registered with the given command word. If no such CommandParser
     * exists, an IncorrectCommandParser is returned instead.
     *
     */
    public CommandParser getParserFromCommandWord(String commandWord) {

        if (!commandRegistry.containsKey(commandWord)) {
            return new IncorrectCommandParser();
        }

        Class<? extends CommandParser> parser = commandRegistry.get(commandWord);

        try {
            CommandParser commandParser = parser.newInstance();
            commandParser.setCommandWord(commandWord);

            return commandParser;
        } catch (Exception e) {
            return new IncorrectCommandParser();
        }
    }
}
