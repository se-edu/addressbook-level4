package seedu.address.testutil;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import guitests.guihandles.PersonCardHandle;
import junit.framework.AssertionFailedError;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    private static final Person[] SAMPLE_PERSON_DATA = getSamplePersonData();

    public static void assertThrows(Class<? extends Throwable> expected, Runnable executable) {
        try {
            executable.run();
        } catch (Throwable actualException) {
            if (actualException.getClass().isAssignableFrom(expected)) {
                return;
            }
            String message = String.format("Expected thrown: %s, actual: %s", expected.getName(),
                    actualException.getClass().getName());
            throw new AssertionFailedError(message);
        }
        throw new AssertionFailedError(
                String.format("Expected %s to be thrown, but nothing was thrown.", expected.getName()));
    }

    private static Person[] getSamplePersonData() {
        try {
            //CHECKSTYLE.OFF: LineLength
            return new Person[]{
                new Person(new Name("Ali Muster"), new Phone("9482424"), new Email("hans@example.com"), new Address("4th street"), getTagSet()),
                new Person(new Name("Boris Mueller"), new Phone("87249245"), new Email("ruth@example.com"), new Address("81th street"), getTagSet()),
                new Person(new Name("Carl Kurz"), new Phone("95352563"), new Email("heinz@example.com"), new Address("wall street"), getTagSet()),
                new Person(new Name("Daniel Meier"), new Phone("87652533"), new Email("cornelia@example.com"), new Address("10th street"), getTagSet()),
                new Person(new Name("Elle Meyer"), new Phone("9482224"), new Email("werner@example.com"), new Address("michegan ave"), getTagSet()),
                new Person(new Name("Fiona Kunz"), new Phone("9482427"), new Email("lydia@example.com"), new Address("little tokyo"), getTagSet()),
                new Person(new Name("George Best"), new Phone("9482442"), new Email("anna@example.com"), new Address("4th street"), getTagSet()),
                new Person(new Name("Hoon Meier"), new Phone("8482424"), new Email("stefan@example.com"), new Address("little india"), getTagSet()),
                new Person(new Name("Ida Mueller"), new Phone("8482131"), new Email("hans@example.com"), new Address("chicago ave"), getTagSet())
            };
            //CHECKSTYLE.ON: LineLength
        } catch (IllegalValueException e) {
            throw new AssertionError();
        }
    }

    public static List<Person> generateSamplePersonData() {
        return Arrays.asList(SAMPLE_PERSON_DATA);
    }

    /**
     * Appends the file name to the sandbox folder path.
     * Creates the sandbox folder if it doesn't exist.
     * @param fileName
     * @return
     */
    public static String getFilePathInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    public static <T> void createDataFileWithData(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes a subset from the list of persons.
     * @param persons The list of persons
     * @param personsToRemove The subset of persons.
     * @return The modified persons after removal of the subset from persons.
     */
    private static Person[] removePersonsFromList(final Person[] persons, Person... personsToRemove) {
        List<Person> listOfPersons = Arrays.asList(persons);
        listOfPersons.removeAll(Arrays.asList(personsToRemove));
        return listOfPersons.toArray(new Person[listOfPersons.size()]);
    }


    /**
     * Returns a copy of the list with the person at specified index removed.
     * @param list original list to copy from
     */
    public static Person[] removePersonFromList(final Person[] list, Index index) {
        return removePersonsFromList(list, list[index.getZeroBased()]);
    }

    /**
     * Appends persons to the array of persons.
     * @param persons A array of persons.
     * @param personsToAdd The persons that are to be appended behind the original array.
     * @return The modified array of persons.
     */
    public static Person[] addPersonsToList(final Person[] persons, Person... personsToAdd) {
        List<Person> listOfPersons = Arrays.asList(persons);
        listOfPersons.addAll(Arrays.asList(personsToAdd));
        return listOfPersons.toArray(new Person[listOfPersons.size()]);
    }

    public static boolean compareCardAndPerson(PersonCardHandle card, ReadOnlyPerson person) {
        return card.isSamePerson(person);
    }

}
