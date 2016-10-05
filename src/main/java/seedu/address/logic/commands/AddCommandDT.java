package seedu.address.logic.commands;

/**
 * Created by haliq on 5/10/16.
 */
public class AddCommandDT extends Command {

    public static final String COMMAND_WORD = "adddt";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Adds a datetime pair to the system. "
            + "Parameters: NAME [OPEN] CLOSE [FLOAT] [GROUP] [WORKLOAD [COMPLETION]]\n"
            + "Example: " + COMMAND_WORD + " errands 5/10/2016:0600 5/10/2016:1600 F home 1 0";
    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_BLOCKED = "This clashes with another event.";
    public static final String MESSAGE_INVALID_DT = "The datetime pairs are invalid; dd/mm/yyyy:HHMM";
    public static final String MESSAGE_INVALID_FLOAT = "The float flag is invalid; only T or F.";
    public static final String MESSAGE_INVALID_COMPLETION = "The completion value is a number from 0 to 1 only.";
    public static final String MESSAGE_INVALID_WORKLOAD = "The workload value is a non negative number.";

    public AddCommandDT() {
        //TODO construct dt model based on arguments
    }

    @Override
    public CommandResult execute() {
        //TODO add constructed dt model to system model
    }
}
