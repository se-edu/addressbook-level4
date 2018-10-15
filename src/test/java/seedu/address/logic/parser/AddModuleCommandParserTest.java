package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;

import org.junit.Test;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.util.ModuleBuilder;

public class AddModuleCommandParserTest {
    private AddModuleCommandParser parser = new AddModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // leading and trailing whitespace
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                + " " + DISCRETE_MATH.getCode().value
                + " " + DISCRETE_MATH.getYear().value
                + " " + DISCRETE_MATH.getSemester().value
                + " " + DISCRETE_MATH.getCredits().value
                + " " + DISCRETE_MATH.getGrade().value, new AddModuleCommand(DISCRETE_MATH));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
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
