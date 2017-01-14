package seedu.address.logic.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import seedu.address.logic.commands.Command;
import seedu.address.testutil.TestParserRegistry;

public class ParserRegistryTest {

    @Test
    public void parserRegistry_emptyRegistry_incorrectCommand() {
        TestParserRegistry registry = new TestParserRegistry();
        registry.setCommandRegistry(Maps.newHashMap());

        CommandParser parser = registry.getParserFromCommandWord("add");

        assertTrue(parser instanceof IncorrectCommandParser);
    }

    @Test
    public void parserRegistry_commandNotRegistered_incorrectCommand() {
        TestParserRegistry registry = new TestParserRegistry();

        CommandParser parser = registry.getParserFromCommandWord("nonExistantCommandWord");

        assertTrue(parser instanceof IncorrectCommandParser);
    }

    @Test
    public void parserRegistry_nullValue_incorrectCommand() {
        TestParserRegistry registry = new TestParserRegistry();

        CommandParser parser = registry.getParserFromCommandWord(null);

        assertTrue(parser instanceof IncorrectCommandParser);
    }

    @Test
    public void parserRegistry_emptyString_incorrectCommand() {
        TestParserRegistry registry = new TestParserRegistry();

        CommandParser parser = registry.getParserFromCommandWord("");

        assertTrue(parser instanceof IncorrectCommandParser);
    }

    @Test
    public void parserRegistry_validCommandWord_returnsCommandParser() {
        TestParserRegistry registry = new TestParserRegistry();
        registry.registerCommand(TestCommandParser.class, "newCommand");

        CommandParser parser = registry.getParserFromCommandWord("newCommand");

        assertNotNull(registry.getCommandRegistry().get("newCommand"));
        assertTrue(parser instanceof TestCommandParser);
    }

    @Test
    public void parserRegistry_validCommandWords_returnsCommandParser() {
        List<String> commandWords = ImmutableList.of("newCommand", "nc");

        TestParserRegistry registry = new TestParserRegistry();
        registry.registerCommand(TestCommandParser.class, commandWords.toArray(new String[0]));

        List<CommandParser> parsers = ImmutableList.of(
                registry.getParserFromCommandWord("newCommand"),
                registry.getParserFromCommandWord("nc"));

        parsers.forEach(p -> assertTrue(p instanceof TestCommandParser));
        commandWords.forEach(word -> assertNotNull(registry.getCommandRegistry().get(word)));
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
