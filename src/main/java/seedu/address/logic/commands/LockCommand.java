package seedu.address.logic.commands;

import seedu.address.logic.LogicManager;
import seedu.address.model.person.HideAllPersonPredicate;


/**
 * Locks the app with a password
 * */
public class LockCommand extends Command {

    public static final String COMMAND_WORD = "lock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been locked!";

    private String password;

    private final HideAllPersonPredicate predicate = new HideAllPersonPredicate();

    public LockCommand() {
        this.password = null;
    }

    public LockCommand(String keyword) {
        this.password = keyword;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        LogicManager.lock();
        if (this.password != null) {
            LogicManager.setPassword(this.password);
        } else {
            LogicManager.setPassword("nopassword");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {

        if (this.password == null && other instanceof LockCommand && ((LockCommand) other).getPassword() == null) {
            return true;
        }

        if (this.password == null && other instanceof LockCommand && ((LockCommand) other).getPassword() != null) {
            return false;
        }

        if (this.password != null && other instanceof LockCommand && ((LockCommand) other).getPassword() == null) {
            return false;
        }

        return other == this // short circuit if same object
                || (other instanceof LockCommand // instanceof handles nulls
                && this.password.compareTo(((LockCommand) other).getPassword()) == 0); // state check
    }
}
