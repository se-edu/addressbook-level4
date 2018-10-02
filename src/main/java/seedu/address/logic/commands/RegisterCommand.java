package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UniqueList;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds an existing person to an existing group in the address book.
 */
public class RegisterCommand extends Command {

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an existing person to a group "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_NAME + " GROUP NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Family ";

    public static final String MISSING_GROUP_NAME = "Please enter group name.";
    private static final String MESSAGE_EDIT_GROUP_SUCCESS = "Added member to group: %1$s";
    private static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the group.";


    private final Index index;
    private final Name groupName;

    /**
     * @param groupName of the group in the filtered group list to edit
     * @param index of the person to be added to the group.
     */
    public RegisterCommand(Name groupName, Index index) {
        requireNonNull(groupName);
        requireNonNull(index);
        this.groupName = groupName;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // Make sure that group exists
        List<Group> lastShownList = model.getFilteredGroupList();
        Group groupToBeEdited = new Group(groupName, ""); //do not know description and groupMembers

        if (!lastShownList.contains(groupToBeEdited)) {
            throw new CommandException(Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
        }

        groupToBeEdited = lastShownList.get(lastShownList.indexOf(groupToBeEdited)); //retrieves original group

        // Retrieves person from index
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAdd = lastShownPersonList.get(index.getZeroBased());

        try {
            Group editedGroup = addMemberToGroup(groupToBeEdited, personToAdd);

            model.updateGroup(groupToBeEdited, editedGroup);
            model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_EDIT_GROUP_SUCCESS, editedGroup));

        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    /**
     * Creates and returns a {@code group} with a new member {@code personToAdd}
     * in {@code groupToBeEdited}
     */
    private static Group addMemberToGroup(Group groupToBeEdited, Person personToAdd) throws DuplicatePersonException {
        assert groupToBeEdited != null;

        UniqueList<Person> newGroupMembers = new UniqueList<>();
        newGroupMembers.setElements(groupToBeEdited.getGroupMembers());
        newGroupMembers.add(personToAdd);
        return new Group(groupToBeEdited.getName(), groupToBeEdited.getDescription(), newGroupMembers);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RegisterCommand)) {
            return false;
        }

        // state check
        RegisterCommand e = (RegisterCommand) other;
        return groupName.equals(e.groupName)
                && index.equals(e.index);
    }
}
