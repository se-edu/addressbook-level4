package seedu.address.controller;

import seedu.address.model.datatypes.person.ReadOnlyPerson;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class PersonCardController extends UiController {

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
        bindDisplayedPersonData();
        initIdTooltip();
    }

    private void bindDisplayedPersonData() {

        firstName.textProperty().bind(person.firstNameProperty());
        lastName.textProperty().bind(person.lastNameProperty());

        address.textProperty().bind(new StringBinding() {
            {
                bind(person.streetProperty());
                bind(person.postalCodeProperty());
                bind(person.cityProperty());
            }
            @Override
            protected String computeValue() {
                return getAddressString(person.getStreet(), person.getCity(), person.getPostalCode());
            }
        });
        birthday.textProperty().bind(new StringBinding() {
            {
                bind(person.birthdayProperty()); //Bind property at instance initializer
            }
            @Override
            protected String computeValue() {
                return person.birthdayString().length() > 0 ? "DOB: " + person.birthdayString() : "";
            }
        });
        tags.textProperty().bind(new StringBinding() {
            {
                bind(person.getObservableTagList()); //Bind property at instance initializer
            }
            @Override
            protected String computeValue() {
                return person.tagsString().equals("") ? "" : " [ " + person.tagsString() + " ]";
            }
        });

    }

    private void initIdTooltip() {
        Tooltip tp = new Tooltip();
        tp.textProperty().bind(idTooltipString);
        firstName.setTooltip(tp);
        lastName.setTooltip(tp);
        idTooltipString.set(person.idString());
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
