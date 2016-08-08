package guitests.guihandles;


import address.TestApp;
import address.model.datatypes.person.Person;
import address.model.datatypes.person.ReadOnlyPerson;
import address.testutil.TestUtil;
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
import javafx.stage.Window;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the person list.
 */
public class PersonListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";
    private static final String FILTER_FIELD_ID = "#filterField";
    private static final String PERSON_LIST_VIEW_ID = "#personListView";

    public static final String DELETE_CONTEXT_MENU_ITEM_FIELD_ID = "#deleteMenuItem";

    public PersonListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public boolean contains(String firstName, String lastName) {
        //TODO: should be checking if the graphical node is displaying the names.
        return getListView().getItems().stream().anyMatch(p -> p.hasName(firstName, lastName));
    }

    public boolean contains(int id) {
        //TODO: should be checking if the graphical node is displaying the names.
        return getListView().getItems().stream().anyMatch(p -> p.getId() == id);
    }

    public boolean contains(Person person) {
        return this.getPersonCardHandle(person) != null;
    }

    public boolean isSelected(String firstName, String lastName) {
        return getSelectedPersons().stream().filter(p -> p.hasName(firstName, lastName)).findAny().isPresent();
    }

    public boolean isOnlySelected(Person person) {
        return isOnlySelected(person.getFirstName(), person.getLastName());
    }

    public boolean isOnlySelected(String firstName, String lastName) {
        return getSelectedPersons().stream().filter(p -> p.hasName(firstName, lastName)).count() == 1;
    }

    public boolean isSelected(Person person) {
        return this.isSelected(person.getFirstName(), person.getLastName());
    }

    public List<ReadOnlyPerson> getSelectedPersons() {
        ListView<ReadOnlyPerson> personList = getListView();
        return personList.getSelectionModel().getSelectedItems();
    }

    public ReadOnlyPerson getFirstSelectedPerson() {
        return this.getSelectedPersons().get(0);
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

    public void use_LIST_JUMP_TO_INDEX_SHORTCUT(int index) {
        switch (index) {
            case 1:
                guiRobot.push(TestUtil.scrub(new KeyCode[]{KeyCode.SHORTCUT, KeyCode.DIGIT1}));
                break;
            case 2:
                guiRobot.push(TestUtil.scrub(new KeyCode[]{KeyCode.SHORTCUT, KeyCode.DIGIT2}));
                break;
            case 3:
                guiRobot.push(TestUtil.scrub(new KeyCode[]{KeyCode.SHORTCUT, KeyCode.DIGIT3}));
                break;
            case 4:
                guiRobot.push(TestUtil.scrub(new KeyCode[]{KeyCode.SHORTCUT, KeyCode.DIGIT4}));
                break;
            case 5:
                guiRobot.push(TestUtil.scrub(new KeyCode[]{KeyCode.SHORTCUT, KeyCode.DIGIT5}));
                break;
            case 6:
                guiRobot.push(TestUtil.scrub(new KeyCode[]{KeyCode.SHORTCUT, KeyCode.DIGIT6}));
                break;
            case 7:
                guiRobot.push(TestUtil.scrub(new KeyCode[]{KeyCode.SHORTCUT, KeyCode.DIGIT7}));
                break;
            case 8:
                guiRobot.push(TestUtil.scrub(new KeyCode[]{KeyCode.SHORTCUT, KeyCode.DIGIT8}));
                break;
            case 9:
                guiRobot.push(TestUtil.scrub(new KeyCode[]{KeyCode.SHORTCUT, KeyCode.DIGIT9}));
                break;
            default:
                throw new RuntimeException("Unsupported shortcut");
        }
    }

    /**
     * Navigate the listview to display and select the person.
     * @param person
     */
    public PersonCardHandle navigateToPerson(Person person) {
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

    /**
     * Checks if the error dialog window for no selected person is shown
     * @return
     */
    public boolean isNoSelectedPersonDialogShown() {
        try{
            Window window = guiRobot.window("Invalid Selection");
            return window != null && window.isShowing();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickOnPerson(Person person) {
        guiRobot.clickOn(person.getFirstName());
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

    public void enterFilterAndApply(String filterText) {
        typeTextField(FILTER_FIELD_ID, filterText);
        pressEnter();
    }

    public String getFilterText() {
        return getTextFieldText(FILTER_FIELD_ID);
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

    /**
     * Returns true if the {@code persons} appear as a sub list (in that order) in the panel.
     */
    public boolean containsInOrder(Person... persons) {
        assert persons.length >= 2;
        int indexOfFirstPerson = getPersonIndex(persons[0]);
        if (indexOfFirstPerson == NOT_FOUND) return false;
        return containsInOrder(indexOfFirstPerson, persons);
    }

    public void clearSelection() {
        getListView().getSelectionModel().clearSelection();
    }

    /**
     * Returns true if the {@code persons} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, Person... persons) {
        List<ReadOnlyPerson> personsInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + persons.length > personsInList.size()){
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < persons.length; i++) {
            if (!personsInList.get(startPosition + i).fullName().equals(persons[i].fullName())){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found in the list.
     */
    public int getPersonIndex(Person targetPerson) {
        List<ReadOnlyPerson> personsInList = getListView().getItems();
        for (int i = 0; i < personsInList.size(); i++) {
            if(personsInList.get(i).getFirstName().equals(targetPerson.getFirstName())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    public PersonCardHandle getPersonCardHandle(int index) {
        return getPersonCardHandle(new Person(getListView().getItems().get(index)));
    }

    /**
     * Checks if the list is showing the person details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param persons A list of person in the correct order.
     * @return
     */
    public boolean isListMatching(int startPosition, Person... persons) throws IllegalArgumentException {
        if (persons.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size not matching\n" +
                    "Expect " + (getListView().getItems().size()) + "persons");
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

    public boolean containsListOnly(List<ReadOnlyPerson> personList) {
        ReadOnlyPerson[] personArray = new Person[personList.size()];
        personList.toArray(personArray);
        return containsListOnly(personArray);
    }

    public boolean containsListOnly(ReadOnlyPerson... persons) {
        if (persons.length != getListView().getItems().size()) return false;

        for (ReadOnlyPerson person : persons) {
            if (!contains(person.getId())) return false;
        }

        return true;
    }

    /**
     * Checks if the list is showing the person details correctly and in correct order.
     * @param persons A list of person in the correct order.
     * @return
     */
    public boolean isListMatching(Person... persons) {
        System.out.println("person length: " + persons.length);
        return this.isListMatching(0, persons);
    }

    public PersonCardHandle getPersonCardHandle(Person person) {
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

}
