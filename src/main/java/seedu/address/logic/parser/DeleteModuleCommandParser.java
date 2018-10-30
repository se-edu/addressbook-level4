package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.validateNumOfArgs;

import java.util.Arrays;
import java.util.HashSet;

import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

//@@author alexkmj
/**
 * Parses input arguments and creates a new DeleteModuleCommand object
 */
public class DeleteModuleCommandParser implements Parser<DeleteModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteModuleCommand
     * and returns an DeleteModuleCommand object for execution.
     *
     * @param args arguments concatenated in a String
     * @return {@code DeleteModuleCommand} for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteModuleCommand parse(String args) throws ParseException {
        String[] tokenizedArgs = ParserUtil.tokenize(args);
        validateNumOfArgs(tokenizedArgs, new HashSet<>(Arrays.asList(1, 3)));

        int index = 0;

        Code code = ParserUtil.parseCode(tokenizedArgs[index++]);

        if (tokenizedArgs.length == 3) {
            Year year = ParserUtil.parseYear(tokenizedArgs[index++]);
            Semester semester = ParserUtil.parseSemester(tokenizedArgs[index++]);
            return new DeleteModuleCommand(code, year, semester);
        }

        return new DeleteModuleCommand(code);
    }
}
