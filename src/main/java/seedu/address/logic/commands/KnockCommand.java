package seedu.address.logic.commands;

import seedu.address.model.AddressBook;

import static java.util.Objects.requireNonNull;

public class KnockCommand extends Command{

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Knock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been knocked!";

    public static String keyword;

    public KnockCommand() {
        this.keyword = null;
    }

    public KnockCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute() {
        
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
