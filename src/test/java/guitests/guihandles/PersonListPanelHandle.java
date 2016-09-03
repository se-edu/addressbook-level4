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
import javafx.stage.Window;
import seedu.address.TestApp;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TestUtil;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the person list.
 */
public class PersonListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";
    private static final String COMMAND_INPUT_FIELD_ID = "#commandInput";

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

    public void enterCommandAndApply(String command) {
        typeTextField(COMMAND_INPUT_FIELD_ID, command);
        pressEnter();
    }

    public String getCommandInput() {
        return getTextFieldText(COMMAND_INPUT_FIELD_ID);
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

}
