package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final EmployeeId employeeId;
    private final Name name;
    private final DateOfBirth dateOfBirth;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Department department;
    private final Position position;
    private final Address address;
    private final Salary salary;
    private final Bonus bonus;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(EmployeeId employeeId, Name name, DateOfBirth dateOfBirth, Phone phone, Email email,
                  Department department, Position position, Address address, Salary salary, Bonus bonus,
                  Set<Tag> tags) {
        requireAllNonNull(name, dateOfBirth, phone, email, department, position, address, salary, tags);
        this.employeeId = employeeId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.department = department;
        this.position = position;
        this.address = address;
        this.salary = salary;
        this.bonus = bonus;
        this.tags.addAll(tags);
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public Name getName() {
        return name;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Department getDepartment() {
        return department;
    }

    public Position getPosition() {
        return position;
    }

    public Address getAddress() {
        return address;
    }

    public Salary getSalary() {
        return salary;
    }

    public Bonus getBonus() {
        return bonus;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEmployeeId())
                .append(" Name: ")
                .append(getName())
                .append(" Date Of Birth: ")
                .append(getDateOfBirth())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Department: ")
                .append(getDepartment())
                .append(" Position: ")
                .append(getPosition())
                .append(" Address: ")
                .append(getAddress())
                .append(" Salary: ")
                .append(getSalary())
                .append(" Bonus ")
                .append(getBonus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
