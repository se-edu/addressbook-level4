package seedu.address.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.person.ReadOnlyPerson;

public class TestPersonCard extends PersonCard {

    public TestPersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(person, displayedIndex);
    }

    public Label getNameLabel() {
        return name;
    }

    public Label getIdLabel() {
        return id;
    }

    public Label getPhoneLabel() {
        return phone;
    }

    public Label getAddressLabel() {
        return address;
    }

    public Label getEmailLabel() {
        return email;
    }

    public FlowPane getTagPane() {
        return tags;
    }
}
