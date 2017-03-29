package seedu.address.ui;

import java.util.function.Function;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.MappedList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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
    private FlowPane tags;

    private ObservableList<Node> workingList = FXCollections.observableArrayList();

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        name.setText(person.getName().fullName);
        id.setText(displayedIndex + ". ");
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

        bindListenersForLabels(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListenersForLabels(ReadOnlyPerson person) {
        name.textProperty().bind(person.nameProperty());
        phone.textProperty().bind(person.phoneProperty());
        address.textProperty().bind(person.addressProperty());
        email.textProperty().bind(person.emailProperty());

        bindTagListener(person);
    }

    private void bindTagListener(ReadOnlyPerson person) {
        addListenerToWorkingList();

        Function<Tag, Node> mapper = tag -> new Label(tag.tagName);
        MappedList<Node, Tag> labelTagMappedList = new MappedList<>(person.tagProperty(), mapper);

        Bindings.bindContent(workingList, labelTagMappedList);
    }

    private void addListenerToWorkingList() {
        workingList.addListener((ListChangeListener.Change<? extends Node> c) -> {
            tags.getChildren().clear();
            c.getList().forEach(label -> tags.getChildren().add(label));
        });
    }
}
