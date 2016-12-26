package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.UniqueTagList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/91234567 e/johndoe@yahoo.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one of the fields should be edited";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final int targetIndex;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param targetIndex the index of the person to edit based on current list
     * @param editPersonDescriptor details of person to be edited
     */
    public EditCommand(int targetIndex, EditPersonDescriptor editPersonDescriptor) {
        assert targetIndex > 0;
        assert editPersonDescriptor != null;

        // converts targetIndex from one-based to zero-based.
        this.targetIndex = targetIndex - 1;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex >= lastShownList.size()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(targetIndex);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (personToEdit.equals(editedPerson) && personToEdit.getTags().equals(editedPerson.getTags())) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

        try {
            model.updatePerson(targetIndex, editedPerson);
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the updated details.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                            EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElseGet(personToEdit::getName);
        Phone updatedPhone = editPersonDescriptor.getPhone().orElseGet(personToEdit::getPhone);
        Email updatedEmail = editPersonDescriptor.getEmail().orElseGet(personToEdit::getEmail);
        Address updatedAddress = editPersonDescriptor.getAddress().orElseGet(personToEdit::getAddress);
        UniqueTagList updatedTags = editPersonDescriptor.getTags().orElseGet(personToEdit::getTags);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    /**
     * Stores the details of person to edit.
     */
    public static class EditPersonDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Phone> phone = Optional.empty();
        private Optional<Email> email = Optional.empty();
        private Optional<Address> address = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

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
            return CollectionUtil.isAnyPresent(this.name, this.phone, this.email, this.address, this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setPhone(Optional<Phone> phone) {
            assert phone != null;
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return phone;
        }

        public void setEmail(Optional<Email> email) {
            assert email != null;
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return email;
        }

        public void setAddress(Optional<Address> address) {
            assert address != null;
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return address;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
