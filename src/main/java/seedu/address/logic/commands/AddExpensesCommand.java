package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_EXPENSES_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSES_AMOUNT;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenses.Expenses;

/**
 * Adds an expense to the Expenses List.
 */
public class AddExpensesCommand extends Command {
    public static final String COMMAND_WORD = "addex";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Request Expenses. "
            + "Parameters: "
            + PREFIX_EMPLOYEE_EXPENSES_ID + "EMPLOYEE_ID "
            + PREFIX_EXPENSES_AMOUNT + "EXPENSES AMOUNT";
    public static final String MESSAGE_SUCCESS = "Adding expenses requested.";
    public static final String MESSAGE_DUPLICATE_EXPENSES = "Expenses list contains duplicate expenses";
    private final Expenses toAdd;
    public AddExpensesCommand(Expenses expenses) {
        requireNonNull(expenses);
        toAdd = expenses;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.hasExpenses(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSES);
        }
        model.addExpenses(toAdd);
        model.commitExpensesList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
