package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.PREFIX;
import static seedu.address.logic.parser.ParserUtil.validateNumOfArgs;

import java.util.Map;

import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credit;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

/**
 * Parses input arguments and creates a new EditModuleCommand object
 */
public class EditModuleCommandParser implements Parser<EditModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditModuleCommand
     * and returns an EditModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditModuleCommand parse(String args) throws ParseException {
        String[] tokenizedArgs = ParserUtil.tokenize(args);
        validateNumOfArgs(tokenizedArgs, 2, 13);

        System.out.println(tokenizedArgs[0]);
        Code targetCode = ParserUtil.parseCode(tokenizedArgs[0]);

        Year targetYear = tokenizedArgs[1].startsWith(PREFIX)
                ? null
                : ParserUtil.parseYear(tokenizedArgs[1]);

        Semester targetSemester = targetYear == null
                ? null
                : ParserUtil.parseSemester(tokenizedArgs[2]);

        Map<String, String> argsMap = ParserUtil.mapArgs(tokenizedArgs);
        argsMap.forEach((x, y) -> System.out.println(x + " " + y));
        Code newCode = argsMap.containsKey("code")
                ? ParserUtil.parseCode(argsMap.get("code"))
                : null;

        Year newYear = argsMap.containsKey("year")
                ? ParserUtil.parseYear(argsMap.get("year"))
                : null;

        Semester newSemester = argsMap.containsKey("semester")
                ? ParserUtil.parseSemester(argsMap.get("semester"))
                : null;

        Credit newCredit = argsMap.containsKey("credit")
                ? ParserUtil.parseCredit(argsMap.get("credit"))
                : null;

        Grade newGrade = argsMap.containsKey("grade")
                ? ParserUtil.parseGrade(argsMap.get("grade"))
                : null;

        return new EditModuleCommand(targetCode, targetYear, targetSemester,
                newCode, newYear, newSemester, newCredit, newGrade);
    }

}
