package guitests.guihandles;


import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the person list.
 */
public class PersonListPanelHandle extends NodeHandle {

    public static final int NOT_FOUND = -1;

    private static final String CARD_PANE_ID = "#cardPane";
    private static final String PERSON_LIST_VIEW_ID = "#personListView";

    public PersonListPanelHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getChildNode(PERSON_LIST_VIEW_ID));
    }

    /**
     * Returns all selected {@code ReadOnlyPerson} in the list.
     */
    public List<ReadOnlyPerson> getSelectedPersons() {
        ListView<ReadOnlyPerson> personList = getListView();
        return personList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyPerson> getListView() {
        return (ListView<ReadOnlyPerson>) getNode();
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
            GUI_ROBOT.pauseForHuman();
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

    /**
     * Scrolls the list such that a {@code ReadOnlyPerson} with {@code name} is visible at the top of the list.
     */
    public PersonCardHandle navigateToPerson(String name) {
        GUI_ROBOT.pauseForHuman();
        final Optional<ReadOnlyPerson> person = getListView().getItems().stream()
                                                    .filter(p -> p.getName().fullName.equals(name))
                                                    .findAny();
        if (!person.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToPerson(person.get());
    }

    /**
     * Scrolls the list such that {@code ReadOnlyPerson} is visible at the top of the list.
     */
    public PersonCardHandle navigateToPerson(ReadOnlyPerson person) {
        GUI_ROBOT.interact(() -> {
            getListView().scrollTo(person);
            getListView().getSelectionModel().select(person);
        });
        GUI_ROBOT.pauseForHuman();
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
     * Returns the person associated with the {@code index} from the list.
     */
    public ReadOnlyPerson getPerson(int index) {
        return getListView().getItems().get(index);
    }

    /**
     * Returns the person card handle of a person associated with the {@code index} from the list.
     */
    public PersonCardHandle getPersonCardHandle(int index) {
        return getPersonCardHandle(new Person(getListView().getItems().get(index)));
    }

    /**
     * Gets the person card handle of a person in the list.
     */
    public PersonCardHandle getPersonCardHandle(ReadOnlyPerson person) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> personCardNode = nodes.stream()
                .filter(n -> new PersonCardHandle(n).isSamePerson(person))
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

    /**
     * Gets the total number of people in the list.
     */
    public int getNumberOfPeople() {
        return getListView().getItems().size();
    }
}
