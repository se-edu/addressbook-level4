package seedu.address.logic.parser;

import seedu.address.logic.commands.AdjustCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;

import static seedu.address.logic.parser.ParserUtil.validateNumOfArgs;

public class AdjustCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AdjustCommand parse(String args) throws ParseException {
        String[] tokenizedArgs = ParserUtil.tokenize(args);
        validateNumOfArgs(tokenizedArgs, 4, 5);

        int index = 0;

        Code code = ParserUtil.parseCode(tokenizedArgs[index++]);
        Grade grade = ParserUtil.parseGrade(tokenizedArgs[index++]);
        Grade adjustGrade = grade.adjustGrade(grade.value);

        return new AdjustCommand(code, adjustGrade);
    }
}
