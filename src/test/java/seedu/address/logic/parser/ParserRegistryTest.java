package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;

import seedu.address.logic.commands.Command;
import seedu.address.testutil.TestParserRegistry;

public class ParserRegistryTest {

    private TestParserRegistry registry;

    @Before
    public void setUp() {
        registry = new TestParserRegistry();
        registerTestParsers(registry);
    }

    @Test
    public void parserRegistry_emptyRegistry_incorrectCommand() {
        registry.setCommandRegistry(Maps.newHashMap());

        assertParserWithCommand("add", registry, IncorrectCommandParser.class);
    }

    @Test
    public void parserRegistry_commandNotRegistered_incorrectCommand() {
        assertParserWithCommand("nonExistantCommandWord", registry, IncorrectCommandParser.class);
    }

    @Test
    public void parserRegistry_nullValue_incorrectCommand() {
        assertParserWithCommand(null, registry, IncorrectCommandParser.class);
    }

    @Test
    public void parserRegistry_emptyString_incorrectCommand() {
        assertParserWithCommand("", registry, IncorrectCommandParser.class);
    }

    @Test
    public void parserRegistry_cannotInitialize_incorrectCommand() {
        assertParserWithCommand("invalid", registry, IncorrectCommandParser.class);
    }

    @Test
    public void parserRegistry_validCommandWord_returnsCommandParser() {
        registry.registerCommand("newCommand", TestCommandParser.class);

        assertParserWithCommand("newCommand", registry, TestCommandParser.class);
    }

    /** Helper methods and classes */

    /**
     * Populate the given registry with some test parsers.
     */
    private void registerTestParsers(ParserRegistry registry) {
        registry
            .registerCommand("add", AddCommandParser.class)
            .registerCommand("find", FindCommandParser.class)
            .registerCommand("edit", EditCommandParser.class)
            .registerCommand("delete", DeleteCommandParser.class)
            .registerCommand("invalid", CommandParser.class);
    }

    private void assertParserWithCommand(String commandWord, ParserRegistry registry,
            Class<?> parser) {
        CommandParser parserInRegistry = registry.getParserFromCommandWord(commandWord);

        assertTrue(parser.isInstance(parserInRegistry));
    }

    /**
     * A command parser class purely for testing. Does not do anything.
     */
    public static class TestCommandParser extends CommandParser {

        @Override
        public Command prepareCommand(String args) {
            return null;
        }
    }
}
