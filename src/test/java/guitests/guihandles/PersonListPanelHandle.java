package guitests.guihandles;


import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the person list.
 */
public class PersonListPanelHandle extends NodeHandle {

    private static final String CARD_PANE_ID = "#cardPane";
    private static final String PERSON_LIST_VIEW_ID = "#personListView";

    public PersonListPanelHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getChildNode(PERSON_LIST_VIEW_ID));
    }

    /**
     * Returns the selected person in the list view. A maximum of 1 item can be selected at any time.
     */
    public Optional<ReadOnlyPerson> getSelectedPerson() {
        List<ReadOnlyPerson> personList = getListView().getSelectionModel().getSelectedItems();

        if (personList.size() > 1) {
            throw new AssertionError("Person list size expected 0 or 1.");
        }

        return personList.isEmpty() ? Optional.empty() : Optional.of(personList.get(0));
    }

    public ListView<ReadOnlyPerson> getListView() {
        return (ListView<ReadOnlyPerson>) getNode();
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(ReadOnlyPerson... persons) throws PersonNotFoundException {
        List<ReadOnlyPerson> personList = getListView().getItems();
        checkArgument(personList.size() == persons.length,
                "List size mismatched\nExpected " + personList.size() + " persons");

        for (int i = 0; i < persons.length; i++) {
            final int scrollTo = i; // lambda expression needs i to be final
            GUI_ROBOT.interact(() -> getListView().scrollTo(scrollTo));
            GUI_ROBOT.pauseForHuman();
            if (!TestUtil.compareCardAndPerson(getPersonCardHandle(i), persons[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public void navigateToPerson(ReadOnlyPerson person) throws PersonNotFoundException {
        if (!getListView().getItems().contains(person)) {
            throw new PersonNotFoundException();
        }

        GUI_ROBOT.interact(() -> {
            getListView().scrollTo(person);
            getListView().getSelectionModel().select(person);
        });
        GUI_ROBOT.pauseForHuman();
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
    private PersonCardHandle getPersonCardHandle(int index) throws PersonNotFoundException {
        return getPersonCardHandle(getPerson(index));
    }

    /**
     * Gets the person card handle of a person in the list.
     */
    public PersonCardHandle getPersonCardHandle(ReadOnlyPerson person) throws PersonNotFoundException {
        if (!getListView().getItems().contains(person)) {
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
        return GUI_ROBOT.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Gets the total number of people in the list.
     */
    public int getNumberOfPeople() {
        return getListView().getItems().size();
    }
}
