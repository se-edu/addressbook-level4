package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.util.ModuleBuilder;

/**
 * Adjusts target grade of a Module
 */
public class AdjustCommand extends Command {
    public static final String COMMAND_WORD = "adjust";
    public static final String MESSAGE_COMMAND_CODE_ONLY = COMMAND_WORD + " CODE";
    public static final String MESSAGE_COMMAND_CODE_YEAR_SEM = COMMAND_WORD + " CODE YEAR SEM";
    public static final String MESSAGE_USAGE = MESSAGE_COMMAND_CODE_ONLY
            + "\n"
            + MESSAGE_COMMAND_CODE_YEAR_SEM
            + "\n"
            + "Adjust target grade of an incomplete module "
            + "Parameters: CODE [YEAR SEM] GRADE "
            + "Example: " + COMMAND_WORD + " CS2103 1 1 A+";
    public static final String MESSAGE_MULTIPLE_INSTANCE =
            "Multiple Instance of Module, please include Year and Semester\n"
            + MESSAGE_COMMAND_CODE_YEAR_SEM;
    public static final String MESSAGE_SUCCESS = "Module Adjusted: %1$s";
    public static final String MESSAGE_MODULE_NOT_FOUND = "Module not found";

    private final Code code;
    private final Year year;
    private final Semester sem;
    private final Grade grade;

    public AdjustCommand(Code code, Year year, Semester sem, Grade grade) {
        requireNonNull(code);
        requireNonNull(grade);
        this.code = code;
        this.year = year;
        this.sem = sem;
        this.grade = grade;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        boolean hasMultipleInstance = model.hasMultipleInstances(code);
        Module targetModule;
        if (hasMultipleInstance && year == null && sem == null) {
            throw new CommandException(MESSAGE_MULTIPLE_INSTANCE);
        } else {
            try {
                if (hasMultipleInstance) {
                    Module moduleToFind = new ModuleBuilder()
                            .withSemester(sem.value)
                            .withCode(code.value)
                            .withYear(year.value)
                            .build();
                    targetModule = model.findModule(moduleToFind);
                } else {
                    targetModule = model.findModule(code);
                }
            } catch (ModuleNotFoundException mnfe) {
                throw new CommandException(MESSAGE_MODULE_NOT_FOUND);
            }
        }
        Module adjustedModule = model.adjustModule(targetModule, grade);
        return new CommandResult(String.format(MESSAGE_SUCCESS, adjustedModule));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AdjustCommand // instanceof handles nulls
                && grade.equals(((AdjustCommand) other).grade)
                && code.equals(((AdjustCommand) other).code)
                && (year == null || year.equals(((AdjustCommand) other).year))
                && (sem == null || sem.equals(((AdjustCommand) other).sem)));
    }
}
