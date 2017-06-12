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
import seedu.address.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the person list.
 */
public class PersonListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
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
    public boolean isListMatching(int startPosition, ReadOnlyPerson... persons) throws IllegalArgumentException {
        if (persons.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n"
                    + "Expected " + (getListView().getItems().size() - 1) + " persons");
        }
        return containsInOrder(startPosition, persons) && cardsAndPersonsMatch(startPosition, persons);
    }

    /**
     * Returns true if each person in {@code persons} matches the card at the exact same position,
     * beginning from the card at {@code startPosition}.
     */
    private boolean cardsAndPersonsMatch(int startPosition, ReadOnlyPerson... persons) {
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

    /**
     * Returns true if the {@code persons} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyPerson... persons) {
        List<ReadOnlyPerson> personsInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + persons.length > personsInList.size()) {
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < persons.length; i++) {
            if (!personsInList.get(startPosition + i).getName().fullName.equals(persons[i].getName().fullName)) {
                return false;
            }
        }

        return true;
    }

    public PersonCardHandle navigateToPerson(String name) {
        guiRobot.pauseForHuman();
        final Optional<ReadOnlyPerson> person = getListView().getItems().stream()
                                                    .filter(p -> p.getName().fullName.equals(name))
                                                    .findAny();
        if (!person.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToPerson(person.get());
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public PersonCardHandle navigateToPerson(ReadOnlyPerson person) {
        int index = getPersonIndex(person);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.pauseForHuman();
            getListView().getSelectionModel().select(index);
        });
        guiRobot.pauseForHuman();
        return getPersonCardHandle(person);
    }


    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found in the list.
     */
    public int getPersonIndex(ReadOnlyPerson targetPerson) {
        List<ReadOnlyPerson> personsInList = getListView().getItems();
        for (int i = 0; i < personsInList.size(); i++) {
            if (personsInList.get(i).getName().equals(targetPerson.getName())) {
                return i;
            }
        }
        return NOT_FOUND;
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
