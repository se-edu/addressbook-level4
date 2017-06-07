package guitests.guihandles;


import static guitests.GuiRobotUtil.MEDIUM_WAIT;
import static guitests.GuiRobotUtil.SHORT_WAIT;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TestUtil;

/**
 * Provides a handle for {@code PersonListPanel} containing the list of {@code PersonCard}.
 */
public class PersonListPanelHandle extends NodeHandle {

    private static final String CARD_PANE_ID = "#cardPane";
    private static final String PERSON_LIST_VIEW_ID = "#personListView";

    public PersonListPanelHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getNode(PERSON_LIST_VIEW_ID));
    }

    private ListView<ReadOnlyPerson> getListView() {
        return (ListView<ReadOnlyPerson>) getRootNode();
    }

    public List<ReadOnlyPerson> getSelectedPersons() {
        ListView<ReadOnlyPerson> personList = getListView();
        return personList.getSelectionModel().getSelectedItems();
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
        assertTrue(this.containsInOrder(startPosition, persons));
        for (int i = 0; i < persons.length; i++) {
            final int scrollTo = i + startPosition;
            GUI_ROBOT.interact(() -> getListView().scrollTo(scrollTo));
            GUI_ROBOT.pauseForHuman(SHORT_WAIT);
            if (!TestUtil.compareCardAndPerson(getPersonCardHandle(startPosition + i), persons[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the {@code persons} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyPerson... persons) {
        List<ReadOnlyPerson> personsToCompare = getListView().getItems().subList(startPosition,
                getListView().getItems().size());

        if (personsToCompare.size() != persons.length) {
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < persons.length; i++) {
            if (!personsToCompare.get(startPosition + i).getName().fullName.equals(persons[i].getName().fullName)) {
                return false;
            }
        }

        return true;
    }

    public PersonCardHandle navigateToPerson(String name) throws PersonNotFoundException, IllegalValueException {
        GUI_ROBOT.pauseForHuman(MEDIUM_WAIT);
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
    public PersonCardHandle navigateToPerson(ReadOnlyPerson person) throws PersonNotFoundException {
        if (!getListView().getItems().contains(person)) {
            throw new PersonNotFoundException();
        }

        GUI_ROBOT.interact(() -> {
            getListView().scrollTo(person);
            GUI_ROBOT.pauseForHuman(SHORT_WAIT);
            getListView().getSelectionModel().select(person);
        });

        GUI_ROBOT.pauseForHuman(SHORT_WAIT);
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
                .filter(n -> new PersonCardHandle(n).belongsTo(person))
                .findFirst();
        if (personCardNode.isPresent()) {
            return new PersonCardHandle(personCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return GUI_ROBOT.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfPeople() {
        return getListView().getItems().size();
    }
}
