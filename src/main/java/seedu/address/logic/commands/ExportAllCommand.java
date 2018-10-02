package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author jitwei98
/**
 * Exports all persons in the address book to a csv/vcf file.
 */
public class ExportAllCommand extends Command {

    public static final String COMMAND_WORD = "exportall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports all the persons in the address book"
            + "Parameters: FILETYPE (must be either \"csv\" or \"vcf\") "
            + "Example: " + COMMAND_WORD + " csv ";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "exportall command not implemented yet.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
