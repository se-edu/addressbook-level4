package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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

    // public static final String MESSAGE_NOT_IMPLEMENTED_YET = "exportall command not implemented yet.";

    public static final String MESSAGE_ARGUMENTS = "Filetype: %1$s";

    // TODO: use enum or other better ways to store
    private String filetype;

    /**
     * @param filetype of the export file
     */
    public ExportAllCommand(String filetype) {
        requireNonNull(filetype);

        this.filetype = filetype;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, filetype));
    }

    @Override
    public boolean equals(Object other) {
        // same object
        if (other == this) {
            return true;
        }

        // handles nulls
        if (!(other instanceof ExportAllCommand)) {
            return false;
        }

        // checks state
        ExportAllCommand e = (ExportAllCommand) other;
        return filetype.equals(e.filetype);
    }
}
