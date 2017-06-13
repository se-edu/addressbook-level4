package guitests.guihandles;


import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.ui.PersonCard;

/**
 * Provides a handle for {@code PersonListPanel} containing the list of {@code PersonCard}.
 */
public class PersonListPanelHandle extends NodeHandle<ListView<PersonCard>> {
    public static final String PERSON_LIST_VIEW_ID = "#personListView";

    public PersonListPanelHandle(ListView<PersonCard> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns the selected {@code PersonCard} in the list view. A maximum of 1 item can be selected at any time.
     */
    public Optional<PersonCard> getSelectedCard() {
        List<PersonCard> personList = getRootNode().getSelectionModel().getSelectedItems();

        if (personList.size() > 1) {
            throw new AssertionError("Person list size expected 0 or 1.");
        }

        return personList.isEmpty() ? Optional.empty() : Optional.of(personList.get(0));
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(ReadOnlyPerson... persons) throws PersonNotFoundException {
        List<PersonCard> personList = getRootNode().getItems();
        checkArgument(personList.size() == persons.length,
                "List size mismatched\nExpected " + personList.size() + " persons");

        for (int i = 0; i < persons.length; i++) {
            final int scrollTo = i; // lambda expression needs i to be final
            guiRobot.interact(() -> getRootNode().scrollTo(scrollTo));
            guiRobot.pauseForHuman();
            if (!getPersonCardHandle(i).belongsTo(persons[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public void navigateToCard(ReadOnlyPerson person) throws PersonNotFoundException {
        List<PersonCard> cards = getRootNode().getItems();
        Optional<PersonCard> matchingCard = cards.stream().filter(card -> card.person.equals(person)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new PersonNotFoundException();
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the {@code PersonCard} at the specified {@code index} in the list.
     */
    public PersonCard getCard(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns the person card handle of a person associated with the {@code index} in the list.
     */
    private PersonCardHandle getPersonCardHandle(int index) throws PersonNotFoundException {
        return getPersonCardHandle(getCard(index).person);
    }

    /**
     * Returns the {@code PersonCardHandle} of the specified {@code person} in the list.
     */
    public PersonCardHandle getPersonCardHandle(ReadOnlyPerson person) throws PersonNotFoundException {
        Optional<PersonCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.person.equals(person))
                .map(card -> new PersonCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(PersonNotFoundException::new);
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
