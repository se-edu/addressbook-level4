package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for TestPersons.
 */
public class TestPersons {

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
     * Removes the person at an index in the array.
     * The resulting array will not have any gaps in between,
     * elements will be shifted forward if necessary.
     * @param persons original array to copy from.
     * @param targetIndexInOneIndexedFormat e.g. index 1 if the first element is to be removed,
     * @return A copy of the array with the person at specified index removed.
     */
    public static TestPerson[] removePersonAtIndex(final TestPerson[] persons,
                                                   int targetIndexInOneIndexedFormat) {
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

}
