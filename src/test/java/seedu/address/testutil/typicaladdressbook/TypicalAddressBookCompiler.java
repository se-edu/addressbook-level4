package seedu.address.testutil.typicaladdressbook;

import static seedu.address.testutil.typicaladdressbook.TypicalPersons.getTypicalPersons;
import static seedu.address.testutil.typicaladdressbook.TypicalTasks.getTypicalTasks;
import static seedu.address.testutil.typicaladdressbook.TypicalTutees.getTypicalPersonsAndTutees;

import seedu.address.model.AddressBook;
import seedu.address.model.Task;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.TimingClashException;

/**
 * A utility class providing an Address Book with its initialized data in order to support tests.
 */
public class TypicalAddressBookCompiler {
    /**
     * Returns an {@code AddressBook} with all the typical persons and typical tasks.
     *
     */
    public static AddressBook getTypicalAddressBook1() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Task task : getTypicalTasks()) {
            try {
                ab.addTask(task);
            } catch (TimingClashException tce) {
                throw new AssertionError("time clash is not possible");
            }
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons & tutees and typical tasks.
     */
    public static AddressBook getTypicalAddressBook2() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersonsAndTutees()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Task task : getTypicalTasks()) {
            try {
                ab.addTask(task);
            } catch (TimingClashException tce) {
                throw new AssertionError("time clash is not possible");
            }
        }
        return ab;
    }
}
