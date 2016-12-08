package seedu.address.logic.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;

public class CommandAliasesTest {

    @Test
    public void translate_unknownAlias() {
        String unknownAlias = "unknown";
        String expected = unknownAlias;
        String actual = CommandAliases.translate(unknownAlias);
        assertTrue(expected.equals(actual));
    }

    @Test
    public void translate_knownAlias() {
        String knownAlias = "a";
        String expected = AddCommand.COMMAND_WORD;
        String actual = CommandAliases.translate(knownAlias);
        assertTrue(expected.equals(actual));
    }

}
