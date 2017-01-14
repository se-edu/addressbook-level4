package seedu.address.logic.parser;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * A container to store all registered {@code CommandParser} classes
 */
public class ParserRegistry {

    protected final Map<String, Class<? extends CommandParser>> commandRegistry;

    public ParserRegistry() {
        commandRegistry = Maps.newHashMap();
    }

    /**
     * Registers the given command word string with the provided
     * command parser class. One command parser can be associated with
     * multiple command words. However one command word can only be associated with one command parser.
     * <p>
     * If the specified command word was previously registered, then this registration entry will
     * replace the current entry in the registry.
     *
     * @return this ParserRegistry object for method chaining
     */
    public ParserRegistry registerCommand(String commandWord, Class<? extends CommandParser> parser) {
        commandRegistry.put(commandWord, parser);

        return this;
    }

    /**
     * Returns a CommandParser registered with the given command word. If no such CommandParser
     * exists, an IncorrectCommandParser is returned instead.
     */
    public CommandParser getCommandParserForCommandWord(String commandWord) {

        if (!commandRegistry.containsKey(commandWord)) {
            return new IncorrectCommandParser();
        }

        Class<? extends CommandParser> parser = commandRegistry.get(commandWord);

        try {
            return parser.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            throw new AssertionError("Command Parser class should have an empty constructor!");
        }
    }
}
