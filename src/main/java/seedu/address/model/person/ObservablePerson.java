package seedu.address.model.person;

import javafx.beans.value.ChangeListener;

/**
 * An observable interface for a Person in the addressbook.
 */
public interface ObservablePerson {

    /**
     * Registers the specified listener to the given {@code PersonProperty} which will be
     * notified whenever the value of the underlying attribute changes
     */
    void registerListener(PersonProperty property, ChangeListener<Object> listener);

    /**
     * Removes the specified listener to the given {@code PersonProperty} from the list
     * of listeners to be notified whenever the underlying attribute changes
     */
    void removeListener(PersonProperty property, ChangeListener<Object> listener);

    enum PersonProperty {
        ADDRESS, EMAIL, NAME, PHONE, TAG
    }
}
