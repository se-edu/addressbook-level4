package seedu.address.logic.commands;

import seedu.address.logic.LogicManager;

public class UnlockCommand extends Command{

    public static final String COMMAND_WORD = "unlock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been unlocked!";

    private static String password;

    public UnlockCommand(String keyword) {
        this.password = keyword;
    }

    @Override
    public CommandResult execute() {
        LogicManager.isKnocked = false;
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static String getPassword() {
        return password;
    }
}
