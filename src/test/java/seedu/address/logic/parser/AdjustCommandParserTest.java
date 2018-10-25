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
        String expectedCode = "CS4234";
        String expectedYear = "1";
        String expectedSem = Semester.SEMESTER_ONE;
        String expectedGrade = "A";
        assertAdjustParseSuccess(expectedCode, expectedSem, expectedYear, expectedGrade);
    }

    /**
     * Asserts that parse will be successful given those parameters
     * @param expectedCode
     * @param expectedSem
     * @param expectedYear
     * @param expectedGrade
     */
    private void assertAdjustParseSuccess(
            String expectedCode, String expectedSem, String expectedYear, String expectedGrade) {
        String userInput = AdjustCommand.COMMAND_WORD + " "
                + expectedCode + " " + expectedYear + " " + expectedSem + " " + expectedGrade;
        Module expectedModule = new ModuleBuilder()
                .withCode(expectedCode).withYear(Integer.parseInt(expectedYear)).withSemester(expectedSem).build();
        Grade expectedAdjustedGrade = new Grade().adjustGrade(expectedGrade);
        AdjustCommand expectedCommand = new AdjustCommand(
                expectedModule.getCode(), expectedModule.getYear(),
                expectedModule.getSemester(), expectedAdjustedGrade);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseInvalidCommandSuccess() {
        String userInput = AdjustCommand.COMMAND_WORD + " 1 1 1";
        String expectedMessage = String.format(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdjustCommand.MESSAGE_USAGE));
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
