package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;

import org.junit.Test;

import seedu.address.logic.commands.DeleteModuleCommand;

public class DeleteModuleCommandParserTest {
    private DeleteModuleCommandParser parser = new DeleteModuleCommandParser();

    @Test
    public void parseAllFieldsPresentSuccess() {
        // leading and trailing whitespace
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                + " " + DISCRETE_MATH.getCode().value
                + " " + DISCRETE_MATH.getYear().value
                + " " + DISCRETE_MATH.getSemester().value,
                new DeleteModuleCommand(DISCRETE_MATH.getCode(), DISCRETE_MATH.getYear(),
                        DISCRETE_MATH.getSemester()));
    }

    @Test
    public void parseOptionalFieldsMissingSuccess() {
        assertParseSuccess(parser, DISCRETE_MATH.getCode().value,
                new DeleteModuleCommand(DISCRETE_MATH.getCode()));
    }
}
