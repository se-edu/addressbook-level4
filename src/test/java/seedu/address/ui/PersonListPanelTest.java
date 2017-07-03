package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.EventsUtil;
import seedu.address.testutil.TypicalPersons;

public class PersonListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(Arrays.asList(new TypicalPersons().getTypicalPersons()));

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private PersonListPanel personListPanel;
    private PersonListPanelHandle personListPanelHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> personListPanel = new PersonListPanel(TYPICAL_PERSONS));
        uiPartRule.setUiPart(personListPanel);

        personListPanelHandle = new PersonListPanelHandle(getChildNode(personListPanel.getRoot(),
                PersonListPanelHandle.PERSON_LIST_VIEW_ID));
    }

    @Test
    public void display() throws Exception {
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            personListPanelHandle.navigateToPerson(TYPICAL_PERSONS.get(i));
            assertEquals(TYPICAL_PERSONS.get(i), personListPanelHandle.getPerson(i));
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        EventsUtil.raise(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();
        assertEquals(INDEX_SECOND_PERSON.getZeroBased(), personListPanelHandle.getSelectedIndex());
    }
}
