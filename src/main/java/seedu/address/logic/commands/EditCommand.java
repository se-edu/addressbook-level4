package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.IndexUtil;
import seedu.address.commons.util.StringUtil;
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
    public static final String MESSAGE_MULTIPLE_VALUES_WARNING = "Warning: Multiple %s values entered. "
            + "Only the last instance of %s has been stored.\n";

    private final int filteredPersonListIndex;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param filteredPersonListIndex the index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(int filteredPersonListIndex, EditPersonDescriptor editPersonDescriptor) {
        assert filteredPersonListIndex > 0;
        assert editPersonDescriptor != null;

        this.filteredPersonListIndex = IndexUtil.oneToZeroIndex(filteredPersonListIndex);
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (filteredPersonListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(filteredPersonListIndex);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        try {
            model.updatePerson(filteredPersonListIndex, editedPerson);
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();

        return new CommandResult(formatResultMessage(personToEdit));
    }

    /**
     * Returns the result message from successfully executing EditCommand. Also includes warning messages
     * to the user if the user entered multiple values for a single field.
     */
    private String formatResultMessage(ReadOnlyPerson personToEdit) {
        String message = String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit);
        if (!editPersonDescriptor.hasMultipleValues()) {
            return message;
        }

        ArrayList<String> fieldsWithMultipleValues = editPersonDescriptor.getFieldsWithMultipleValues();
        String toFormat = StringUtil.joinStrings(fieldsWithMultipleValues);

        return String.format(MESSAGE_MULTIPLE_VALUES_WARNING, toFormat, toFormat) + message;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}. If there are multiple values in any of the fields
     * {@code Phone, Email} or {@code Address}, the last value for that field will be used to
     * edit {@code personToEdit}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElseGet(personToEdit::getName);
        Phone updatedPhone = editPersonDescriptor.getLastPhone().orElseGet(personToEdit::getPhone);
        Email updatedEmail = editPersonDescriptor.getLastEmail().orElseGet(personToEdit::getEmail);
        Address updatedAddress = editPersonDescriptor.getLastAddress().orElseGet(personToEdit::getAddress);
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElseGet(personToEdit::getTags);

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
        return filteredPersonListIndex == e.filteredPersonListIndex
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Optional<Name> name = Optional.empty();
        private List<Phone> phones = new ArrayList<>();
        private List<Email> emails = new ArrayList<>();
        private List<Address> addresses = new ArrayList<>();
        private Optional<Set<Tag>> tags = Optional.empty();

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.getName();
            this.phones = toCopy.getPhone();
            this.emails = toCopy.getEmail();
            this.addresses = toCopy.getAddress();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.tags) || !phones.isEmpty() || !emails.isEmpty()
                    || !addresses.isEmpty();
        }

        /**
         * Returns true if {@code phone, email} or {@code address} has multiple values.
         */
        public boolean hasMultipleValues() {
            return hasMultiplePhones() || hasMultipleEmails() || hasMultipleAddresses();
        }

        public boolean hasMultiplePhones() {
            return phones.size() > 1;
        }

        public boolean hasMultipleEmails() {
            return emails.size() > 1;
        }

        public boolean hasMultipleAddresses() {
            return addresses.size() > 1;
        }

        /**
         * Returns an {@code ArrayList<String>} containing the names of classes
         * with multiple values. The possible classes are: {@code Phone, Email}
         * and {@code Address}.
         */
        public ArrayList<String> getFieldsWithMultipleValues() {
            ArrayList<String> fieldsWithMultipleValues = new ArrayList<>();

            if (hasMultiplePhones()) {
                fieldsWithMultipleValues.add(Phone.class.getSimpleName());
            }

            if (hasMultipleEmails()) {
                fieldsWithMultipleValues.add(Email.class.getSimpleName());
            }

            if (hasMultipleAddresses()) {
                fieldsWithMultipleValues.add(Address.class.getSimpleName());
            }

            return fieldsWithMultipleValues;
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setPhone(List<Phone> phone) {
            assert phone != null;
            this.phones = phone;
        }

        public List<Phone> getPhone() {
            return phones;
        }

        public Optional<Phone> getLastPhone() {
            return !phones.isEmpty() ? Optional.of(phones.get(phones.size() - 1)) : Optional.empty();
        }

        public void setEmail(List<Email> email) {
            assert email != null;
            this.emails = email;
        }

        public List<Email> getEmail() {
            return emails;
        }

        public Optional<Email> getLastEmail() {
            return !emails.isEmpty() ? Optional.of(emails.get(emails.size() - 1)) : Optional.empty();
        }

        public void setAddress(List<Address> address) {
            assert address != null;
            this.addresses = address;
        }

        public List<Address> getAddress() {
            return addresses;
        }

        public Optional<Address> getLastAddress() {
            return !addresses.isEmpty() ? Optional.of(addresses.get(addresses.size() - 1)) : Optional.empty();
        }

        public void setTags(Optional<Set<Tag>> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
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
            return name.equals(e.getName())
                    && phone.equals(e.getPhone())
                    && email.equals(e.getEmail())
                    && address.equals(e.getAddress())
                    && tags.equals(e.getTags());
        }
    }
}
