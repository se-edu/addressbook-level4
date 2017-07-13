package guitests.guihandles;


import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.TestApp;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TestUtil;
import seedu.address.ui.PersonCard;

/**
 * Provides a handle for the panel containing the person list.
 */
public class PersonListPanelHandle extends GuiHandle {

    private static final String CARD_PANE_ID = "#cardPane";

    private static final String PERSON_LIST_VIEW_ID = "#personListView";

    public PersonListPanelHandle() {
        super(TestApp.APP_TITLE);
    }

    /**
     * Returns the selected person in the list view. A maximum of 1 item can be selected at any time.
     */
    public Optional<ReadOnlyPerson> getSelectedPerson() {
        List<PersonCard> personList = getListView().getSelectionModel().getSelectedItems();

        if (personList.size() > 1) {
            throw new AssertionError("Person list size expected 0 or 1.");
        }

        return personList.isEmpty() ? Optional.empty() : Optional.of(personList.get(0).person);
    }

    private ListView<PersonCard> getListView() {
        return getNode(PERSON_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(ReadOnlyPerson... persons) throws PersonNotFoundException {
        List<PersonCard> personList = getListView().getItems();
        checkArgument(personList.size() == persons.length,
                "List size mismatched\nExpected " + personList.size() + " persons");

        for (int i = 0; i < persons.length; i++) {
            final int scrollTo = i; // lambda expression needs i to be final
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.pauseForHuman();
            if (!TestUtil.compareCardAndPerson(getPersonCardHandle(i), persons[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public void navigateToPerson(ReadOnlyPerson person) throws PersonNotFoundException {
        List<PersonCard> cards = getListView().getItems();
        Optional<PersonCard> matchingCard = cards.stream().filter(card -> card.person.equals(person)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new PersonNotFoundException();
        }

        guiRobot.interact(() -> {
            getListView().scrollTo(matchingCard.get());
            getListView().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Gets a person from the list by index
     */
    public ReadOnlyPerson getPerson(int index) {
        return getListView().getItems().get(index).person;
    }

    private PersonCardHandle getPersonCardHandle(int index) throws PersonNotFoundException {
        return getPersonCardHandle(getPerson(index));
    }

    public PersonCardHandle getPersonCardHandle(ReadOnlyPerson person) throws PersonNotFoundException {
        if (getListView().getItems().stream().map(card -> card.person)
                .noneMatch(cardPerson -> cardPerson.equals(person))) {
            throw new PersonNotFoundException();
        }

        Set<Node> nodes = getAllCardNodes();
        Optional<PersonCardHandle> personCardNode = nodes.stream()
                .map(PersonCardHandle::new)
                .filter(handle -> handle.isSamePerson(person))
                .findFirst();

        // post-condition: since we verified at the start that the person exist in the list,
        // it must have a corresponding person card, so the person card's node must exist
        assert personCardNode.isPresent();

        return personCardNode.get();
    }

    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfPeople() {
        return getListView().getItems().size();
    }
}
