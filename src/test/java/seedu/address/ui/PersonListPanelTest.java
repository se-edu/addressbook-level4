package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.Test;

import com.google.common.base.Stopwatch;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.person.Person;
import seedu.address.storage.XmlSerializableAddressBook;

public class PersonListPanelTest extends GuiUnitTest {
    private static final ObservableList<Person> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/sandbox/");

    private static final Logger logger = LogsCenter.getLogger(PersonListPanelTest.class);

    private static final int CARD_CREATION_AND_DELETION_TIMEOUT = 2500;
    private static final int LIST_CREATION_TIMEOUT = 9500;
    private static final int TEST_TIMEOUT = CARD_CREATION_AND_DELETION_TIMEOUT + LIST_CREATION_TIMEOUT;

    private PersonListPanelHandle personListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_PERSONS);

        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            personListPanelHandle.navigateToCard(TYPICAL_PERSONS.get(i));
            Person expectedPerson = TYPICAL_PERSONS.get(i);
            PersonCardHandle actualCard = personListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedPerson, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_PERSONS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PersonCardHandle expectedPerson = personListPanelHandle.getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PersonCardHandle selectedPerson = personListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} does not take too long
     * to execute.
     * <ol>
     *     <li>If {@code TestTimedOutException} is thrown before the logs are printed, then the failure occurs during
     *         the preparation of the test (creating the xml file containing the large number of persons).</li>
     *     <li>If {@code TestTimedOutException} is thrown after the logs are printed, then the failure occurs during
     *         the creation and deletion of the person cards.</li>
     * </ol>
     */
    // The timeout makes the test fail fast. Without it, the test can stall for a long time if
    // guiRobot#interact(Runnable) takes very long to complete execution because guiRobot#interact(Runnable) only
    // returns after the Runnable completes execution.
    @Test(timeout = TEST_TIMEOUT)
    public void performanceTest() throws Exception {
        ObservableList<Person> backingList = createBackingList(10000);

        Stopwatch stopwatch = Stopwatch.createStarted();
        initUi(backingList);
        guiRobot.interact(backingList::clear);
        if (stopwatch.elapsed(TimeUnit.MILLISECONDS) >= CARD_CREATION_AND_DELETION_TIMEOUT) {
            fail("Creation and deletion of person cards exceeded time limit");
        }
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code PersonListPanel}. Logs the time taken for method execution if it finishes before
     * {@code LIST_CREATION_TIMEOUT}.
     *
     * @throws AssertionError if this method takes too long to execute.
     */
    private ObservableList<Person> createBackingList(int personCount) throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();

        File xmlFile = createXmlFileWithPersons(personCount);
        XmlSerializableAddressBook xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableAddressBook.class);
        ObservableList<Person> personList =
                FXCollections.observableArrayList(xmlAddressBook.toModelType().getPersonList());

        long createListTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        String createListMessage = "List creation took: " + createListTime + "ms. ";
        if (createListTime >= LIST_CREATION_TIMEOUT) {
            fail(createListMessage + "Time limit exceeded.");
        } else {
            logger.info(createListMessage + "Continuing test.");
        }

        return personList;
    }

    /**
     * Returns a .xml file containing {@code personCount} persons. This file will be deleted when the JVM terminates.
     */
    private File createXmlFileWithPersons(int personCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<addressbook>\n");
        for (int i = 0; i < personCount; i++) {
            builder.append("<persons>\n");
            builder.append("<name>a</name>\n");
            builder.append("<phone>").append(i).append("00</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("</persons>\n");
        }
        builder.append("</addressbook>\n");

        File manyPersonsFile = new File(TEST_DATA_FOLDER + "manyPersons.xml");
        FileUtil.createFile(manyPersonsFile);
        FileUtil.writeToFile(manyPersonsFile, builder.toString());
        manyPersonsFile.deleteOnExit();
        return manyPersonsFile;
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Person> backingList) {
        PersonListPanel personListPanel = new PersonListPanel(backingList);
        uiPartRule.setUiPart(personListPanel);

        personListPanelHandle = new PersonListPanelHandle(getChildNode(personListPanel.getRoot(),
                PersonListPanelHandle.PERSON_LIST_VIEW_ID));
    }
}
