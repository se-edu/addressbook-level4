package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

public class PersonListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(Arrays.asList(new TypicalPersons().getTypicalPersons()));

    private PersonListPanel personListPanel;
    private PersonListPanelHandle personListPanelHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> personListPanel = new PersonListPanel(TYPICAL_PERSONS));
        uiPartRule.setUiPart(personListPanel);

        personListPanelHandle = new PersonListPanelHandle();
    }

    @Test
    public void display() throws Exception {
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            personListPanelHandle.navigateToPerson(TYPICAL_PERSONS.get(i));
            guiRobot.pauseForHuman();

            assertEquals(TYPICAL_PERSONS.get(i), personListPanelHandle.getPerson(i));
        }
    }
}
