package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

public class PersonListPanelTest extends GuiUnitTest {
    private static final List<ReadOnlyPerson> TYPICAL_PERSONS = Arrays.asList(new TypicalPersons().getTypicalPersons());

    private PersonListPanel personListPanel;
    private PersonListPanelHandle personListPanelHandle;

    @Before
    public void setUp() throws Exception {
        ObservableList<ReadOnlyPerson> observableList = FXCollections.observableList(TYPICAL_PERSONS);

        guiRobot.interact(() -> personListPanel = new PersonListPanel(observableList));
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
