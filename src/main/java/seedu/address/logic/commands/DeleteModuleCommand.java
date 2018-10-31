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
            + "Parameters: MODULE_CODE [YEAR SEMESTER]\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module";

    private final Code targetCode;
    private final Year targetYear;
    private final Semester targetSemester;

    /**
     * Prevents instantiation of empty constructor.
     */
    private DeleteModuleCommand() {
        targetCode = null;
        targetYear = null;
        targetSemester = null;
    }

    /**
     * Sets the targeted code set to {@code targetCode} in the argument.
     * @param targetCode the code used to identify the module to be deleted
     */
    public DeleteModuleCommand(Code targetCode) {
        this(targetCode, null, null);
    }

    /**
     * Sets the targeted code, targeted year, and targeted semester to {@code targetCode},
     * {@code targetYear}, {@code targetSemester} in the argument.
     * @param targetCode the code used to identify the module to be deleted
     * @param targetYear the year used to identify the module to be deleted
     * @param targetSemester the semester used to identify the module to be deleted
     */
    public DeleteModuleCommand(Code targetCode, Year targetYear, Semester targetSemester) {
        this.targetCode = targetCode;
        this.targetYear = targetYear;
        this.targetSemester = targetSemester;
    }

    /**
     * Attempts to delete a module entry from the transcript. Throws {@code CommandException} if
     * year and semester wasn't provided and multiple entries of module with the same module code
     * exist or when no entry of such module exist.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return Message which says that the execution was successful
     * @throws CommandException exception thrown when command cannot be executed properly
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasMultipleInstances(targetCode)
                && (targetYear == null || targetSemester == null)) {
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
