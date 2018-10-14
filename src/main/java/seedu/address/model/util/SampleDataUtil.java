package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTranscript;
import seedu.address.model.Transcript;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {


    public static final Module DISCRETE_MATH = new ModuleBuilder().withCode("CS1231")
        .withYear(1)
        .withSemester(Semester.SEMESTER_ONE)
        .withCredit(4)
        .withGrade("A+")
        .build();

    public static final Module PROGRAMMING_METHODOLOGY_TWO = new ModuleBuilder().withCode("CS2030")
        .withYear(2)
        .withSemester(Semester.SEMESTER_TWO)
        .withCredit(4)
        .withGrade("B+")
        .build();

    public static final Module DATA_STRUCTURES = new ModuleBuilder().withCode("CS2040")
        .withYear(3)
        .withSemester(Semester.SEMESTER_SPECIAL_ONE)
        .withCredit(4)
        .withGrade("F")
        .build();

    public static final Module ASKING_QUESTIONS = new ModuleBuilder().withCode("GEQ1000")
        .withYear(1)
        .withSemester(Semester.SEMESTER_ONE)
        .withCredit(4)
        .withGrade("CS")
        .build();

    public static final Double MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP = 3.0;

    public static final Module SOFTWARE_ENGINEERING = new ModuleBuilder().withCode("CS2103")
        .withYear(3)
        .withSemester(Semester.SEMESTER_ONE)
        .withCredit(4)
        .withGrade("A+")
        .build();

    public static final Module DATABASE_SYSTEMS = new ModuleBuilder().withCode("CS2102")
        .withYear(2)
        .withSemester(Semester.SEMESTER_ONE)
        .withCredit(4)
        .withGrade("A+")
        .build();

    public static final Module DATABASE_SYSTEMS_2MC = new ModuleBuilder().withCode("CS2102B")
        .withYear(2)
        .withSemester(Semester.SEMESTER_ONE)
        .withCredit(2)
        .withGrade("A+")
        .build();


    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 GePersonylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
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
     * Returns an {@code Transcript} given modules as arguments.
     */
    public static ReadOnlyTranscript getTranscriptWithModules(Module... modules) {
        Transcript tr = new Transcript();
        for (Module module : modules) {
            tr.addModule(module);
        }
        return tr;
    }

    public static ReadOnlyTranscript getSampleTranscript() {
        return getTranscriptWithModules(
            DISCRETE_MATH,
            PROGRAMMING_METHODOLOGY_TWO,
            DATA_STRUCTURES,
            ASKING_QUESTIONS,
            SOFTWARE_ENGINEERING,
            DATABASE_SYSTEMS,
            DATABASE_SYSTEMS_2MC);
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
