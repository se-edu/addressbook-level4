package seedu.address.ui;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ObservablePerson.PersonProperty;
import seedu.address.model.person.ReadOnlyPerson;

public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final PersonProperty[] personProperties = PersonProperty.values();

    /**
     * A map to contain all the change listeners associated to a Person object.
     * All values are weakly referenced to allow for garbage collection if the Person is deleted.
     */
    private static final ListMultimap<ReadOnlyPerson, WeakReference<ChangeListener<Object>>> LISTENER_MAP =
            ArrayListMultimap.create();

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

    private final Map<PersonProperty, Consumer<ReadOnlyPerson>> personPropertyConsumerMap;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        name.setText(person.getName().fullName);
        id.setText(displayedIndex + ". ");
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        initTags(person);

        personPropertyConsumerMap = Maps.newEnumMap(PersonProperty.class);

        populatePersonPropertyConsumerMap();
        bindListeners(person);
    }

    private void initTags(ReadOnlyPerson person) {
        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void populatePersonPropertyConsumerMap() {
        personPropertyConsumerMap.put(PersonProperty.ADDRESS, (person) -> address.setText(person.getAddress().value));
        personPropertyConsumerMap.put(PersonProperty.EMAIL, (person) -> email.setText(person.getEmail().value));
        personPropertyConsumerMap.put(PersonProperty.NAME, (person) -> name.setText(person.getName().fullName));
        personPropertyConsumerMap.put(PersonProperty.PHONE, (person) -> phone.setText(person.getPhone().value));
        personPropertyConsumerMap.put(PersonProperty.TAG, this::initTags);
    }

    private void bindListeners(ReadOnlyPerson person) {
        for (PersonProperty property : personProperties) {
            removeListenersForPerson(person);

            ChangeListener<Object> changeListener =
                (o, oldVal, newVal) -> personPropertyConsumerMap.get(property).accept(person);

            person.registerListener(property, changeListener);

            LISTENER_MAP.put(person, new WeakReference<>(changeListener));
        }
    }

    private void removeListenersForPerson (ReadOnlyPerson person) {
        if (!LISTENER_MAP.containsKey(person)) {
            // If there is no listeners currently registered for this person, exit.
            return;
        }

        List<WeakReference<ChangeListener<Object>>> listenerReferenceList = LISTENER_MAP.get(person);

        if (listenerReferenceList.size() != personProperties.length) {
            // Don't remove listeners for this Person before all are registered.
            return;
        }

        // Remove listeners in the same order they are registered (guaranteed by the enum list)
        for (int i = 0; i < personProperties.length; i++) {
            person.removeListener(personProperties[i], listenerReferenceList.get(i).get());
        }

        LISTENER_MAP.removeAll(person);
    }
}
