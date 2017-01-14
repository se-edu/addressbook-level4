package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.Command;

public class ParserRegistryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ParserRegistry registry;

    @Before
    public void setUp() {
        registry = new ParserRegistry();
        registerTestParsers(registry);
    }

    @Test
    public void parserRegistry_emptyRegistry_incorrectCommand() {
        registry = new ParserRegistry();

        assertRegisteredCommandParser("add", registry, IncorrectCommandParser.class);
    }

    @Test
    public void parserRegistry_commandNotRegistered_incorrectCommand() {
        assertRegisteredCommandParser("nonExistantCommandWord", registry, IncorrectCommandParser.class);
    }

    @Test
    public void parserRegistry_nullValue_incorrectCommand() {
        assertRegisteredCommandParser(null, registry, IncorrectCommandParser.class);
    }

    @Test
    public void parserRegistry_emptyString_incorrectCommand() {
        assertRegisteredCommandParser("", registry, IncorrectCommandParser.class);
    }

    @Test
    public void parserRegistry_cannotInitialize_throwsAssertionError() {
        thrown.expect(AssertionError.class);

        // Should not happen under normal cases.
        registry.getCommandParserForCommandWord("invalid");
    }

    @Test
    public void parserRegistry_validCommandWord_returnsCommandParser() {
        registry.registerCommand("newCommand", TestCommandParser.class);

        assertRegisteredCommandParser("newCommand", registry, TestCommandParser.class);
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

    /**
     * Verifies that the returned {@code CommandParser} class from the given
     * {@code ParserRegistry} for the specified command word is indeed an instance of
     * the specified command parser class
     */
    private void assertRegisteredCommandParser(String commandWord, ParserRegistry registry,
            Class<?> commandParserClass) {
        CommandParser parserInRegistry = registry.getCommandParserForCommandWord(commandWord);

        assertTrue(commandParserClass.isInstance(parserInRegistry));
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
