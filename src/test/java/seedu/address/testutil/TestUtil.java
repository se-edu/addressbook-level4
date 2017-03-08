package seedu.address.testutil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import guitests.guihandles.PersonCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.UniqueTagList;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temporary files created during testing. Ignored by Git.
     */
    public static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    /**
     * A Person array containing sample persons data.
     */
    public static final Person[] SAMPLE_PERSONS_ARRAY = makeSamplePersonsArray();

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

    /**
     * @return a list of sample persons data.
     */
    public static List<Person> getSamplePersonsDataAsList() {
        return Arrays.asList(SAMPLE_PERSONS_ARRAY);
    }

    /**
     * Appends the {@code fileName} to the sandbox folder path.
     * Creates the sandbox folder if it does not exist.
     * @return the sandbox folder file path appended with given file name.
     */
    public static String getFilePathOfFileInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    /**
     * Saves the {@code data} to a file at {@code filePath}.
     * Creates the file at {@code filePath} if it doesn't exist.
     */
    public static <T> void save(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Tweaks the {@code keyCodeCombination} to resolve the {@code KeyCode.SHORTCUT} to their
     * respective platform-specific keycodes
     */
    public static KeyCode[] scrub(KeyCodeCombination keyCodeCombination) {
        List<KeyCode> keys = new ArrayList<>();
        if (keyCodeCombination.getAlt() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.ALT);
        }
        if (keyCodeCombination.getShift() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.SHIFT);
        }
        if (keyCodeCombination.getMeta() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.META);
        }
        if (keyCodeCombination.getControl() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.CONTROL);
        }
        keys.add(keyCodeCombination.getCode());
        return keys.toArray(new KeyCode[]{});
    }

    /**
     * Gets mid point of the screen that {@code node} is on.
     * @return a {@code Point2D} object containing the x and y coordinates
     * of the mid point on the screen that {@code node} is on.
     */
    public static Point2D getScreenMidPoint(Node node) {
        double x = getNodeBounds(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getNodeBounds(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x, y);
    }

    /**
     * @return the bounds of a {@code node} relative to the screen.
     */
    private static Bounds getNodeBounds(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }

    /**
     * Removes a subset from the array of persons.
     * @param persons The array of persons
     * @param personsToRemove The subset of persons.
     * @return The modified persons after removal of the subset from persons.
     */
    public static TestPerson[] removePersons(final TestPerson[] persons, TestPerson... personsToRemove) {
        List<TestPerson> listOfPersons = asList(persons);
        listOfPersons.removeAll(asList(personsToRemove));
        return listOfPersons.toArray(new TestPerson[listOfPersons.size()]);
    }

    /**
     * Removes the person at {@code targetIndexInOneIndexedFormat} from the array.
     * The resulting array will not have any gaps in between,
     * elements will be shifted forward if necessary.
     * @param persons original array to copy from
     * @param targetIndexInOneIndexedFormat e.g. index 1 if the first element is to be removed
     * @return A copy of the array with the person at specified index removed.
     */
    public static TestPerson[] removePerson(final TestPerson[] persons, int targetIndexInOneIndexedFormat) {
        return removePersons(persons, persons[targetIndexInOneIndexedFormat - 1]);
    }

    /**
     * Appends persons to the array of persons.
     * @param persons An array of persons.
     * @param personsToAdd The persons that are to be appended to the original array.
     * @return The modified array of persons.
     */
    public static TestPerson[] addPersons(final TestPerson[] persons, TestPerson... personsToAdd) {
        List<TestPerson> listOfPersons = asList(persons);
        listOfPersons.addAll(asList(personsToAdd));
        return listOfPersons.toArray(new TestPerson[listOfPersons.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    /**
     * Returns true if {@code person} is the same as the person on {@code card}.
     * The persons are considered to be the same if they have the same name, phone, email,
     * address, and tags.
     * Returns false otherwise.
     */
    public static boolean isPersonSameAsPersonOnCard(PersonCardHandle card, ReadOnlyPerson person) {
        return card.isSamePerson(person);
    }

}
