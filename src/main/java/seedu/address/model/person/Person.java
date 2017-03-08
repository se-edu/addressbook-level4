package seedu.address.model.person;

import java.util.Map;
import java.util.Objects;

import com.google.common.collect.Maps;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private final Map<PersonProperty, ObjectProperty> propertyMap;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, address, tags);

        propertyMap = Maps.newEnumMap(PersonProperty.class);

        propertyMap.put(PersonProperty.NAME, new SimpleObjectProperty<>(name));
        propertyMap.put(PersonProperty.PHONE, new SimpleObjectProperty<>(phone));
        propertyMap.put(PersonProperty.EMAIL, new SimpleObjectProperty<>(email));
        propertyMap.put(PersonProperty.ADDRESS, new SimpleObjectProperty<>(address));

        UniqueTagList tagList = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        propertyMap.put(PersonProperty.TAG, new SimpleObjectProperty<>(tagList));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        propertyMap.get(PersonProperty.NAME).set(name);
    }

    @Override
    public Name getName() {
        return (Name) propertyMap.get(PersonProperty.NAME).get();
    }

    public void setPhone(Phone phone) {
        assert phone != null;
        propertyMap.get(PersonProperty.PHONE).set(phone);
    }

    @Override
    public Phone getPhone() {
        return (Phone) propertyMap.get(PersonProperty.PHONE).get();
    }

    public void setEmail(Email email) {
        assert email != null;
        propertyMap.get(PersonProperty.EMAIL).set(email);
    }

    @Override
    public Email getEmail() {
        return (Email) propertyMap.get(PersonProperty.EMAIL).get();
    }

    public void setAddress(Address address) {
        assert address != null;
        propertyMap.get(PersonProperty.ADDRESS).set(address);
    }

    @Override
    public Address getAddress() {
        return (Address) propertyMap.get(PersonProperty.ADDRESS).get();
    }

    @Override
    public UniqueTagList getTags() {
        UniqueTagList underlyingList = (UniqueTagList) propertyMap.get(PersonProperty.TAG).get();
        return new UniqueTagList(underlyingList);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        propertyMap.get(PersonProperty.TAG).set(new UniqueTagList(replacement));
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
    public void registerListener(PersonProperty property, ChangeListener<Object> listener) {
        propertyMap.get(property).addListener(listener);
    }

    @Override
    public void removeListener(PersonProperty property, ChangeListener<Object> listener) {
        propertyMap.get(property).removeListener(listener);
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
