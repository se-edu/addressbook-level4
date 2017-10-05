package seedu.address.logic.parser;

import org.junit.Test;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.RemarkCommand;

public class RemarkCommandParserTest {

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_invalidArguments_throwsParseException() throws Exception {

        
        assertParseFailure(parser, "r/hey", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "r/lol 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 hey", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArguments_success() throws Exception {
        Command expectedCommand;
        String userInput;

//        expectedCommand= new RemarkCommand(1, "lmao");
//        userInput  = "1 r/lmao";
//        assertParseSuccess(parser, userInput, expectedCommand);
//
//
//        expectedCommand= new RemarkCommand(5, "hey");
//        userInput  = "5 r/hey";
//        assertParseSuccess(parser, userInput, expectedCommand);

    }

}