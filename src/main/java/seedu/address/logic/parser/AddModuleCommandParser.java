package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.validateNumOfArgs;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credit;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

//@@author alexkmj
/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModuleCommand
     * and returns an AddModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModuleCommand parse(String args) throws ParseException {
        String[] tokenizedArgs = ParserUtil.tokenize(args);
        validateNumOfArgs(tokenizedArgs, 4, 5);

        int index = 0;

        Code code = ParserUtil.parseCode(tokenizedArgs[index++]);
        Year year = ParserUtil.parseYear(tokenizedArgs[index++]);
        Semester semester = ParserUtil.parseSemester(tokenizedArgs[index++]);
        Credit credit = ParserUtil.parseCredit(tokenizedArgs[index++]);

        Module module = null;

        if (tokenizedArgs.length == 4) {
            module = new Module(code, year, semester, credit, null, false);
        } else {
            Grade grade = ParserUtil.parseGrade(tokenizedArgs[index++]);
            module = new Module(code, year, semester, credit, grade, true);
        }

        return new AddModuleCommand(module);
    }
}
