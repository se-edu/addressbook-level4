package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.export.ExportType;

/**
 * Export different types of data from the application to the user
 */
public class ExportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the specified type of content.\n"
            + "Parameters: ExportType (must be one of the following - CALENDAR, PORTFOLIO)\n"
            + "Example: " + COMMAND_WORD + " portfolio";

    public static final String MESSAGE_SUCCESS = "Successfully exported: %1$s";

    private ExportType typeToExport;

    public ExportCommand(ExportType typeToExport) {
        this.typeToExport = typeToExport;
    }

    @Override
    protected CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.export(typeToExport);
        return new CommandResult(String.format(MESSAGE_SUCCESS, typeToExport.toString()));
    }
}
