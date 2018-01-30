package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.person.Person;

/**
 * Provides a handle for {@code PersonListPanel} containing the list of {@code PersonCard}.
 */
public class PersonListPanelHandle extends NodeHandle<ListView<Person>> {
    public static final String PERSON_LIST_VIEW_ID = "#personListView";

    private Optional<Person> lastRememberedSelectedPersonCard;

    public PersonListPanelHandle(ListView<Person> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public Person getSelectedPerson() {
        List<Person> personList = getRootNode().getSelectionModel().getSelectedItems();

        if (personList.size() != 1) {
            throw new AssertionError("Person list size expected 1.");
        }

        return personList.get(0);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Person> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the {@code person}.
     */
    public void navigateToCard(Person person) {
        if (!getRootNode().getItems().contains(person)) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(person);
            getRootNode().getSelectionModel().select(person);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code PersonCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the person at the specified {@code index} in the list.
     */
    public Person getPerson(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Remembers the selected {@code PersonCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<Person> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<Person> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPersonCard.isPresent();
        } else {
            return !lastRememberedSelectedPersonCard.isPresent()
                    || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
