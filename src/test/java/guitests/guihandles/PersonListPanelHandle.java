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
public class PersonListPanelHandle extends NodeHandle<ListView<ReadOnlyPerson>> {
    public static final String PERSON_LIST_VIEW_ID = "#personListView";

    private static final String CARD_PANE_ID = "#cardPane";

    public PersonListPanelHandle(ListView<ReadOnlyPerson> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns the selected person in the list view. A maximum of 1 item can be selected at any time.
     */
    public Optional<ReadOnlyPerson> getSelectedPerson() {
        List<ReadOnlyPerson> personList = getRootNode().getSelectionModel().getSelectedItems();

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
        List<ReadOnlyPerson> personList = getRootNode().getItems();
        checkArgument(personList.size() == persons.length,
                "List size mismatched\nExpected " + personList.size() + " persons");

        for (int i = 0; i < persons.length; i++) {
            final int scrollTo = i; // lambda expression needs i to be final
            guiRobot.interact(() -> getRootNode().scrollTo(scrollTo));
            guiRobot.pauseForHuman();
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
        if (!getRootNode().getItems().contains(person)) {
            throw new PersonNotFoundException();
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(person);
            getRootNode().getSelectionModel().select(person);
        });
    }

    /**
     * Returns the person at the specified {@code index} in the list.
     */
    public ReadOnlyPerson getPerson(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns the person card handle of a person associated with the {@code index} in the list.
     */
    private PersonCardHandle getPersonCardHandle(int index) throws PersonNotFoundException {
        return getPersonCardHandle(getPerson(index));
    }

    /**
     * Returns the {@code PersonCardHandle} of the specified {@code person} in the list.
     */
    public PersonCardHandle getPersonCardHandle(ReadOnlyPerson person) throws PersonNotFoundException {
        Set<Node> nodes = getAllCardNodes();
        Optional<PersonCardHandle> personCardNode = nodes.stream()
                .map(PersonCardHandle::new)
                .filter(handle -> handle.isSamePerson(person))
                .findFirst();

        if (!personCardNode.isPresent()) {
            throw new PersonNotFoundException();
        }

        return personCardNode.get();
    }

    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Returns the total number of people in the list.
     */
    public int getNumberOfPeople() {
        return getRootNode().getItems().size();
    }
}
