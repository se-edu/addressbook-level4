package guitests.guihandles;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the person list.
 */
public class PersonListPanelHandle extends GuiHandle {

    public static final String CARD_PANE_ID = "#cardPane";

    private static final String PERSON_LIST_VIEW_ID = "#personListView";

    public PersonListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Returns the selected person in the list view. Only a maximum of 1 item can be selected at any time.
     */
    public Optional<ReadOnlyPerson> getSelectedPerson() {
        List<ReadOnlyPerson> personList = getListView().getSelectionModel().getSelectedItems();

        if (personList.size() > 1) {
            throw new AssertionError("Person list size expected 0 or 1.");
        }

        return personList.isEmpty() ? Optional.empty() : Optional.of(personList.get(0));
    }

    public ListView<ReadOnlyPerson> getListView() {
        return getNode(PERSON_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(ReadOnlyPerson... persons) {
        return this.isListMatching(0, persons);
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyPerson... persons) {
        List<ReadOnlyPerson> personList = getListView().getItems();

        if (personList.size() != persons.length + startPosition) {
            throw new IllegalArgumentException("List size mismatched\n"
                    + "Expected " + personList.size() + " persons");
        }
        return cardsAndPersonsMatchInOrder(startPosition, persons);
    }

    /**
     * Returns true if each person in {@code persons} matches the card at the exact same position,
     * beginning from the card at {@code startPosition}.
     */
    private boolean cardsAndPersonsMatchInOrder(int startPosition, ReadOnlyPerson... persons) {
        for (int i = 0; i < persons.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.pauseForHuman();
            if (!TestUtil.compareCardAndPerson(getPersonCardHandle(startPosition + i), persons[i])) {
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

    public PersonCardHandle navigateToPerson(String name) throws PersonNotFoundException {
        final Optional<ReadOnlyPerson> person = getListView().getItems().stream()
                                                    .filter(p -> p.getName().fullName.equals(name))
                                                    .findAny();
        if (!person.isPresent()) {
            throw new PersonNotFoundException();
        }

        return navigateToPerson(person.get());
    }

    /**
     * Navigates the listview to display and select the person.
     */
    private PersonCardHandle navigateToPerson(ReadOnlyPerson person) throws PersonNotFoundException {
        if (!getListView().getItems().contains(person)) {
            throw new PersonNotFoundException();
        }

        guiRobot.interact(() -> {
            getListView().scrollTo(person);
            getListView().getSelectionModel().select(person);
        });
        return getPersonCardHandle(person);
    }

    /**
     * Gets a person from the list by index
     */
    public ReadOnlyPerson getPerson(int index) {
        return getListView().getItems().get(index);
    }

    public PersonCardHandle getPersonCardHandle(int index) {
        return getPersonCardHandle(new Person(getListView().getItems().get(index)));
    }

    public PersonCardHandle getPersonCardHandle(ReadOnlyPerson person) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> personCardNode = nodes.stream()
                .filter(n -> new PersonCardHandle(guiRobot, primaryStage, n).isSamePerson(person))
                .findFirst();
        if (personCardNode.isPresent()) {
            return new PersonCardHandle(guiRobot, primaryStage, personCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfPeople() {
        return getListView().getItems().size();
    }
}
