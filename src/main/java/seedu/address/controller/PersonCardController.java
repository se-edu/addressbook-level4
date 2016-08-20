package seedu.address.controller;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import seedu.address.model.person.ReadOnlyPerson;

import java.io.IOException;

public class PersonCardController {

    @FXML
    private HBox cardPane;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label address;
    @FXML
    private Label birthday;
    @FXML
    private Label tags;

    private ReadOnlyPerson person;
    private StringProperty idTooltipString = new SimpleStringProperty("");

    public PersonCardController(ReadOnlyPerson person) {
        this.person = person;

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
        firstName.setText(person.getName().fullName);
        address.setText(person.getAddress().value);
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
