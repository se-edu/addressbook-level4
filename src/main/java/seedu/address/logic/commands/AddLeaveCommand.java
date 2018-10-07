package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPROVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Leave;

/**
 * Adds a leave request to the Leave List.
 */
public class AddLeaveCommand extends Command {

    public static final String COMMAND_WORD = "leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Request leave. "
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_DATE + "DATE (DD/MM/YYYY) "
            + PREFIX_APPROVAL + "PENDING ";


    public static final String MESSAGE_SUCCESS = "Leave application requested.";
    public static final String MESSAGE_DUPLICATE_LEAVE = "This request already exist in the database.";

    private final Leave toAdd;

    public AddLeaveCommand(Leave leave) {
        requireNonNull(leave);
        toAdd = leave;
    }



    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.hasLeave(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LEAVE);
        }

        model.addLeave(toAdd);
        model.commitLeaveList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }
}
