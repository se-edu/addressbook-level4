package guitests.guihandles;


import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.PickResult;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the person list.
 */
public class PersonListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String PERSON_LIST_VIEW_ID = "#personListView";

    public static final String DELETE_CONTEXT_MENU_ITEM_FIELD_ID = "#deleteMenuItem";

    public PersonListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public boolean contains(Person person) {
        return this.getPersonCardHandle(person) != null;
    }


    public List<ReadOnlyPerson> getSelectedPersons() {
        ListView<ReadOnlyPerson> personList = getListView();
        return personList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyPerson> getListView() {
        return (ListView<ReadOnlyPerson>) getNode(PERSON_LIST_VIEW_ID);
    }

    /**
     * Clicks on the middle of the Listview.
     * In order for headfull testing to work in travis ci, listview needs to be clicked before firing hot keys.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Fires ContextMenuEvent which shows a contextmenu in the middle of the Listview.
     */
    private void fireContextMenuEvent() {
        Point2D screenPoint = TestUtil.getScreenMidPoint(getListView());
        Point2D scenePoint = TestUtil.getSceneMidPoint(getListView());
        Event event = new ContextMenuEvent(ContextMenuEvent.CONTEXT_MENU_REQUESTED, scenePoint.getX(), scenePoint.getY(),
                screenPoint.getX(), screenPoint.getY(), false,
                new PickResult(getListView(), screenPoint.getX(), screenPoint.getY()));
        guiRobot.interact(() -> Event.fireEvent(getListView(), event));
    }

    /**
     * Checks if the list is showing the person details correctly and in correct order.
     * @param persons A list of person in the correct order.
     * @return
     */
    public boolean isListMatching(ReadOnlyPerson... persons) {
        return this.isListMatching(0, persons);
    }

    /**
     * Returns true if the {@code persons} appear as a sub list (in that order) in the panel.
     */
    public boolean containsInOrder(ReadOnlyPerson... persons) {
        assert persons.length >= 2;
        int indexOfFirstPerson = getPersonIndex(persons[0]);
        if (indexOfFirstPerson == NOT_FOUND) return false;
        return containsInOrder(indexOfFirstPerson, persons);
    }

    /**
     * Returns true if the {@code persons} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyPerson... persons) {
        List<ReadOnlyPerson> personsInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + persons.length > personsInList.size()){
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < persons.length; i++) {
            if (!personsInList.get(startPosition + i).getName().fullName.equals(persons[i].getName().fullName)){
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the list is showing the person details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param persons A list of person in the correct order.
     * @return
     */
    public boolean isListMatching(int startPosition, ReadOnlyPerson... persons) throws IllegalArgumentException {
        if (persons.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size not matching\n" +
                    "Expect " + (getListView().getItems().size() - 1) + "persons");
        }
        assertTrue(this.containsInOrder(startPosition, persons));
        for (int i = 0; i < persons.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndPerson(getPersonCardHandle(startPosition + i), persons[i])) {
                return false;
            }
        }
        return true;
    }


    public PersonCardHandle navigateToPerson(String name) {
        ObservableList<ReadOnlyPerson> items = getListView().getItems();
        final Optional<ReadOnlyPerson> person = getListView().getItems().stream().filter(p -> p.getName().fullName.equals(name)).findAny();
        if (!person.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToPerson(person.get());
    }

    /**
     * Navigate the listview to display and select the person.
     * @param person
     */
    public PersonCardHandle navigateToPerson(ReadOnlyPerson person) {
        int index = getPersonIndex(person);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getPersonCardHandle(person);
    }

    public void navigateUp() {
        guiRobot.push(KeyCode.UP);
    }

    public void navigateDown() {
        guiRobot.push(KeyCode.DOWN);
    }

    public void clickOnPerson(Person person) {
        guiRobot.clickOn(person.getName().fullName);
    }

    /**
     * Right click on Person to show context menu.
     * @param person
     */
    public PersonListPanelHandle rightClickOnPerson(Person person) {
        //Instead of using guiRobot.rightCickOn(), We will be firing off contextmenu request manually.
        //As there is a bug in monocle that doesn't show contextmenu by actual right clicking.
        //Refer to https://github.com/TestFX/Monocle/issues/12
        clickOnPerson(person);
        fireContextMenuEvent();
        guiRobot.sleep(100);
        assertTrue(isContextMenuShown());
        return this;
    }

    private boolean isContextMenuShown() {
        return isNodePresent(DELETE_CONTEXT_MENU_ITEM_FIELD_ID);
    }

    private boolean isNodePresent(String fieldId) {
        try {
            getNode(fieldId);
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }


    public void clickOnPerson(String personName) {
        guiRobot.clickOn(personName);
    }



    private void clickOnMultipleNames(List<String> listOfNames) {
        guiRobot.press(KeyCode.SHORTCUT);
        listOfNames.forEach(guiRobot::clickOn);
        guiRobot.release(KeyCode.SHORTCUT);
    }

    /**
     * Attempts to select multiple person cards
     *
     * Currently, this is done programmatically since multiple selection has problems in headless mode
     *
     * @param listOfPersons
     */
    public void selectMultiplePersons(List<Person> listOfPersons) {
        listOfPersons.stream().forEach(vPerson -> getListView().getSelectionModel().select(vPerson));
    }


    public void clearSelection() {
        getListView().getSelectionModel().clearSelection();
    }

    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found in the list.
     */
    public int getPersonIndex(ReadOnlyPerson targetPerson) {
        List<ReadOnlyPerson> personsInList = getListView().getItems();
        for (int i = 0; i < personsInList.size(); i++) {
            if(personsInList.get(i).getName().equals(targetPerson.getName())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a person from the list by index
     * @param index
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

    /**
     * Select cards
     * @param persons
     * @return
     */
    public List<PersonCardHandle> selectCards(Person... persons) {
        guiRobot.press(KeyCode.SHORTCUT);
        for (Person person: persons) {
            guiRobot.interact(() -> {
                getListView().scrollTo(getPersonIndex(person));
                guiRobot.sleep(150);
                getListView().getSelectionModel().select(getPersonIndex(person));
            });
        }
        guiRobot.release(KeyCode.SHORTCUT);
        return getSelectedCards();
    }

    public PersonCardHandle selectCard(Person person) {
        guiRobot.interact(() -> getListView().scrollTo(getPersonIndex(person)));
        guiRobot.sleep(150);
        clickOnPerson(person);
        guiRobot.sleep(500);
        return getPersonCardHandle(person);
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public List<PersonCardHandle> getSelectedCards() {
        ObservableList<ReadOnlyPerson> persons = getListView().getSelectionModel().getSelectedItems();
        return persons.stream().map(p -> getPersonCardHandle(new Person(p)))
                               .collect(Collectors.toCollection(ArrayList::new));
    }

    public int getSelectedCardSize() {
        return getListView().getSelectionModel().getSelectedItems().size();
    }

    public int getNumberOfPeople() {
        return getListView().getItems().size();
    }
}
