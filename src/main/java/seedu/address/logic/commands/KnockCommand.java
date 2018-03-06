package seedu.address.logic.commands;

import seedu.address.logic.LogicManager;

public class KnockCommand extends Command{

    public static final String COMMAND_WORD = "knock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Knock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been knocked!";

    private static String password;

    public KnockCommand() {
        this.password = null;
    }

    public KnockCommand(String keyword) {
        this.password = keyword;
    }

    @Override
    public CommandResult execute() {
        LogicManager.isKnocked = true;
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static String getPassword() {
        return password;
    }
}
