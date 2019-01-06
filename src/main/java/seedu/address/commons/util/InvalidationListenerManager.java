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
     * Calls {@link InvalidationListener#invalidated(Observable)} on all added listeners.
     * Any modifications to the listeners list during the invocation of this method
     * will only take effect on the next invocation of this method.
     *
     * @param observable The {@code Observable} that became invalid.
     */
    public void callListeners(Observable observable) {
        // Make a copy of listeners such that any modifications to the listeners list during
        // the invocation of this method will only take effect on the next invocation of this method.
        ArrayList<InvalidationListener> listenersCopy = new ArrayList<>(listeners);

        for (InvalidationListener listener : listenersCopy) {
            listener.invalidated(observable);
        }
    }

    /**
     * Adds {@code listener} to the list of listeners.
     * If the same listener is added more that once, then it will be notified more than once.
     */
    public void addListener(InvalidationListener listener) {
        requireNonNull(listener);
        listeners.add(listener);
    }

    /**
     * Removes {@code listener} from the list of listeners.
     * If the given listener was not previously added, then this method call is a no-op.
     * If the given listener was added more than once, then only the first occurrence in the list will be removed.
     */
    public void removeListener(InvalidationListener listener) {
        requireNonNull(listener);
        listeners.remove(listener);
    }

}
