package seedu.address.logic.commands;

import seedu.address.logic.LogicManager;

public class UnknockCommand extends Command{

    public static final String COMMAND_WORD = "unknock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Knock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been unknocked!";

    private static String password;

    public UnknockCommand() {
        this.password = null;
    }

    public UnknockCommand(String keyword) {
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
