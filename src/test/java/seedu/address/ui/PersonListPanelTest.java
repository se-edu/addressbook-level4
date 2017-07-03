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

    private PersonListPanel personListPanel;
    private PersonListPanelHandle personListPanelHandle;

    private List<ReadOnlyPerson> typicalPersons;

    @Before
    public void setUp() throws Exception {
        typicalPersons = Arrays.asList(new TypicalPersons().getTypicalPersons());

        ObservableList<ReadOnlyPerson> observableList = FXCollections.observableList(typicalPersons);

        guiRobot.interact(() -> personListPanel = new PersonListPanel(observableList));
        uiPartRule.setUiPart(personListPanel);

        personListPanelHandle = new PersonListPanelHandle();
    }

    @Test
    public void display() throws Exception {
        for (int i = 0; i < typicalPersons.size(); i++) {
            personListPanelHandle.navigateToPerson(typicalPersons.get(i));
            guiRobot.pauseForHuman();

            assertEquals(typicalPersons.get(i), personListPanelHandle.getPerson(i));
        }
    }
}
