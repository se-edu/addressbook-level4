package seedu.address.model.person;

import javafx.beans.property.ReadOnlyProperty;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.tag.Tag;

/**
 * An observable interface for a Person in the addressbook.
 */
public interface ObservablePerson {

    ReadOnlyProperty<String> nameProperty();
    ReadOnlyProperty<String> addressProperty();
    ReadOnlyProperty<String> phoneProperty();
    ReadOnlyProperty<String> emailProperty();

    /**
     * Returns an unmodifiable observable list of the underlying tag list of this {@code Person}
     */
    UnmodifiableObservableList<Tag> tagProperty();
}
