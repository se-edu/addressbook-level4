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
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class BrowserPanelTest extends GuiUnitTest {

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle();
    }

    @Test
    public void display() throws Exception {
        browserPanelHandle.clickOnWebView();

        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // web page with person
        Person person = new PersonBuilder().build();
        guiRobot.interact(() -> EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(person)));
        guiRobot.pauseForHuman();
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + person.getName().fullName.replaceAll(" ", "+"));
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
