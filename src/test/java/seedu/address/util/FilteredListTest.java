package seedu.address.util;

import seedu.address.model.datatypes.person.Person;
import seedu.address.testutil.TestUtil;
import seedu.address.util.collections.FilteredList;
import seedu.address.commons.StringUtil;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FilteredListTest {
    // datatype to observe sent changes by filtered list
    private class Observer<T> extends TransformationList<T, T> {
        private List<ListChangeListener.Change<? extends T>> changeList = new ArrayList<>();

        protected Observer(ObservableList<? extends T> source) {
            super(source);
        }

        @Override
        protected void sourceChanged(ListChangeListener.Change<? extends T> c) {
            changeList.add(c);
        }

        public List<ListChangeListener.Change<? extends T>> getChangeList() {
            return changeList;
        }

        @Override
        public int getSourceIndex(int index) {
            return 0;
        }

        @Override
        public T get(int index) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    }

    @Test
    public void stringList_setFiltered_correctFilteredList() {
        ObservableList<String> listOfStrings = FXCollections.observableArrayList();
        listOfStrings.addAll("Apple", "Orange", "Pear", "Watermelon", "Strawberry", "Blueberry", "Cranberry");
        FilteredList<String> filteredList = new FilteredList<>(listOfStrings);
        assertEquals(7, filteredList.size());

        filteredList.setPredicate(string -> string.length() == 9);
        assertEquals(2, filteredList.size());
    }

    @Test
    public void stringList_changeFilter_correctFilteredList() {
        ObservableList<String> listOfStrings = FXCollections.observableArrayList();
        listOfStrings.addAll("Apple", "Orange", "Pear", "Watermelon", "Strawberry", "Blueberry", "Cranberry");
        FilteredList<String> filteredList = new FilteredList<>(listOfStrings);
        assertEquals(7, filteredList.size());

        filteredList.setPredicate(string -> StringUtil.containsIgnoreCase(string, "berry"));
        assertEquals(3, filteredList.size());

        filteredList.setPredicate(string -> StringUtil.containsIgnoreCase(string, "a"));
        assertEquals(6, filteredList.size());
    }

    @Test
    public void personList_changeFilter_unaffectedPersonsNotSentInChanges() {
        ObservableList<Person> listOfPersons = FXCollections.observableArrayList();
        listOfPersons.addAll(TestUtil.generateSamplePersonData());
        FilteredList<Person> filteredList = new FilteredList<>(listOfPersons);
        Observer<Person> observer = new Observer<>(filteredList);
        List<ListChangeListener.Change<? extends Person>> changeList = observer.getChangeList();

        assertEquals(9, filteredList.size());
        assertEquals(0, changeList.size());

        filteredList.setPredicate(person -> person.getLastName().equalsIgnoreCase("mueller"));

        // correct filtered list
        assertEquals(2, filteredList.size());
        assertEquals("Ruth", filteredList.get(0).getFirstName());
        assertEquals("Martin", filteredList.get(1).getFirstName());
        // a change has been added
        assertEquals(1, changeList.size());
        ListChangeListener.Change firstChange = changeList.get(0);
        assertTrue(firstChange.next());
        // inspect removal of 7 items
        assertEquals(7, firstChange.getRemovedSize());
        assertEquals(0, firstChange.getAddedSize());
        assertFalse(firstChange.next());

        filteredList.setPredicate(person -> person.getLastName().contains("z"));

        // correct filtered list
        assertEquals(2, filteredList.size());
        assertEquals("Heinz", filteredList.get(0).getFirstName());
        assertEquals("Lydia", filteredList.get(1).getFirstName());
        // a change has been added
        assertEquals(2, changeList.size());
        ListChangeListener.Change secondChange = changeList.get(1);
        assertTrue(secondChange.next());
        // inspect removal of 2 items
        assertEquals(2, secondChange.getRemovedSize());
        assertEquals(0, secondChange.getAddedSize());
        assertTrue(secondChange.next());
        // inspect addition of 2 items
        assertEquals(0, secondChange.getRemovedSize());
        assertEquals(2, secondChange.getAddedSize());
        assertFalse(secondChange.next());
    }
}
