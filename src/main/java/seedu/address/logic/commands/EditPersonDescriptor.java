package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.UniqueTagList;

/**
 * Stores the details of person to edit.
 */
public class EditPersonDescriptor {
    private Optional<Name> name = Optional.empty();
    private Optional<Phone> phone = Optional.empty();
    private Optional<Email> email = Optional.empty();
    private Optional<Address> address = Optional.empty();
    private Optional<UniqueTagList> tags = Optional.empty();

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isFieldsEdited() {
        return CollectionUtil.isAnyNonEmpty(this.name, this.phone, this.email, this.address, this.tags);
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
