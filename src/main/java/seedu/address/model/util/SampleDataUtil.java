package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.addressbook.ReadOnlyAddressBook;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final DateOfBirth SAMPLE_DATEOFBIRTH = new DateOfBirth("01/01/2000");
    public static final Department SAMPLE_DEPARTMENT = new Department("Human Resource");
    public static final Position SAMPLE_POSITION = new Position("Intern");
    public static final Salary SAMPLE_SALARY = new Salary("1000.00");
    public static final Bonus EMPTY_BONUS = new Bonus("00.00");
    public static Person[] getSamplePersons() {


        return new Person[] {
            new Person(new EmployeeId("000001"), new Name("Alex Yeoh"), SAMPLE_DATEOFBIRTH, new Phone("87438807"),
                new Email("alexyeoh@example.com"), SAMPLE_DEPARTMENT, SAMPLE_POSITION,
                new Address("Blk 30 Geylang Street 29, #06-40"), SAMPLE_SALARY, EMPTY_BONUS,
                getTagSet("friends")),
            new Person(new EmployeeId("000002"), new Name("Bernice Yu"), SAMPLE_DATEOFBIRTH, new Phone("99272758"),
                new Email("berniceyu@example.com"), SAMPLE_DEPARTMENT, SAMPLE_POSITION,
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), SAMPLE_SALARY, EMPTY_BONUS,
                getTagSet("colleagues", "friends")),
            new Person(new EmployeeId("000003"), new Name("Charlotte Oliveiro"), SAMPLE_DATEOFBIRTH,
                new Phone("93210283"), new Email("charlotte@example.com"), SAMPLE_DEPARTMENT, SAMPLE_POSITION,
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), SAMPLE_SALARY, EMPTY_BONUS,
                getTagSet("neighbours")),
            new Person(new EmployeeId("000004"), new Name("David Li"), SAMPLE_DATEOFBIRTH, new Phone("91031282"),
                new Email("lidavid@example.com"), SAMPLE_DEPARTMENT, SAMPLE_POSITION,
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), SAMPLE_SALARY, EMPTY_BONUS,
                getTagSet("family")),
            new Person(new EmployeeId("000005"), new Name("Irfan Ibrahim"), SAMPLE_DATEOFBIRTH, new Phone("92492021"),
                new Email("irfan@example.com"), SAMPLE_DEPARTMENT, SAMPLE_POSITION,
                new Address("Blk 47 Tampines Street 20, #17-35"), SAMPLE_SALARY, EMPTY_BONUS,
                getTagSet("classmates")),
            new Person(new EmployeeId("000006"), new Name("Roy Balakrishnan"), SAMPLE_DATEOFBIRTH,
                new Phone("92624417"), new Email("royb@example.com"), SAMPLE_DEPARTMENT, SAMPLE_POSITION,
                new Address("Blk 45 Aljunied Street 85, #11-31"), SAMPLE_SALARY, EMPTY_BONUS,
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
