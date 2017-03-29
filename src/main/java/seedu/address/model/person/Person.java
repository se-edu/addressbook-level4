package seedu.address.model.person;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private final ObjectProperty<String> nameProperty;
    private final ObjectProperty<String> addressProperty;
    private final ObjectProperty<String> emailProperty;
    private final ObjectProperty<String> phoneProperty;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

        nameProperty = new SimpleObjectProperty<>(name.fullName);
        phoneProperty = new SimpleObjectProperty<>(phone.value);
        emailProperty = new SimpleObjectProperty<>(email.value);
        addressProperty = new SimpleObjectProperty<>(address.value);

        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;

        this.name = name;
        nameProperty.set(this.name.fullName);
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public ReadOnlyProperty<String> nameProperty() {
        return nameProperty;
    }

    public void setPhone(Phone phone) {
        assert phone != null;

        this.phone = phone;
        phoneProperty.set(this.phone.value);
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public ReadOnlyProperty<String> phoneProperty() {
        return phoneProperty;
    }

    public void setEmail(Email email) {
        assert email != null;

        this.email = email;
        emailProperty.set(this.email.value);
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public ReadOnlyProperty<String> emailProperty() {
        return emailProperty;
    }

    public void setAddress(Address address) {
        assert address != null;

        this.address = address;
        addressProperty.set(this.address.value);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public ReadOnlyProperty<String> addressProperty() {
        return addressProperty;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    @Override
    public UnmodifiableObservableList<Tag> tagProperty() {
        return tags.asObservableList();
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyPerson replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPhone(replacement.getPhone());
        this.setEmail(replacement.getEmail());
        this.setAddress(replacement.getAddress());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(), getTags());
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
