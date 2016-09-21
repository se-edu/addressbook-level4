package seedu.address.ui;

import javafx.scene.control.ListCell;
import seedu.address.controller.PersonCard;
import seedu.address.model.person.ReadOnlyPerson;

public class PersonListViewCell extends ListCell<ReadOnlyPerson> {

    public PersonListViewCell() {
    }

    @Override
    protected void updateItem(ReadOnlyPerson person, boolean empty) {
        super.updateItem(person, empty);

        if (empty || person == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new PersonCard(person, getIndex() + 1).getLayout());
        }
    }
}
