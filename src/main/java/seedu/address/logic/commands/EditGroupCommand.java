package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UniqueList;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing group in the address book.
 */
public class EditGroupCommand extends Command {

    public static final String COMMAND_WORD = "edit_group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the name of group. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: Current name"
            + "[" + PREFIX_NAME + "NEW NAME]"
            + "[" + PREFIX_DESCRIPTION + "PHONE]\n"
            + "Example: " + COMMAND_WORD + " MyGroup "
            + PREFIX_NAME + "Family "
            + PREFIX_DESCRIPTION + "A family group";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    private static final String MESSAGE_EDIT_GROUP_SUCCESS = "Edited Group: %1$s";
    private static final String MESSAGE_DUPLICATE_GROUP = "This person already exists in the address book.";

    private final Name oldName;
    private final EditGroupDescriptor editGroupDescriptor;

    /**
     * @param oldName of the group in the filtered group list to edit
     * @param editGroupDescriptor details to edit the person with
     */
    public EditGroupCommand(Name oldName, EditGroupDescriptor editGroupDescriptor) {
        requireNonNull(oldName);
        requireNonNull(editGroupDescriptor);

        this.oldName = oldName;
        this.editGroupDescriptor = new EditGroupDescriptor(editGroupDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownList = model.getFilteredGroupList();
        Group groupToBeEdited = new Group(oldName, ""); //do not know description and groupMembers

        if (!lastShownList.contains(groupToBeEdited)) {
            throw new CommandException(Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
        }

        groupToBeEdited = lastShownList.get(lastShownList.indexOf(groupToBeEdited)); //retrieves original group

        Group editedGroup = createEditedGroup(groupToBeEdited, editGroupDescriptor);


        if (!groupToBeEdited.isSame(editedGroup) && model.hasGroup(editedGroup)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.updateGroup(groupToBeEdited, editedGroup);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_GROUP_SUCCESS, editedGroup));
    }

    /**
     * Creates and returns a {@code Group} with the details of {@code groupToBeEdited}
     * edited with {@code editGroupDescriptor}.
     */
    private static Group createEditedGroup(Group groupToBeEdited, EditGroupDescriptor editGroupDescriptor) {
        assert groupToBeEdited != null;

        Name updatedName = editGroupDescriptor.getName().orElse(groupToBeEdited.getName());
        String updatedDescription = editGroupDescriptor.getDescription().orElse(groupToBeEdited.getDescription());
        UniqueList<Person> existingGroupMembers = new UniqueList<>();
        existingGroupMembers.setElements(groupToBeEdited.getGroupMembers());

        return new Group(updatedName, updatedDescription, existingGroupMembers);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditGroupCommand)) {
            return false;
        }

        // state check - to change TO DO
        EditGroupCommand e = (EditGroupCommand) other;
        return oldName.equals(e.oldName)
                && editGroupDescriptor.equals(e.editGroupDescriptor);
    }

    /**
     * Stores the details to edit the group with. Each non-empty field value will replace the
     * corresponding field value of the group.
     */
    public static class EditGroupDescriptor {
        private Name name;
        private String description;

        public EditGroupDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditGroupDescriptor(EditGroupDescriptor toCopy) {
            setName(toCopy.name);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, description);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditGroupDescriptor)) {
                return false;
            }

            // state check
            EditGroupDescriptor e = (EditGroupDescriptor) other;

            return getName().equals(e.getName())
                    && getDescription().equals(e.getDescription());
        }
    }
}
