package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Lists all persons in the address book to the user.
 */
public class DeleteGroupCommand extends Command {

    public static final String COMMAND_WORD = "delete_group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an existing group. "
            + "Parameters: "
            + PREFIX_NAME + "GROUP NAME ";

    public static final String MESSAGE_SUCCESS = "Deleted group successfully: %1$s";

    private final Group groupToBeDeleted;

    /**
     * Creates an CreateGroupCommand to create the specified {@code Group}
     */
    public DeleteGroupCommand(Group group) {
        requireNonNull(group);
        groupToBeDeleted = group;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.deleteGroup(groupToBeDeleted);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, groupToBeDeleted));
        } catch (PersonNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && groupToBeDeleted.equals(((DeleteGroupCommand) other).groupToBeDeleted));
    }
}
