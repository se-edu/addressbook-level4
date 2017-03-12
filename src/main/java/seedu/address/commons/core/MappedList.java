package seedu.address.commons.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;

/**
 * An utility class that maps a source {@code ObservableList} consisting of elements F into
 * another {@code ObservableList} consisting of elements E using the mapper {@code Function}
 *
 * @param <E> The type to convert into
 * @param <F> The type to convert from
 */
public class MappedList<E, F> extends TransformationList<E, F> {

    private final Function<F, E> mapper;

    public MappedList(ObservableList<? extends F> source, Function<F, E> mapper) {
        super(source);
        this.mapper = mapper;
    }

    @Override
    public int getSourceIndex(int index) {
        return index;
    }

    @Override
    public E get(int index) {
        return mapper.apply(getSource().get(index));
    }

    @Override
    public int size() {
        return getSource().size();
    }

    @Override
    protected void sourceChanged(Change<? extends F> change) {
        fireChange(new Change<E>(this) {

            @Override
            public boolean wasAdded() {
                return change.wasAdded();
            }

            @Override
            public boolean wasRemoved() {
                return change.wasRemoved();
            }

            @Override
            public boolean wasReplaced() {
                return change.wasReplaced();
            }

            @Override
            public boolean wasUpdated() {
                return change.wasUpdated();
            }

            @Override
            public boolean wasPermutated() {
                return change.wasPermutated();
            }

            @Override
            public int getPermutation(int i) {
                return change.getPermutation(i);
            }

            @Override
            protected int[] getPermutation() {
                // This method is only called by the superclass methods
                // wasPermutated() and getPermutation(int), which are
                // both overriden by this class. There is no other way
                // this method can be called.
                throw new AssertionError("Unreachable code");
            }

            @Override
            public List<E> getRemoved() {
                ArrayList<E> res = new ArrayList<>(change.getRemovedSize());
                for (F element: change.getRemoved()) {
                    res.add(mapper.apply(element));
                }
                return res;
            }

            @Override
            public int getFrom() {
                return change.getFrom();
            }

            @Override
            public int getTo() {
                return change.getTo();
            }

            @Override
            public boolean next() {
                return change.next();
            }

            @Override
            public void reset() {
                change.reset();
            }
        });
    }
}
