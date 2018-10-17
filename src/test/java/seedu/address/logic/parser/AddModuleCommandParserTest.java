package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;

import org.junit.Test;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;

//@@author alexkmj
public class AddModuleCommandParserTest {
    private AddModuleCommandParser parser = new AddModuleCommandParser();

    @Test
    public void parseAllFieldsPresentSuccess() throws Exception {
        // leading and trailing whitespace
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                + " " + DISCRETE_MATH.getCode().value
                + " " + DISCRETE_MATH.getYear().value
                + " " + DISCRETE_MATH.getSemester().value
                + " " + DISCRETE_MATH.getCredits().value
                + " " + DISCRETE_MATH.getGrade().value, new AddModuleCommand(DISCRETE_MATH));
    }

    @Test
    public void parseOptionalFieldsMissingSuccess() {
        Module expectedModule = new ModuleBuilder(DISCRETE_MATH)
                .noGrade()
                .withCompleted(false)
                .build();

        // no grade
        assertParseSuccess(parser, DISCRETE_MATH.getCode().value
                + " " + DISCRETE_MATH.getYear().value
                + " " + DISCRETE_MATH.getSemester().value
                + " " + DISCRETE_MATH.getCredits().value, new AddModuleCommand(expectedModule));
    }
}
