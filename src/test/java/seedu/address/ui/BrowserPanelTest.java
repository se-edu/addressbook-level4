package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.post;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_PREFIX;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class BrowserPanelTest extends GuiUnitTest {

    private static final Person ALICE = new TypicalPersons().alice;

    private PersonCard alicePersonCard;
    private PersonPanelSelectionChangedEvent selectedChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> alicePersonCard = new PersonCard(ALICE, 0));
        selectedChangedEventStub = new PersonPanelSelectionChangedEvent(alicePersonCard);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        post(selectedChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+"));
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
