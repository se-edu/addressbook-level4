package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.UniqueTagList;

/**
 * Utility class containing sample persons data.
 */
public class SamplePersonsData {

    /**
     * A Person array containing sample persons data.
     */
    public static final Person[] SAMPLE_PERSONS_ARRAY = makeSamplePersonsArray();

    /**
     * @return a list of sample persons data.
     */
    public static List<Person> getSamplePersonsDataAsList() {
        return Arrays.asList(SAMPLE_PERSONS_ARRAY);
    }

    private static Person[] makeSamplePersonsArray() {
        try {
            //CHECKSTYLE.OFF: LineLength
            return new Person[]{
                new Person(new Name("Ali Muster"), new Phone("9482424"), new Email("hans@google.com"), new Address("4th street"), new UniqueTagList()),
                new Person(new Name("Boris Mueller"), new Phone("87249245"), new Email("ruth@google.com"), new Address("81th street"), new UniqueTagList()),
                new Person(new Name("Carl Kurz"), new Phone("95352563"), new Email("heinz@yahoo.com"), new Address("wall street"), new UniqueTagList()),
                new Person(new Name("Daniel Meier"), new Phone("87652533"), new Email("cornelia@google.com"), new Address("10th street"), new UniqueTagList()),
                new Person(new Name("Elle Meyer"), new Phone("9482224"), new Email("werner@gmail.com"), new Address("michegan ave"), new UniqueTagList()),
                new Person(new Name("Fiona Kunz"), new Phone("9482427"), new Email("lydia@gmail.com"), new Address("little tokyo"), new UniqueTagList()),
                new Person(new Name("George Best"), new Phone("9482442"), new Email("anna@google.com"), new Address("4th street"), new UniqueTagList()),
                new Person(new Name("Hoon Meier"), new Phone("8482424"), new Email("stefan@mail.com"), new Address("little india"), new UniqueTagList()),
                new Person(new Name("Ida Mueller"), new Phone("8482131"), new Email("hans@google.com"), new Address("chicago ave"), new UniqueTagList())
            };
            //CHECKSTYLE.ON: LineLength
        } catch (IllegalValueException e) {
            assert false;
            // not possible
            return null;
        }
    }

}
