package seedu.address.commons.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

//@@author A0135812L
/**
* Maintains a sorted database for the tasks
*/
public class SortedObservableArrayList<E extends Comparable<? super E>> implements ObservableList<E> {

    private ObservableList<E> backingList;
    public SortedObservableArrayList(){
        backingList = FXCollections.observableArrayList();
    }
    
    public SortedObservableArrayList(ObservableList<? extends E> backingList){
        this.backingList = FXCollections.observableArrayList();
        addAll(backingList);
    }
    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    public boolean isEmpty() {
        return backingList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return backingList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) backingList.iterator();
    }

    @Override
    public Object[] toArray() {
        return backingList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return backingList.toArray(a);
    }

    @Override
    public boolean add(E e) {
        int index = Collections.binarySearch(backingList, e);
        if (index < 0) index = ~index;
        add(index, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return backingList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return backingList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        
        for(E e: c){
            add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return backingList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return backingList.retainAll(c);
    }

    @Override
    public void clear() {
        backingList.clear();
    }

    @Override
    public E get(int index) {
        return backingList.get(index);
    }

    @Override
    public E set(int index, E element){
        remove(index);
        add(element);
        return get(indexOf(element));
    }

    @Override
    public void add(int index, E element) {
        backingList.add(index, element);
    }

    @Override
    public E remove(int index) {
        return backingList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return backingList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return backingList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return backingList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return backingList.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return backingList.subList(fromIndex, toIndex);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        backingList.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        backingList.removeListener(listener);
    }

    @Override
    public void addListener(ListChangeListener<? super E> listener) {
        backingList.addListener(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super E> listener) {
        backingList.removeListener(listener);
    }

    @Override
    public boolean addAll(E... elements) {
        for(E e: elements){
            add(e);
        }
        return true;
    }

    @Override
    public boolean setAll(E... elements) {
        clear();
        return addAll(elements);
    }

    @Override
    public boolean setAll(Collection<? extends E> col) {
        clear();
        return addAll(col);
    }

    @Override
    public boolean removeAll(E... elements) {
        return backingList.removeAll(elements);
    }

    @Override
    public boolean retainAll(E... elements) {
        return backingList.retainAll(elements);
    }

    @Override
    public void remove(int from, int to) {
        backingList.remove(from, to);
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof SortedObservableArrayList){
            SortedObservableArrayList other = (SortedObservableArrayList) o;
            return containsAll(other);
        }else{
            return false;
        }
    }

}
