package seedu.address.logic.commands;

import seedu.address.logic.LogicManager;

/**
 * Unlocks the addressbook
 */
public class UnlockCommand extends Command {

    public static final String COMMAND_WORD = "unlock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been unlocked!";

    public static final String MESSAGE_INCORRECT_PASSWORD = "Incorrect unlock password!";

    private String password;

    public UnlockCommand(String keyword) {
        this.password = keyword;
    }

    @Override
    public CommandResult execute() {
        if (this.password.compareTo(LogicManager.getPassword()) == 0) {
            LogicManager.unLock();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_INCORRECT_PASSWORD);
        }
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
