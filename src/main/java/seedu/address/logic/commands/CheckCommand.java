package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Check in or out to work.
 */
public class CheckCommand extends Command {

    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks in/out to work. "
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_PASSWORD + "PASSWORD "
            + PREFIX_MODE + "MODE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "G1234567T "
            + PREFIX_PASSWORD + "Hello12 "
            + PREFIX_MODE + "in ";

    public static final String MESSAGE_SUCCESS = "Successfully checked in/out";
    public static final String MESSAGE_ARGUMENTS = "NRIC: %1$s, Password: %2$s, Mode: %3$s";

    private final String nric;
    private final String password;
    private final String mode;

    public CheckCommand(String nric, String password, String mode){
        requireAllNonNull(nric, password, mode);

        this.nric = nric;
        this.password = password;
        this.mode = mode;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        throw new CommandException(String.format(MESSAGE_ARGUMENTS, nric, password, mode));

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CheckCommand)) {
            return false;
        }

        // state check
        CheckCommand e = (CheckCommand) other;
        return nric.equals(e.nric)
                && password.equals(e.password)
                && mode.equals(e.mode);
    }
}
