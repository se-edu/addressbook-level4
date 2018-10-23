package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.Module;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by the module code.\n"
            + "Parameters: MODULE_CODE\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";

    private final Code targetCode;

    public DeleteModuleCommand(Code targetCode) {
        this.targetCode = targetCode;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        Module moduleToDelete = lastShownList.stream()
                .filter(module -> module.getCode().equals(targetCode))
                .findAny()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_MODULE));

        model.deleteModule(moduleToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && targetCode.equals(((DeleteModuleCommand) other).targetCode)); // state check
    }
}
