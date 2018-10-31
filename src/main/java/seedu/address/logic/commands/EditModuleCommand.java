package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credit;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.util.ModuleBuilder;

/**
 * Adds a person to the address book.
 */
public class EditModuleCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the module specified by the module code. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "-code MODULE_CODE "
            + "-year [YEAR] "
            + "-semester [SEMESTER] "
            + "-credit [CREDIT] "
            + "-grade [GRADE] ";

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited module: %1$s";
    public static final String MESSAGE_NO_SUCH_MODULE = "No such module exist.";
    public static final String MESSAGE_MODULE_EXIST = "Edited module already exist.";

    private final Code targetCode;
    private final Year targetYear;
    private final Semester targetSemester;
    private final Code newCode;
    private final Year newYear;
    private final Semester newSemester;
    private final Credit newCredit;
    private final Grade newGrade;

    /**
     * Prevents the use of empty constructor.
     */
    private EditModuleCommand() {
        this.targetCode = null;
        this.targetYear = null;
        this.targetSemester = null;
        this.newCode = null;
        this.newYear = null;
        this.newSemester = null;
        this.newCredit = null;
        this.newGrade = null;
    }

    public EditModuleCommand(Code targetCode, Year targetYear, Semester targetSemester,
            Code newCode, Year newYear, Semester newSemester, Credit newCredit, Grade newGrade) {
        requireNonNull(targetCode);

        this.targetCode = targetCode;
        this.targetYear = targetYear;
        this.targetSemester = targetSemester;
        this.newCode = newCode;
        this.newYear = newYear;
        this.newSemester = newSemester;
        this.newCredit = newCredit;
        this.newGrade = newGrade;
    }

    /**
     * Edits the current module in the transcripts.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        int numOfModule = (int) model.getFilteredModuleList()
                .stream()
                .filter(index -> index.getCode().equals(targetCode))
                .count();

        if (numOfModule == 0) {
            throw new CommandException(MESSAGE_NO_SUCH_MODULE);
        }

        if (numOfModule > 1 && (targetYear == null || targetSemester == null)) {
            throw new CommandException("");
        }

        return numOfModule == 1
                ? executeUniqueModuleCode(model, history)
                : executeNonUniqueModuleCode(model, history);
    }

    /**
     * Edits the current module in the transcripts.
     */
    public CommandResult executeUniqueModuleCode(Model model, CommandHistory history)
            throws CommandException {
        Module currentModule = model.getFilteredModuleList()
                .stream()
                .filter(index -> index.getCode().equals(targetCode))
                .findAny()
                .get();

        Module editedModule = new ModuleBuilder(currentModule)
                .withCode(newCode == null ? currentModule.getCode() : newCode)
                .withYear(newYear == null ? currentModule.getYear() : newYear)
                .withSemester(newSemester == null ? currentModule.getSemester() : newSemester)
                .withCredit(newCredit == null ? currentModule.getCredits() : newCredit)
                .withGrade(newGrade == null ? currentModule.getGrade() : newGrade)
                .build();

        if ((newCode != null || newYear != null || newSemester != null)
                && model.hasModule(editedModule)) {
            throw new CommandException(MESSAGE_MODULE_EXIST);
        }

        model.deleteModule(currentModule);
        model.addModule(editedModule);
        model.commitTranscript();

        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedModule));
    }

    /**
     * Edits the current module in the transcripts.
     */
    public CommandResult executeNonUniqueModuleCode(Model model, CommandHistory history)
            throws CommandException {
        Module currentModule = model.getFilteredModuleList()
                .stream()
                .filter(index -> {
                    return index.getCode().equals(targetCode)
                            && index.getYear().equals(targetYear)
                            && index.getSemester().equals(targetSemester);
                })
                .findAny()
                .get();

        Module editedModule = new ModuleBuilder(currentModule)
                .withCode(newCode == null ? currentModule.getCode() : newCode)
                .withYear(newYear == null ? currentModule.getYear() : newYear)
                .withSemester(newSemester == null ? currentModule.getSemester() : newSemester)
                .withCredit(newCredit == null ? currentModule.getCredits() : newCredit)
                .withGrade(newGrade == null ? currentModule.getGrade() : newGrade)
                .build();

        if (model.hasModule(editedModule)) {
            throw new CommandException(MESSAGE_MODULE_EXIST);
        }

        model.deleteModule(currentModule);
        model.addModule(editedModule);
        model.commitTranscript();

        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedModule));
    }
}
