package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Stopwatch;

import guitests.guihandles.PersonListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.person.Person;
import seedu.address.storage.XmlSerializableAddressBook;

public class PersonListPanelTest extends GuiUnitTest {
    private static final ObservableList<Person> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/PersonListPanelTest/");
    private static final File MANY_PERSONS_FILE = new File(TEST_DATA_FOLDER + "9999persons.xml");

    private PersonListPanelHandle personListPanelHandle;

    @Test
    public void display() {
        setUp(TYPICAL_PERSONS);

        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            personListPanelHandle.navigateToCard(TYPICAL_PERSONS.get(i));
            Person expectedPerson = TYPICAL_PERSONS.get(i);
            Person actualPerson = personListPanelHandle.getPerson(i);

            assertEquals(expectedPerson, actualPerson);
            // Because we do not have the actual card, we cannot compare the index on the card
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        setUp(TYPICAL_PERSONS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        Person expectedPerson = personListPanelHandle.getPerson(INDEX_SECOND_PERSON.getZeroBased());
        Person selectedPerson = personListPanelHandle.getSelectedPerson();
        assertEquals(expectedPerson, selectedPerson);
    }

    @Test
    public void performanceTest() throws Exception {
        XmlSerializableAddressBook xmlAddressBook =
                XmlUtil.getDataFromFile(MANY_PERSONS_FILE, XmlSerializableAddressBook.class);
        ObservableList<Person> personList = FXCollections.observableArrayList(xmlAddressBook.getPersonList());
        Stopwatch stopwatch = Stopwatch.createStarted();

        setUp(personList);
        guiRobot.pauseForHuman();
        guiRobot.interact(personList::clear);
        guiRobot.pauseForHuman();
        if (stopwatch.elapsed(TimeUnit.MILLISECONDS) >= 2500) {
            fail();
        }
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     */
    private void setUp(ObservableList<Person> backingList) {
        PersonListPanel personListPanel = new PersonListPanel(backingList);
        uiPartRule.setUiPart(personListPanel);

        personListPanelHandle = new PersonListPanelHandle(getChildNode(personListPanel.getRoot(),
                PersonListPanelHandle.PERSON_LIST_VIEW_ID));
    }
}
