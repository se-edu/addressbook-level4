package seedu.address.commons.core;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.text.Collator;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Unmodifiable view of an observable list
 */
public class UnmodifiableObservableList<E> implements ObservableList<E> {

    public static final String MUTATION_OP_EXCEPTION_MESSAGE = "Attempted to modify an unmodifiable view";

    private final ObservableList<? extends E> backingList;

    public UnmodifiableObservableList(ObservableList<? extends E> backingList) {
        if (backingList == null) {
            throw new NullPointerException();
        }
        this.backingList = backingList;
    }
    
    @Override
    public final void addListener(ListChangeListener<? super E> listener) {
        backingList.addListener(listener);
    }

    @Override
    public final void removeListener(ListChangeListener<? super E> listener) {
        backingList.removeListener(listener);
    }

    @Override
    public final void addListener(InvalidationListener listener) {
        backingList.addListener(listener);
    }

    @Override
    public final void removeListener(InvalidationListener listener) {
        backingList.removeListener(listener);
    }

    @Override
    public final boolean addAll(Object... elements) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean setAll(Object... elements) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean setAll(Collection<? extends E> col) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }
    
    @Override
    public final boolean removeAll(Object... elements) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }
    
    @Override
    public final boolean retainAll(Object... elements) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final void remove(int from, int to) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }


    @Override
    public final FilteredList<E> filtered(Predicate<E> predicate) {
        return new FilteredList<>(this, predicate);
    }

    @Override
    public final SortedList<E> sorted(Comparator<E> comparator) {
        return new SortedList<>(this, comparator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final SortedList<E> sorted() {
        return sorted(Comparator.nullsFirst((o1, o2) -> {
            if (o1 instanceof Comparable) {
                return ((Comparable) o1).compareTo(o2);
            }
            return Collator.getInstance().compare(o1.toString(), o2.toString());
        }));
    }

    @Override
    public final int size() {
        return backingList.size();
    }

    @Override
    public final boolean isEmpty() {
        return backingList.isEmpty();
    }
    
    @Override
    public final boolean contains(Object o) {
        return backingList.contains(o);
    }
    
    @Override
    public final Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<? extends E> i = backingList.iterator();

            public final boolean hasNext() {
                return i.hasNext();
            }
            public final E next() {
                return i.next();
            }
            public final void remove() {
                throw new UnsupportedOperationException();
            }
            @Override
            public final void forEachRemaining(Consumer<? super E> action) {
                // Use backing collection version
                i.forEachRemaining(action);
            }
        };
    }

    @Override
    public final Object[] toArray() {
        return backingList.toArray();
    }

    @Override
    public final <T> T[] toArray(T[] a) {
        return backingList.toArray(a);
    }

    @Override
    public final boolean add(E o) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }
    
    @Override
    public final boolean remove(Object o) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean containsAll(Collection<?> c) {
        return backingList.containsAll(c);
    }

    @Override
    public final boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }
    
    @Override
    public final boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }
    
    @Override
    public final boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final void sort(Comparator<? super E> c) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }
    
    @Override
    public final void clear() {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    
    @Override
    public final boolean equals(Object o) {
        return o == this || backingList.equals(o);
    }

    @Override
    public final int hashCode() {
        return backingList.hashCode();
    }

    
    @Override
    public final E get(int index) {
        return backingList.get(index);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Object set(int index, Object element) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final void add(int index, Object element) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @Override
    public final E remove(int index) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }
    
    @Override
    public final int indexOf(Object o) {
        return backingList.indexOf(o);
    }
    
    @Override
    public final int lastIndexOf(Object o) {
        return backingList.lastIndexOf(o);
    }

    @Override
    public final ListIterator<E> listIterator() {
        return listIterator(0);
    }
    
    @Override
    public final ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {
            private final ListIterator<? extends E> i = backingList.listIterator(index);

            public final boolean hasNext() {
                return i.hasNext();
            }
            public final E next() {
                return i.next();
            }
            public final boolean hasPrevious() {
                return i.hasPrevious();
            }
            public final E previous() {
                return i.previous();
            }
            public final int nextIndex() {
                return i.nextIndex();
            }
            public final int previousIndex() {
                return i.previousIndex();
            }

            public final void remove() {
                throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
            }
            public final void set(E e) {
                throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
            }
            public final void add(E e) {
                throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
            }

            @Override
            public final void forEachRemaining(Consumer<? super E> action) {
                i.forEachRemaining(action);
            }
        };
    }

    @Override
    public final List<E> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(backingList.subList(fromIndex, toIndex));
    }

    @Override
    public final boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException(MUTATION_OP_EXCEPTION_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Stream<E> stream() {
        return (Stream<E>) backingList.stream();
    }
    
    @Override
    public final void forEach(Consumer<? super E> action) {
        backingList.forEach(action);
    }

}
