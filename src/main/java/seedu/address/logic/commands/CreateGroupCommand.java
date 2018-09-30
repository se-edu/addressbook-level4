package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Lists all persons in the address book to the user.
 */
public class CreateGroupCommand extends Command {

    public static final String COMMAND_WORD = "create_group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new group. "
            + "Parameters: "
            + PREFIX_NAME + "GROUP NAME "
            + PREFIX_DESCRIPTION + "OPTIONAL DESCRIPTION";

    public static final String MESSAGE_SUCCESS = "Created group successfully.";

    private final Group newGroup;

    /**
     * Creates an CreateGroupCommand to create the specified {@code Group}
     */
    public CreateGroupCommand(Group group) {
        requireNonNull(group);
        newGroup = group;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.addGroup(newGroup);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, newGroup));
    }
}
