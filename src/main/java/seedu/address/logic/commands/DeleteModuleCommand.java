package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

//@@author alexkmj
/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by the module code.\n"
            + "Parameters: MODULE_CODE [YEAR] [SEMESTER]\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module";
    public static final String MESSAGE_MODULE_NOT_EXIST =
            "This module does not exist in the transcript";

    private final Code targetCode;
    private final Year targetYear;
    private final Semester targetSemester;

    private DeleteModuleCommand() {
        targetCode = null;
        targetYear = null;
        targetSemester = null;
    }

    public DeleteModuleCommand(Code targetCode) {
        this(targetCode, null, null);
    }

    public DeleteModuleCommand(Code targetCode, Year targetYear, Semester targetSemester) {
        this.targetCode = targetCode;
        this.targetYear = targetYear;
        this.targetSemester = targetSemester;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasMultipleInstances(targetCode) && targetYear == null) {
            throw new CommandException(Messages.MESSAGE_MULTIPLE_INSTANCES_FOUND);
        }

        Predicate<Module> predicate = target -> {
            return target.getCode().equals(targetCode)
                    && targetYear == null || target.getYear().equals(targetYear)
                    && targetSemester == null || target.getSemester().equals(targetSemester);
        };

        try {
            model.deleteModule(predicate);
        } catch (ModuleNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE);
        }

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && targetCode.equals(((DeleteModuleCommand) other).targetCode)); // state check
    }
}
