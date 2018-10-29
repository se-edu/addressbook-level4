package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AdjustCommand;
import seedu.address.model.module.Module;
import seedu.address.testutil.TypicalModules;

public class AdjustCommandParserTest {

    private AdjustCommandParser parser = new AdjustCommandParser();

    @Test
    public void parseValidCommandSuccess() {
        Module adjustedModule = TypicalModules.duplicateWithGradesAdjusted(TypicalModules.DATA_STRUCTURES);
        assertAdjustParseFullArgumentSuccess(adjustedModule);
        assertAdjustParseCodeOnlySuccess(adjustedModule);
    }

    /**
     * Asserts that parse will be successful given all valid parameters
     * @param expectedModule
     */
    private void assertAdjustParseFullArgumentSuccess(Module expectedModule) {
        String userInput = expectedModule.getCode().value
                + " " + expectedModule.getYear().value
                + " " + expectedModule.getSemester().value
                + " " + expectedModule.getGrade().value;
        AdjustCommand expectedCommand = new AdjustCommand(
                expectedModule.getCode(), expectedModule.getYear(),
                expectedModule.getSemester(), expectedModule.getGrade());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Asserts that parse will be successful given code only
     * @param expectedModule
     */
    private void assertAdjustParseCodeOnlySuccess(Module expectedModule) {
        String userInput = expectedModule.getCode().value
                + " " + expectedModule.getGrade().value;
        AdjustCommand expectedCommand = new AdjustCommand(
                expectedModule.getCode(), null, null, expectedModule.getGrade());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseInvalidCommandFailure() {
        assertWrongNumberArgumentParseFailure();
    }

    /**
     * Assert that given wrong number of argument should fail
     */
    private void assertWrongNumberArgumentParseFailure() {
        String userInput = "1 1 1";
        String expectedMessage = String.format(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdjustCommand.MESSAGE_USAGE));
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
