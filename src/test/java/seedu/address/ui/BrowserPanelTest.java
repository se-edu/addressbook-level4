package seedu.address.ui;

import static org.junit.Assert.assertEquals;
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
import seedu.address.testutil.EventsUtil;
import seedu.address.testutil.TypicalPersons;

public class BrowserPanelTest extends GuiUnitTest {

    private static final Person ALICE = new TypicalPersons().alice;
    private static final PersonPanelSelectionChangedEvent SELECTION_CHANGED_EVENT_STUB =
            new PersonPanelSelectionChangedEvent(ALICE);

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // display associated webpage of a person
        EventsUtil.raise(SELECTION_CHANGED_EVENT_STUB);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+"));
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
