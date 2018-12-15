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
    private ArrayList<InvalidationListener> lockedListeners;

    /**
     * If set to true, the listeners list will not be modified.
     * Instead, it will be copied to lockedListeners, and any modification will be done on lockedListeners.
     */
    private boolean locked;

    /**
     * Adds a listener to the list of listeners.
     * If the same listener is added more that once, then it will be notified more than once.
     */
    public void addListener(InvalidationListener listener) {
        requireNonNull(listener);
        if (locked) {
            if (lockedListeners == null) {
                lockedListeners = new ArrayList<>(listeners);
            }
            lockedListeners.add(listener);
        } else {
            listeners.add(listener);
        }
    }

    /**
     * Removes the given listener from the list of listeners.
     * If the given listener was not previously added, then this method call is a no-op.
     * If the given listener was added more than once, then only the first occurrence in the list will be removed.
     */
    public void removeListener(InvalidationListener listener) {
        requireNonNull(listener);
        if (locked) {
            if (lockedListeners == null) {
                lockedListeners = new ArrayList<>(listeners);
            }
            lockedListeners.remove(listener);
        } else {
            listeners.remove(listener);
        }
    }

    /**
     * Calls {@link InvalidationListener#invalidated(Observable)} on all added listeners.
     *
     * @param observable The {@code Observable} that became invalid.
     */
    public void callListeners(Observable observable) {
        try {
            locked = true;
            for (InvalidationListener listener : listeners) {
                listener.invalidated(observable);
            }
        } finally {
            locked = false;
        }

        if (lockedListeners != null) {
            listeners.clear();
            listeners.addAll(lockedListeners);
            lockedListeners = null;
        }
    }

}
