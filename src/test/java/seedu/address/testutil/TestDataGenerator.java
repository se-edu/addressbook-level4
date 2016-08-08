package seedu.address.testutil;

import seedu.address.exceptions.DataConversionException;
import seedu.address.model.datatypes.AddressBook;
import seedu.address.model.datatypes.person.Person;
import seedu.address.model.datatypes.tag.Tag;
import seedu.address.storage.StorageManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A utility class to generate data for testing.
 */
public class TestDataGenerator {

    private static String DATA_FILE_NAME = "scalabilitydata.xml";
    private static int NO_OF_PERSONS = 1000;
    private static List<Tag> allTags = generateTagList();

    private static List<Tag> generateTagList() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("friends"));
        tags.add(new Tag("colleagues"));
        tags.add(new Tag("relatives"));
        return tags;
    }

    public static void main(String[] args) throws IOException, DataConversionException {
        StorageManager.saveAddressBook(new File(DATA_FILE_NAME), generateData());
    }

    private static AddressBook generateData() {
        AddressBook ab = new AddressBook();
        ab.setTags(allTags);

        IntStream.range(0, NO_OF_PERSONS)
                .forEach(i -> ab.addPerson(generatePerson(i)));

        return ab;
    }

    private static Person generatePerson(int i) {
        Person p = new Person("FirstName" + i, "LastName" + i, i);
        p.setCity("City " + i);
        p.setGithubUsername("dummy");
        p.setPostalCode("123456");
        p.setBirthday(LocalDate.now().minusDays(i));
        p.setTags(getRandomListOfTags());
        return p;
    }

    /**
     * Returns a random subset of the sample tags.
     */
    private static List<Tag> getRandomListOfTags() {
        return allTags.stream()
                .filter(t -> new Random().nextBoolean())
                .collect(Collectors.toList());
    }

}
