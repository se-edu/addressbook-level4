package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AdjustCommand;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.util.ModuleBuilder;

public class AdjustCommandParserTest {

    private AdjustCommandParser parser = new AdjustCommandParser();

    @Test
    public void parseValidCommandSuccess() {
        assertAdjustParseFullArgumentSuccess("CS4234", Semester.SEMESTER_ONE, "1", "A");
        assertAdjustParseCodeOnlySuccess("CS4234", "A");
    }

    /**
     * Asserts that parse will be successful given all valid parameters
     * @param expectedCode
     * @param expectedSem
     * @param expectedYear
     * @param expectedGrade
     */
    private void assertAdjustParseFullArgumentSuccess(
            String expectedCode, String expectedSem, String expectedYear, String expectedGrade) {
        String userInput = expectedCode + " " + expectedYear + " " + expectedSem + " " + expectedGrade;
        Grade expectedAdjustedGrade = new Grade().adjustGrade(expectedGrade);
        Module expectedModule = new ModuleBuilder()
                .withCode(expectedCode).withYear(Integer.parseInt(expectedYear)).withSemester(expectedSem).build();
        AdjustCommand expectedCommand = new AdjustCommand(
                expectedModule.getCode(), expectedModule.getYear(),
                expectedModule.getSemester(), expectedAdjustedGrade);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Asserts that parse will be successful given code only
     * @param expectedCode
     * @param expectedGrade
     */
    private void assertAdjustParseCodeOnlySuccess(String expectedCode, String expectedGrade) {
        String userInput = expectedCode + " " + expectedGrade;
        Grade expectedAdjustedGrade = new Grade().adjustGrade(expectedGrade);
        Module expectedModule = new ModuleBuilder()
                .withCode(expectedCode).build();
        AdjustCommand expectedCommand = new AdjustCommand(
                expectedModule.getCode(), null, null, expectedAdjustedGrade);
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
