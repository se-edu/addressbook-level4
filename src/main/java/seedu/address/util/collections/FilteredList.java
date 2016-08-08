package seedu.address.util.collections;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;

import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class is meant to handle filtering the given list of elements given a predicate
 *
 * Elements not affected by the filter change will not be sent in the changes, unlike
 * javafx.collections.transformation.FilteredList which removes all elements and adds the matching ones back
 */
public class FilteredList<E> extends TransformationList<E, E> {
    private ObservableList<E> sourceCopy;
    private Predicate<E> predicate;
    private ObservableList<E> filteredList;

    public FilteredList(ObservableList<E> source, Predicate<E> predicate) {
        this(source);
        setPredicate(predicate);
    }

    public FilteredList(ObservableList<E> source) {
        super(source);
        this.sourceCopy = FXCollections.observableArrayList(source);
        this.filteredList = FXCollections.observableArrayList(source);
    }

    @Override
    public E get(int index) {
        return filteredList.get(index);
    }

    @Override
    public int size() {
        return filteredList.size();
    }

    private ObservableList<E> filterList(Predicate<E> predicate) {
        return sourceCopy.stream()
                .filter(predicate)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * Sets the predicate filter for the list
     *
     * @param predicate should not be null
     */
    public void setPredicate(Predicate<E> predicate) {
        this.predicate = predicate;
        ObservableList<E> newFilteredList = filterList(predicate);

        ObservableList<E> removedList = getRemovedList(filteredList, newFilteredList);
        ObservableList<E> addedList = getAddedList(filteredList, newFilteredList);

        beginChange();
        fireRemoveChanges(removedList, filteredList);
        fireAddChanges(addedList, filteredList);
        endChange();
    }

    private ObservableList<E> getAddedList(ObservableList<E> oldList, ObservableList<E> newList) {
        return sourceCopy.stream()
                .filter(e -> !oldList.contains(e))
                .filter(newList::contains)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    private ObservableList<E> getRemovedList(ObservableList<E> oldList, ObservableList<E> newList) {
        return getAddedList(newList, oldList);
    }

    /**
     * Fires add change notifications for any observers
     *
     * @param addedList list of added elements
     * @param updatedList list after the add changes have been made
     */
    private void fireAddChanges(ObservableList<E> addedList, ObservableList<E> updatedList) {
        addedList.forEach(e -> {
            updatedList.add(e);
            nextAdd(updatedList.indexOf(e), updatedList.indexOf(e) + 1);
        });
    }

    /**
     * Fires remove change notifications for any observers
     *
     * @param removedList list of removed elements
     * @param originalList list before the remove changes have been made
     */
    private void fireRemoveChanges(ObservableList<E> removedList, ObservableList<E> originalList) {
        removedList.forEach(e -> {
            originalList.remove(e);
            nextRemove(originalList.indexOf(e), e);
        });
    }

    @Override
    protected void sourceChanged(ListChangeListener.Change<? extends E> c) {
        beginChange();
        while (c.next()) {
            if (c.wasAdded() || c.wasRemoved()) {
                sourceCopy.removeAll(c.getRemoved());
                c.getRemoved().stream()
                        .filter(predicate)
                        .forEach(e -> {
                            filteredList.remove(e);
                            nextRemove(filteredList.indexOf(e), e);
                        });

                sourceCopy.addAll(c.getAddedSubList());
                c.getAddedSubList().stream()
                        .filter(predicate)
                        .forEach(e -> {
                            filteredList.add(e);
                            nextAdd(filteredList.indexOf(e), filteredList.indexOf(e) + 1);
                        });
            }
        }
        endChange();
    }

    @Override
    public int getSourceIndex(int index) {
        return sourceCopy.indexOf(filteredList.get(index));
    }
}
