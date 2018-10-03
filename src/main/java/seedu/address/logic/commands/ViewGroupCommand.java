package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;

/**
 * Lists all members of the group entered to the user.
 */
public class ViewGroupCommand extends Command {

    public static final String COMMAND_WORD = "view_group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View group members of a group"
            + "[" + PREFIX_NAME + " GROUP NAME]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_NAME + "Family ";

    public static final String MESSAGE_SUCCESS = "Listed all members:\n";

    private final Name groupName;

    /**
     * @param groupName of the group to list its members
     */
    public ViewGroupCommand(Name groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // Make sure that group exists
        List<Group> lastShownList = model.getFilteredGroupList();
        Group group = new Group(groupName, ""); //do not know description and groupMembers

        if (!lastShownList.contains(group)) {
            throw new CommandException(Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
        }

        group = lastShownList.get(lastShownList.indexOf(group)); //retrieves original group

        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(MESSAGE_SUCCESS + group.printMembers());
    }
}
