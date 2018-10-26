package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.validateNumOfArgs;

import seedu.address.logic.commands.AdjustCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

//@@author jeremiah-ang
/**
 * Parses input arguments and creates a new AdjustCommand object
 */
public class AdjustCommandParser implements Parser<AdjustCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AdjustCommand parse(String args) throws ParseException {
        String[] tokenizedArgs = ParserUtil.tokenize(args);
        validateNumOfArgs(tokenizedArgs, 2, 4);

        int index = 0;

        Year year = null;
        Semester sem = null;
        Code code;
        Grade grade;
        if (tokenizedArgs.length == 4) {
            code = ParserUtil.parseCode(tokenizedArgs[index++]);
            year = ParserUtil.parseYear(tokenizedArgs[index++]);
            sem = ParserUtil.parseSemester(tokenizedArgs[index++]);
            grade = ParserUtil.parseGrade(tokenizedArgs[index++]);
        } else if (tokenizedArgs.length == 2) {
            code = ParserUtil.parseCode(tokenizedArgs[index++]);
            grade = ParserUtil.parseGrade(tokenizedArgs[index++]);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdjustCommand.MESSAGE_USAGE));
        }

        Grade adjustGrade = grade.adjustGrade(grade.value);

        return new AdjustCommand(code, year, sem, adjustGrade);
    }
}
