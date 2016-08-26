package seedu.address.testutil;

import com.google.common.io.Files;
import guitests.guihandles.PersonCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import junit.framework.AssertionFailedError;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;
import seedu.address.TestApp;
import seedu.address.commons.FileUtil;
import seedu.address.commons.OsDetector;
import seedu.address.commons.XmlUtil;
import seedu.address.model.datatypes.AddressBook;
import seedu.address.model.datatypes.person.Person;
import seedu.address.model.datatypes.tag.Tag;
import seedu.address.storage.StorageAddressBook;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    public static void assertThrows(Class<? extends Throwable> expected, Runnable executable) {
        try {
            executable.run();
        }
        catch (Throwable actualException) {
            if (!actualException.getClass().isAssignableFrom(expected)) {
                String message = String.format("Expected thrown: %s, actual: %s", expected.getName(),
                        actualException.getClass().getName());
                throw new AssertionFailedError(message);
            } else return;
        }
        throw new AssertionFailedError(
                String.format("Expected %s to be thrown, but nothing was thrown.", expected.getName()));
    }

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    public static String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    public static final Person[] samplePersonData = {
            new Person("Hans", "Muster", 1),
            new Person("Ruth", "Mueller", 2),
            new Person("Heinz", "Kurz", 3),
            new Person("Cornelia", "Meier", 4),
            new Person("Werner", "Meyer", 5),
            new Person("Lydia", "Kunz", 6),
            new Person("Anna", "Best", 7),
            new Person("Stefan", "Meier", 8),
            new Person("Martin", "Mueller", 9)
    };

    public static final Tag[] sampleTagData = {
            new Tag("relatives"),
            new Tag("friends")
    };

    public static Person generateSamplePersonWithAllData(int customId) {
        final Person p = new Person("first", "last", customId);
        p.setStreet("some street");
        p.setPostalCode("1234");
        p.setCity("some city");
        p.setGithubUsername("SomeName");
        p.setBirthday(LocalDate.now());
        p.setTags(Arrays.asList(new Tag("A"), new Tag("B")));
        return p;
    }

    public static List<Person> generateSamplePersonData() {
        return Arrays.asList(samplePersonData);
    }

    /**
     * Appends the file name to the sandbox folder path
     * @param fileName
     * @return
     */
    public static String appendToSandboxPath(String fileName) {
        return SANDBOX_FOLDER + fileName;
    }

    public static void createDataFileWithSampleData(String filePath) {
        createDataFileWithData(generateSampleStorageAddressBook(), filePath);
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

    public static void main(String... s) {
        createDataFileWithSampleData(TestApp.SAVE_LOCATION_FOR_TESTING);
    }

    public static AddressBook generateSampleAddressBook() {
        return new AddressBook(Arrays.asList(samplePersonData), Arrays.asList(sampleTagData));
    }

    public static StorageAddressBook generateSampleStorageAddressBook() {
        return new StorageAddressBook(generateSampleAddressBook());
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
        if (keyCodeCombination.getShortcut() == KeyCombination.ModifierValue.DOWN) {
            keys.add(getPlatformSpecificShortcutKey());

        }
        keys.add(keyCodeCombination.getCode());
        return keys.toArray(new KeyCode[]{});
    }

    public static boolean isHeadlessEnvironment() {
        String headlessProperty = System.getProperty("testfx.headless");
        return headlessProperty != null && headlessProperty.equals("true");
    }

    /**
     * Returns {@code KeyCode.COMMAND or KeyCode.META} if on a Mac depending on headful/headless mode,
     * {@code KeyCode.CONTROL} otherwise.
     */
    private static KeyCode getPlatformSpecificShortcutKey() {
        KeyCode macShortcut = isHeadlessEnvironment() ? KeyCode.COMMAND : KeyCode.META;
        return OsDetector.isOnMac() ? macShortcut : KeyCode.CONTROL;
    }

    /**
     * Replaces any {@code KeyCode.SHORTCUT or KeyCode.META} with {@code KeyCode.META or KeyCode.COMMAND} on Macs
     * depending on headful/headless mode, and {@code KeyCode.CONTROL} on other platforms.
     */
    public static KeyCode[] scrub(KeyCode[] keyCodes) {
        for (int i = 0; i < keyCodes.length; i++) {
            if (keyCodes[i] == KeyCode.META || keyCodes[i] == KeyCode.SHORTCUT) {
                keyCodes[i] = getPlatformSpecificShortcutKey();
            }
        }
        return keyCodes;
    }

    public static void captureScreenShot(String fileName) {
        File file = GuiTest.captureScreenshot();
        try {
            Files.copy(file, new File(fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String descOnFail(Object... comparedObjects) {
        return "Comparison failed \n"
                + Arrays.asList(comparedObjects).stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Generates a minimal {@link KeyEvent} object that matches the {@code keyCombination}
     */
    public static KeyEvent getKeyEvent(String keyCombination) {
        String[] keys = keyCombination.split(" ");

        String key = keys[keys.length - 1];
        boolean shiftDown = keyCombination.toLowerCase().contains("shift");
        boolean metaDown = keyCombination.toLowerCase().contains("meta")
                || (keyCombination.toLowerCase().contains("shortcut") && OsDetector.isOnMac());
        boolean altDown = keyCombination.toLowerCase().contains("alt");
        boolean ctrlDown = keyCombination.toLowerCase().contains("ctrl")
                || keyCombination.toLowerCase().contains("shortcut") && !OsDetector.isOnMac();
        return new KeyEvent(null, null, null, KeyCode.valueOf(key), shiftDown, ctrlDown, altDown, metaDown);
    }

    public static void setFinalStatic(Field field, Object newValue) throws NoSuchFieldException, IllegalAccessException{
        field.setAccessible(true);
        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        // ~Modifier.FINAL is used to remove the final modifier from field so that its value is no longer
        // final and can be changed
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    public static void initRuntime() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.hideStage();
    }

    public static void tearDownRuntime() throws Exception {
        FxToolkit.cleanupStages();
    }

    /**
     * Gets private method of a class
     * Invoke the method using method.invoke(objectInstance, params...)
     *
     * Caveat: only find method declared in the current Class, not inherited from supertypes
     */
    public static Method getPrivateMethod(Class objectClass, String methodName) throws NoSuchMethodException {
        Method method = objectClass.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method;
    }

    public static void renameFile(File file, String newFileName) {
        try {
            Files.copy(file, new File(newFileName));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Gets mid point of a node relative to the screen.
     * @param node
     * @return
     */
    public static Point2D getScreenMidPoint(Node node) {
        double x = getScreenPos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScreenPos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x,y);
    }

    /**
     * Gets mid point of a node relative to its scene.
     * @param node
     * @return
     */
    public static Point2D getSceneMidPoint(Node node) {
        double x = getScenePos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScenePos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x,y);
    }

    /**
     * Gets the bound of the node relative to the parent scene.
     * @param node
     * @return
     */
    public static Bounds getScenePos(Node node) {
        return node.localToScene(node.getBoundsInLocal());
    }

    public static Bounds getScreenPos(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }

    public static double getSceneMaxX(Scene scene) {
        return scene.getX() + scene.getWidth();
    }

    public static double getSceneMaxY(Scene scene) {
        return scene.getX() + scene.getHeight();
    }

    public static String getOsDependentKeyCombinationString(String keyCombinationString) {
        if (!OsDetector.isOnMac()) return keyCombinationString;
        return keyCombinationString.replaceAll("Alt\\+", "⌥")
                .replaceAll("Meta\\+", "⌘")
                .replaceAll("Shift\\+", "⇧");
    }

    public static Object getLastElement(List<?> list) {
        return list.get(list.size() - 1);
    }

    public static Person[] removePersonsFromList(Person[] persons, Person... personsToRemove) {
        List<Person> listOfPersons = asList(persons);
        listOfPersons.removeAll(asList(personsToRemove));
        return listOfPersons.toArray(new Person[listOfPersons.size()]);
    }

    public static Person[] replacePersonFromList(Person[] persons, Person person, int index) {
        persons[index] = person;
        return persons;
    }

    public static Person[] addPersonsToList(Person[] persons, Person... personsToAdd) {
        List<Person> listOfPersons = asList(persons);
        listOfPersons.addAll(asList(personsToAdd));
        return listOfPersons.toArray(new Person[listOfPersons.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for(T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareCardAndPerson(PersonCardHandle card, Person person) {
        return card.getFirstName().equals(person.getFirstName()) && card.getLastName().equals(person.getLastName());
                //TODO: compare birthday, tag list and address.
    }

    public static Tag[] getTagList(String tags) {

        if (tags.equals("")) {
            return new Tag[]{};
        }

        final String[] split = tags.split(", ");

        final List<Tag> collect = Arrays.asList(split).stream().map(e -> new Tag(e.replaceFirst("Tag: ", ""))).collect(Collectors.toList());

        return collect.toArray(new Tag[split.length]);
    }
}
