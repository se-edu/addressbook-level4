package seedu.address.ui;

import java.net.URL;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";
    private static final String DEFAULT_PAGE = "default.html";

    @FXML
    private WebView browser;

    /**
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public BrowserPanel(AnchorPane placeholder) {
        super(FXML);
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the
                                                     // loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(browser);

        loadDefaultPage();
    }

    public void loadPersonPage(ReadOnlyPerson person) {
        loadPage("https://www.google.com.sg/#safe=off&q=" + person.getName().fullName.replaceAll(" ", "+"));
    }

    public void loadPage(String url) {
        browser.getEngine().load(url);
    }

    /**
     * Loads an default HTML file with a matching background on start-up
     * to better fit the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
