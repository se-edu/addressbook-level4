package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Accounts {

    // Identity fields
    private final String username;
    private final String password;


    /**
     * Every field must be present and not null.
     */
    public Accounts(String username, String password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

//    public Name getName() {
//        return name;
//    }
//
//    public Phone getPhone() {
//        return phone;
//    }
//
//    public Email getEmail() {
//        return email;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//
//    /**
//     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
//     * if modification is attempted.
//     */
//    public Set<Tag> getTags() {
//        return Collections.unmodifiableSet(tags);
//    }
//
//    /**
//     * Returns true if both persons of the same name have at least one other identity field that is the same.
//     * This defines a weaker notion of equality between two persons.
//     */
//    public boolean isSamePerson(Person otherPerson) {
//        if (otherPerson == this) {
//            return true;
//        }
//
//        return otherPerson != null
//                && otherPerson.getName().equals(getName())
//                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
//    }
//
//    /**
//     * Returns true if both persons have the same identity and data fields.
//     * This defines a stronger notion of equality between two persons.
//     */
//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//
//        if (!(other instanceof Person)) {
//            return false;
//        }
//
//        Person otherPerson = (Person) other;
//        return otherPerson.getName().equals(getName())
//                && otherPerson.getPhone().equals(getPhone())
//                && otherPerson.getEmail().equals(getEmail())
//                && otherPerson.getAddress().equals(getAddress())
//                && otherPerson.getTags().equals(getTags());
//    }
//
//    @Override
//    public int hashCode() {
//        // use this method for custom fields hashing instead of implementing your own
//        return Objects.hash(name, phone, email, address, tags);
//    }
//
//    @Override
//    public String toString() {
//        final StringBuilder builder = new StringBuilder();
//        builder.append(getName())
//                .append(" Phone: ")
//                .append(getPhone())
//                .append(" Email: ")
//                .append(getEmail())
//                .append(" Address: ")
//                .append(getAddress())
//                .append(" Tags: ");
//        getTags().forEach(builder::append);
//        return builder.toString();
//    }

}
