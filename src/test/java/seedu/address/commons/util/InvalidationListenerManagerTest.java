package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;

public class InvalidationListenerManagerTest {
    private final SimpleObjectProperty<Object> dummyObservable = new SimpleObjectProperty<>();
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();
    private int counter;

    @Test
    public void addListener_sameListenerOnce_listenerAdded() {
        invalidationListenerManager.addListener(observable -> {
            assertEquals(dummyObservable, observable);
            counter++;
        });
        invalidationListenerManager.callListeners(dummyObservable);
        assertEquals(1, counter);
    }

    @Test
    public void addListener_sameListenerTwice_listenerAddedTwice() {
        InvalidationListener listener = observable -> counter++;
        invalidationListenerManager.addListener(listener);
        invalidationListenerManager.addListener(listener);
        invalidationListenerManager.callListeners(dummyObservable);
        assertEquals(2, counter);
    }

    @Test
    public void addListener_listenersBeingCalled_listenerNotCalled() {
        InvalidationListener listener1 = observable -> {
            throw new AssertionError("should not be called");
        };
        InvalidationListener listener2 = observable -> invalidationListenerManager.addListener(listener1);
        invalidationListenerManager.addListener(listener2);
        invalidationListenerManager.callListeners(dummyObservable);
    }

    @Test
    public void removeListener_singleListenerAdded_listenerRemoved() {
        InvalidationListener listener = observable -> counter++;
        invalidationListenerManager.addListener(listener);
        invalidationListenerManager.removeListener(listener);
        invalidationListenerManager.callListeners(dummyObservable);
        assertEquals(0, counter);
    }

    @Test
    public void removeListener_sameListenerAddedTwice_firstListenerRemoved() {
        InvalidationListener listener = observable -> counter++;
        invalidationListenerManager.addListener(listener);
        invalidationListenerManager.addListener(listener);
        invalidationListenerManager.removeListener(listener);
        invalidationListenerManager.callListeners(dummyObservable);
        assertEquals(1, counter);
    }

    @Test
    public void removeListener_listenersBeingCalled_listenerStillCalled() {
        InvalidationListener listener1 = observable -> counter++;
        InvalidationListener listener2 = observable -> invalidationListenerManager.removeListener(listener1);
        invalidationListenerManager.addListener(listener2);
        invalidationListenerManager.addListener(listener1);
        invalidationListenerManager.callListeners(dummyObservable);
        assertEquals(1, counter);
    }
}
