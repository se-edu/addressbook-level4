package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LockCommand;

/**
 * Test scope: similar to {@code LockCommandParserTest}.
 * @see LockCommandParserTest
 */
public class LockCommandParserTest {

    private LockCommandParser parser = new LockCommandParser();

    @Test
    public void parse_validArgs_returnsLockCommand() {
        // no agrs provided command
        assertParseSuccess(parser, "", new LockCommand());

        // agrs provided command
        assertParseSuccess(parser, "1234", new LockCommand("1234"));
    }
}
