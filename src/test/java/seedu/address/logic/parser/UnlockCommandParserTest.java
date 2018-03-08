package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UnlockCommand;

/**
 * Test scope: similar to {@code UnlockCommandParserTest}.
 * @see UnlockCommandParserTest
 */
public class UnlockCommandParserTest {

    private UnlockCommandParser parser = new UnlockCommandParser();

    @Test
    public void parse_validArgs_returnsLockCommand() {
        // no agrs provided command
        assertParseSuccess(parser, "  ", new UnlockCommand("nopassword"));

        // agrs provided command
        assertParseSuccess(parser, " 1234", new UnlockCommand("1234"));
    }
}
