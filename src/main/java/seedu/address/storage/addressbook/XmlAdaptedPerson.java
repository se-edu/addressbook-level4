package seedu.address.storage.addressbook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Salary;
import seedu.address.model.person.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String employeeId;
    @XmlElement(required = true)
    private String name;
    @XmlElement (required = true)
    private String dateOfBirth;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String department;
    @XmlElement(required = true)
    private String position;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String salary;
    @XmlElement
    private String bonus;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String employeeId, String name, String dateOfBirth, String phone, String email,
                            String department, String position, String address, String salary, String bonus,
                            List<XmlAdaptedTag> tagged) {
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
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        employeeId = source.getEmployeeId().value;
        name = source.getName().fullName;
        dateOfBirth = source.getDateOfBirth().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        department = source.getDepartment().value;
        position = source.getPosition().value;
        address = source.getAddress().value;
        salary = source.getSalary().value;
        bonus = source.getBonus().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (employeeId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeeId.class.getSimpleName()));
        }
        if (!EmployeeId.isValidEmployeeId(employeeId)) {
            throw new IllegalValueException(EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS);
        }
        final EmployeeId modelEmployeeId = new EmployeeId(employeeId);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (dateOfBirth == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateOfBirth.class.getSimpleName()));
        }
        if (!DateOfBirth.isValidDateOfBirth(dateOfBirth)) {
            throw new IllegalValueException(DateOfBirth.MESSAGE_DATEOFBIRTH_CONSTRAINTS);
        }
        final DateOfBirth modelDateOfBirth = new DateOfBirth(dateOfBirth);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (department == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Department.class.getSimpleName()));
        }
        if (!Department.isValidDepartment(department)) {
            throw new IllegalValueException(Department.MESSAGE_DEPARTMENT_CONSTRAINTS);
        }
        final Department modelDepartment = new Department(department);

        if (position == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Position.class.getSimpleName()));
        }
        if (!Position.isValidPosition(position)) {
            throw new IllegalValueException(Position.MESSAGE_POSITION_CONSTRAINTS);
        }
        final Position modelPosition = new Position(position);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (salary == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName()));
        }
        if (!Salary.isValidSalary(salary)) {
            throw new IllegalValueException(Salary.MESSAGE_SALARY_CONSTRAINTS);
        }
        final Salary modelSalary = new Salary(salary);

        final Bonus modelBonus = new Bonus("0.0");
        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelEmployeeId, modelName, modelDateOfBirth, modelPhone, modelEmail, modelDepartment,
                modelPosition, modelAddress, modelSalary, modelBonus, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(employeeId, otherPerson.employeeId)
                && Objects.equals(name, otherPerson.name)
                && Objects.equals(dateOfBirth, otherPerson.dateOfBirth)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(department, otherPerson.department)
                && Objects.equals(position, otherPerson.position)
                && Objects.equals(address, otherPerson.address)
                && Objects.equals(salary, otherPerson.salary)
                && Objects.equals(bonus, otherPerson.bonus)
                && tagged.equals(otherPerson.tagged);
    }
}
