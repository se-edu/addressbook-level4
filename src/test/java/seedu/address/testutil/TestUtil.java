package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import guitests.guihandles.PersonCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Tweaks the {@code keyCodeCombination} to resolve the {@code KeyCode.SHORTCUT} to their
     * respective platform-specific keycodes.
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
     * Returns a {@code Point2D} object containing the x and y coordinates
     * of the mid point on the screen that {@code node} is on.
     */
    public static Point2D getScreenMidPoint(Node node) {
        double x = getNodeBounds(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getNodeBounds(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x, y);
    }

    /**
     * Returns the bounds of a {@code node} relative to the screen.
     */
    private static Bounds getNodeBounds(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }

    /**
     * Removes a subset from the array of persons.
     * @param persons The array of persons.
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
     * @param persons original array to copy from.
     * @param targetIndexInOneIndexedFormat e.g. index 1 if the first element is to be removed.
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
