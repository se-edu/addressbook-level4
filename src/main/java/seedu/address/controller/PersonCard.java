package seedu.address.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.person.ReadOnlyPerson;

import java.io.IOException;

public class PersonCard {

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label tags;

    private ReadOnlyPerson person;
    private int displayedIndex;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        this.person = person;
        this.displayedIndex = displayedIndex;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PersonListCard.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        name.setText(person.getName().fullName);
        id.setText(displayedIndex + ". ");
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        tags.setText(person.tagsString());

    }

    public HBox getLayout() {
        return cardPane;
    }

    public static String getAddressString(String street, String city, String postalCode) {
        StringBuilder sb = new StringBuilder();
        if (street.length() > 0) {
            sb.append(street).append(System.lineSeparator());
        }
        if (city.length() > 0) {
            sb.append(city).append(System.lineSeparator());
        }
        if (postalCode.length() > 0) {
            sb.append(postalCode);
        }
        return sb.toString();
    }
}
