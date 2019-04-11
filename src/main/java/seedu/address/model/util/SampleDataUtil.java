package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ExpenditureList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyExpenditureList;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.TaskList;
import seedu.address.model.WorkoutBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.purchase.Price;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.purchase.PurchaseName;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DeadlineDate;
import seedu.address.model.task.DeadlineTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alexis Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
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

    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new TaskName("CS2101 Developer Guide"), new DeadlineTime("2359"), new DeadlineDate("250619"),
                    getTagSet("IMPORTANT", "TEAM")),
            new Task(new TaskName("CS2113 User Guide"), new DeadlineTime("2359"), new DeadlineDate("250619"),
                    getTagSet()),
            new Task(new TaskName("Product Demo"), new DeadlineTime("0800"), new DeadlineDate("230719"),
                    getTagSet("TEAM", "PRACTICE")),
            new Task(new TaskName("CS3235 Assignment"), new DeadlineTime("2359"), new DeadlineDate("230519"),
                    getTagSet("RACHEL")),
            new Task(new TaskName("Review Pull Request"), new DeadlineTime("1900"), new DeadlineDate("150519"),
                    getTagSet())
        };
    }

    public static Purchase[] getSamplePurchases() {
        return new Purchase[] {
            new Purchase(new PurchaseName("Chicken rice"), new Price("3.50"),
                getTagSet("food")),
            new Purchase(new PurchaseName("Bicycle rental for 3hrs"), new Price("10.00"),
                getTagSet("family", "outing")),
            new Purchase(new PurchaseName("Calbee potato chip"), new Price("1.50"),
                getTagSet("snack")),
            new Purchase(new PurchaseName("Gongcha honey milk tea"), new Price("3.70"),
                getTagSet("drinks")),
            new Purchase(new PurchaseName("Movie captain marvel"), new Price("13.50"),
                getTagSet("entertainment", "sunday"))
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

    public static ReadOnlyTaskList getSampleTaskList() {
        TaskList sampleTl = new TaskList();
        for (Task sampleTask : getSampleTasks()) {
            sampleTl.addTask(sampleTask);
        }
        return sampleTl;
    }

    public static ReadOnlyExpenditureList getSampleExpenditureList() {
        ExpenditureList sampleExpl = new ExpenditureList();
        for (Purchase samplePurchase : getSamplePurchases()) {
            sampleExpl.addPurchase(samplePurchase);
        }
        return sampleExpl;
    }

    public static ReadOnlyWorkoutBook getSampleWorkoutBook() {
        WorkoutBook workoutBook = new WorkoutBook();
        //TODO
        return workoutBook;
    }
}
