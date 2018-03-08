package seedu.address.logic.commands;

/**
 * Unlocks the addressbook
 */
public class UnlockCommand extends Command {

    public static final String COMMAND_WORD = "unlock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been unlocked!";

    private String password;

    public UnlockCommand(String keyword) {
        this.password = keyword;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnlockCommand // instanceof handles nulls
                && this.password.equals(((UnlockCommand) other).getPassword())); // state check
    }
}
