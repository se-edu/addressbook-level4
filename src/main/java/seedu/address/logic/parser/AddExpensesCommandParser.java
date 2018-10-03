package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_EXPENSES_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSES_AMOUNT;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddExpensesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenses.EmployeeExpensesId;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesAmount;

/**
 * Parses input arguments and creates a new AddExpensesCommand object
 */
public class AddExpensesCommandParser implements Parser<AddExpensesCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddExpensesCommand
     * and returns an AddExpensesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExpensesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMPLOYEE_EXPENSES_ID, PREFIX_EXPENSES_AMOUNT);
        if (!arePrefixesPresent(argMultimap, PREFIX_EMPLOYEE_EXPENSES_ID, PREFIX_EXPENSES_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpensesCommand.MESSAGE_USAGE));
        }
        EmployeeExpensesId employeeExpensesId = ParserUtil.parseEmployeeExpensesId(argMultimap.getValue
                (PREFIX_EMPLOYEE_EXPENSES_ID).get());
        ExpensesAmount expensesAmount = ParserUtil.parseExpensesAmount(argMultimap.getValue(PREFIX_EXPENSES_AMOUNT)
                .get());
        Expenses expenses = new Expenses (employeeExpensesId, expensesAmount);
        return new AddExpensesCommand(expenses);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
