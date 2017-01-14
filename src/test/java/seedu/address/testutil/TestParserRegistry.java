package seedu.address.testutil;

import java.util.Map;

import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.ParserRegistry;

/**
 * A parser registry class solely used for testing
 *
 */
public class TestParserRegistry extends ParserRegistry {

    public Map<String, Class<? extends CommandParser>> getCommandRegistry() {
        return commandRegistry;
    }

    public void setCommandRegistry(Map<String, Class<? extends CommandParser>> newRegistry) {
        commandRegistry.clear();
        commandRegistry.putAll(newRegistry);
    }
}
