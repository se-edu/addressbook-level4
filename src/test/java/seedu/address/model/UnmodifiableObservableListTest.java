package seedu.address.model;

import javafx.collections.FXCollections;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.core.UnmodifiableObservableList;

import java.util.*;

import static org.junit.Assert.assertSame;
import static seedu.address.testutil.TestUtil.assertThrows;

public class UnmodifiableObservableListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    List<Integer> backing;
    UnmodifiableObservableList<Integer> list;

    @Before
    public void setup() {
        backing = new ArrayList<>();
        backing.add(10);
        list = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
    }

    @Test
    public void transformationListGenerators_correctBackingList() {
        assertSame(list.sorted().getSource(), list);
        assertSame(list.filtered(i -> true).getSource(), list);
    }

    @Test
    public void mutatingMethods_disabled() {

        final Class<UnsupportedOperationException> ex = UnsupportedOperationException.class;

        assertThrows(ex, () -> list.add(0, 2));
        assertThrows(ex, () -> list.add(3));

        assertThrows(ex, () -> list.addAll(2, 1));
        assertThrows(ex, () -> list.addAll(backing));
        assertThrows(ex, () -> list.addAll(0, backing));

        assertThrows(ex, () -> list.set(0, 2));

        assertThrows(ex, () -> list.setAll(new ArrayList<Number>()));
        assertThrows(ex, () -> list.setAll(1, 2));

        assertThrows(ex, () -> list.remove(0, 1));
        assertThrows(ex, () -> list.remove(null));
        assertThrows(ex, () -> list.remove(0));

        assertThrows(ex, () -> list.removeAll(backing));
        assertThrows(ex, () -> list.removeAll(1, 2));

        assertThrows(ex, () -> list.retainAll(backing));
        assertThrows(ex, () -> list.retainAll(1, 2));

        assertThrows(ex, () -> list.replaceAll(i -> 1));

        assertThrows(ex, () -> list.sort(Comparator.naturalOrder()));

        assertThrows(ex, () -> list.clear());

        final Iterator<Integer> iter = list.iterator();
        iter.next();
        assertThrows(ex, iter::remove);

        final ListIterator<Integer> liter = list.listIterator();
        liter.next();
        assertThrows(ex, liter::remove);
        assertThrows(ex, () -> liter.add(5));
        assertThrows(ex, () -> liter.set(3));
        assertThrows(ex, () -> list.removeIf(i -> true));
    }
}
