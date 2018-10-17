package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;

import static java.util.Objects.requireNonNull;

public class AdjustCommand extends Command {
    public static final String COMMAND_WORD = "c_adjust";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adjust target grade of an incomplete module "
            + "Parameters: CODE GRADE "
            + "Example: " + COMMAND_WORD + " CS2103 A+";
    public static final String MESSAGE_SUCCESS = "Module Adjusted: %1$s";

    private final Code code;
    private final Grade grade;

    public AdjustCommand(Code code, Grade grade) {
        requireNonNull(code);
        requireNonNull(grade);
        this.code = code;
        this.grade = grade;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Module updatedModule = model.adjustModule(code, grade);
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedModule));
    }
}
