package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATEOFBIRTH_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATEOFBIRTH_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withEmployeeId("000001").withName("Alice Pauline")
            .withDateOfBirth("12/12/1995").withPhone("94351253").withEmail("alice@example.com")
            .withDepartment("Human Resource").withPosition("Staff").withAddress("123, Jurong West Ave 6, #08-111")
            .withSalary("1000.00").withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withEmployeeId("000001").withName("Benson Meier")
            .withDateOfBirth("12/12/1995").withPhone("98765432").withEmail("johnd@example.com")
            .withDepartment("Human Resource").withPosition("Staff").withAddress("311, Clementi Ave 2, #02-25")
            .withSalary("1000.00").withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withEmployeeId("000003").withName("Carl Kurz")
            .withDateOfBirth("12/12/1995").withPhone("95352563").withEmail("heinz@example.com")
            .withDepartment("Human Resource").withPosition("Staff").withAddress("wall street")
            .withSalary("1000.00").build();
    public static final Person DANIEL = new PersonBuilder().withEmployeeId("000004").withName("Daniel Meier")
            .withDateOfBirth("12/12/1995").withPhone("87652533").withEmail("cornelia@example.com")
            .withDepartment("Human Resource").withPosition("Staff").withAddress("10th street").withTags("friends")
            .withSalary("1000.00").build();
    public static final Person ELLE = new PersonBuilder().withEmployeeId("000005").withName("Elle Meyer")
            .withDateOfBirth("12/12/1995").withPhone("9482224").withEmail("werner@example.com")
            .withDepartment("Human Resource").withPosition("Staff").withAddress("michegan ave")
            .withSalary("1000.00").build();
    public static final Person FIONA = new PersonBuilder().withEmployeeId("000006").withName("Fiona Kunz")
            .withDateOfBirth("12/12/1995").withPhone("9482427").withEmail("lydia@example.com")
            .withDepartment("Human Resource").withPosition("Staff").withAddress("little tokyo")
            .withSalary("1000.00").build();
    public static final Person GEORGE = new PersonBuilder().withEmployeeId("000007").withName("George Best")
            .withDateOfBirth("12/12/1995").withPhone("9482442").withEmail("anna@example.com")
            .withDepartment("Human Resource").withPosition("Staff").withAddress("4th street")
            .withSalary("1000.00").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withEmployeeId("000008").withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withEmployeeId("000009").withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withEmployeeId(VALID_EMPLOYEEID_AMY).withName(VALID_NAME_AMY)
            .withDateOfBirth(VALID_DATEOFBIRTH_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withDepartment(VALID_DEPARTMENT_AMY).withPosition(VALID_POSITION_AMY).withAddress(VALID_ADDRESS_AMY)
            .withSalary(VALID_SALARY_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withEmployeeId(VALID_EMPLOYEEID_BOB).withName(VALID_NAME_BOB)
            .withDateOfBirth(VALID_DATEOFBIRTH_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withDepartment(VALID_DEPARTMENT_BOB).withPosition(VALID_POSITION_BOB).withAddress(VALID_ADDRESS_BOB)
            .withSalary(VALID_SALARY_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
