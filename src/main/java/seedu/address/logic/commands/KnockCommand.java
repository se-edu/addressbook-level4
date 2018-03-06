package seedu.address.logic.commands;

import seedu.address.logic.LogicManager;
import seedu.address.model.person.HideAllPersonPredicate;



public class KnockCommand extends Command{

    public static final String COMMAND_WORD = "knock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Knock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been knocked!";

    private static String password;

    private final HideAllPersonPredicate predicate = new HideAllPersonPredicate();

    public KnockCommand() {
        this.password = null;
    }

    public KnockCommand(String keyword) {
        this.password = keyword;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        LogicManager.isKnocked = true;
        if (password != null) {
            LogicManager.password = password;
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static String getPassword() {
        return password;
    }
}
