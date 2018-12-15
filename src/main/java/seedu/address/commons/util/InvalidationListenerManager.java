package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * Manages a list of {@link InvalidationListener}.
 */
public class InvalidationListenerManager {
    private final ArrayList<InvalidationListener> listeners = new ArrayList<>();

    /**
     * Indicates that listeners are currently being called via {@link #callListeners(Observable)}.
     * Since any changes to the listeners list should only take effect on the next invocation of the method,
     * the listeners list should not be modified directly.
     * Instead, the listeners list should first be copied to tempListeners,
     * and any modification should be done on tempListeners.
     */
    private boolean areListenersBeingCalled;
    private ArrayList<InvalidationListener> tempListeners;

    /**
     * Calls {@link InvalidationListener#invalidated(Observable)} on all added listeners.
     * Any modifications to the listeners list during the invocation of this method
     * will only take effect on the next invocation of this method.
     *
     * @param observable The {@code Observable} that became invalid.
     */
    public void callListeners(Observable observable) {
        try {
            areListenersBeingCalled = true;
            for (InvalidationListener listener : listeners) {
                listener.invalidated(observable);
            }
        } finally {
            areListenersBeingCalled = false;
        }

        if (tempListeners != null) {
            listeners.clear();
            listeners.addAll(tempListeners);
            tempListeners = null;
        }
    }

    /**
     * Adds {@code listener} to the list of listeners.
     * If the same listener is added more that once, then it will be notified more than once.
     */
    public void addListener(InvalidationListener listener) {
        requireNonNull(listener);
        if (areListenersBeingCalled) {
            if (tempListeners == null) {
                tempListeners = new ArrayList<>(listeners);
            }
            tempListeners.add(listener);
        } else {
            listeners.add(listener);
        }
    }

    /**
     * Removes {@code listener} from the list of listeners.
     * If the given listener was not previously added, then this method call is a no-op.
     * If the given listener was added more than once, then only the first occurrence in the list will be removed.
     */
    public void removeListener(InvalidationListener listener) {
        requireNonNull(listener);
        if (areListenersBeingCalled) {
            if (tempListeners == null) {
                tempListeners = new ArrayList<>(listeners);
            }
            tempListeners.remove(listener);
        } else {
            listeners.remove(listener);
        }
    }

}
