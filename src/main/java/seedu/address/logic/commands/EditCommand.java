package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name name = editPersonDescriptor.getName();
        Name updatedName = (name == null) ? personToEdit.getName() : name;
        Phone phone = editPersonDescriptor.getPhone();
        Phone updatedPhone = (phone == null) ? personToEdit.getPhone() : phone;
        Email email = editPersonDescriptor.getEmail();
        Email updatedEmail = (email == null) ? personToEdit.getEmail() : email;
        Address address = editPersonDescriptor.getAddress();
        Address updatedAddress = (address == null) ? personToEdit.getAddress() : address;
        Set<Tag> tags = editPersonDescriptor.getTags();
        Set<Tag> updatedTags = (tags == null) ? personToEdit.getTags() : tags;

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.getName();
            this.phone = toCopy.getPhone();
            this.email = toCopy.getEmail();
            this.address = toCopy.getAddress();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address, this.tags);
        }

        public void setName(Name name) {
            this.name = requireNonNull(name);
        }

        public Name getName() {
            return name;
        }

        public void setPhone(Phone phone) {
            this.phone = requireNonNull(phone);
        }

        public Phone getPhone() {
            return phone;
        }

        public void setEmail(Email email) {
            this.email = requireNonNull(email);
        }

        public Email getEmail() {
            return email;
        }

        public void setAddress(Address address) {
            this.address = requireNonNull(address);
        }

        public Address getAddress() {
            return address;
        }

        public void setTags(Set<Tag> tags) {
            this.tags = requireNonNull(tags);
        }

        public Set<Tag> getTags() {
            return tags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            boolean isNameEquals = (name == null) ? (e.getName() == null) : name.equals(e.getName());
            boolean isPhoneEquals = (phone == null) ? (e.getPhone() == null) : phone.equals(e.getPhone());
            boolean isEmailEquals = (email == null) ? (e.getEmail() == null) : email.equals(e.getEmail());
            boolean isAddressEquals = (address == null) ? (e.getAddress() == null) : address.equals(e.getAddress());
            boolean isTagEquals = (tags == null) ? (e.getTags() == null) : tags.equals(e.getTags());
            return isNameEquals && isPhoneEquals && isEmailEquals && isAddressEquals && isTagEquals;
        }
    }
}
