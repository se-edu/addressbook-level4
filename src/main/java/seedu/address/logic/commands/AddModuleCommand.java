package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

/**
 * Adds a module to the transcript.
 */
public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to the transcript. "
            + "Parameters: CODE YEAR SEMESTER CREDIT [GRADE]"
            + "Example: " + COMMAND_WORD + " CS2103 2 1 4 A+";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE =
            "This module already exists in the transcript";

    private final Module toAdd;

    /**
     * Creates an AddModuleCommand to add the specified {@code Module}
     */
    public AddModuleCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(toAdd);
        model.commitTranscript();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
