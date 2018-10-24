package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.validateNumOfArgs;

import seedu.address.logic.commands.AdjustCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.util.ModuleBuilder;

//@@author jeremiah-ang
/**
 * Parses input arguments and creates a new AdjustCommand object
 */
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
        Year year = ParserUtil.parseYear(tokenizedArgs[index++]);
        Semester sem = ParserUtil.parseSemester(tokenizedArgs[index++]);
        Module moduleToFind = new ModuleBuilder()
                .withCode(code.toString())
                .withYear(Integer.parseInt(year.toString()))
                .withSemester(sem.toString()).build();

        Grade grade = ParserUtil.parseGrade(tokenizedArgs[index++]);
        Grade adjustGrade = grade.adjustGrade(grade.value);

        return new AdjustCommand(moduleToFind, adjustGrade);
    }
}
