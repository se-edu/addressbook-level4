package seedu.address.logic.commands;

//import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BONUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//import seedu.address.model.person.Bonus;
//import seedu.address.model.person.Salary;
//import seedu.address.model.person.tag.Tag;

/**
 *  Modify the salary and bonus of an employee's in CHRS
 */
public class ModifyPayCommand extends Command {

    public static final String COMMAND_WORD = "modifypay";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Modify the employee's pay "
            + "selected by the employee's ID. "
            + "Existing salary will be updated based on the percentage and month(s) of bonus included.\n"
            + "Parameters: id/[EMPLOYEE ID] (must be of 6 positive digit) "
            + PREFIX_SALARY + "[SALARY % OF CHANGE]"
            + "AND/OR"
            + PREFIX_BONUS + "[MONTH(S) OF BONUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMPLOYEEID + "123456 "
            + PREFIX_SALARY + "10 "
            + PREFIX_BONUS + "1.5";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Employee's pay not modified yet.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
