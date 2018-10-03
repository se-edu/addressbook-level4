package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_EMPLOYEEID = "000001";
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_DATEOFBIRTH = "12/12/1995";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_DEPARTMENT = "Human Resource";
    public static final String DEFAULT_POSITION = "Staff";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SALARY = "1000.00";

    private EmployeeId employeeId;
    private Name name;
    private DateOfBirth dateOfBirth;
    private Phone phone;
    private Email email;
    private Department department;
    private Position position;
    private Address address;
    private Salary salary;
    private Bonus bonus;
    private Set<Tag> tags;

    public PersonBuilder() {
        employeeId = new EmployeeId(DEFAULT_EMPLOYEEID);
        name = new Name(DEFAULT_NAME);
        dateOfBirth = new DateOfBirth(DEFAULT_DATEOFBIRTH);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        department = new Department(DEFAULT_DEPARTMENT);
        position = new Position(DEFAULT_POSITION);
        address = new Address(DEFAULT_ADDRESS);
        salary = new Salary(DEFAULT_SALARY);
        bonus = new Bonus("0.0");
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        employeeId = personToCopy.getEmployeeId();
        name = personToCopy.getName();
        dateOfBirth = personToCopy.getDateOfBirth();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        department = personToCopy.getDepartment();
        position = personToCopy.getPosition();
        address = personToCopy.getAddress();
        salary = personToCopy.getSalary();
        bonus = new Bonus("0.0");
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code EmployeeId} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmployeeId(String employeeId) {
        this.employeeId = new EmployeeId(employeeId);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code DateOfBirth} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = new DateOfBirth(dateOfBirth);
        return this;
    }

    /**
     * Sets the {@code Department} of the {@code Person} that we are building.
     */
    public PersonBuilder withDepartment(String department) {
        this.department = new Department(department);
        return this;
    }

    /**
     * Sets the {@code Position} of the {@code Person} that we are building.
     */
    public PersonBuilder withPosition(String position) {
        this.position = new Position(position);
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code Person} that we are building.
     */
    public PersonBuilder withSalary(String salary) {
        this.salary = new Salary(salary);
        return this;
    }

    /**
     * Sets the {@code Bonus} of the {@code Person} that we are building.
     */
    public PersonBuilder withBonus(String bonus) {
        this.bonus = new Bonus(bonus);
        return this;
    }

    /**
     * Builds (@code Person) with required employee's variables
     */
    public Person build() {
        return new Person(employeeId, name, dateOfBirth, phone, email, department, position, address, salary,
                bonus, tags);
    }

}
