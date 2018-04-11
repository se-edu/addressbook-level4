package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;

//@@author ChoChihTun
/**
 * Browser page of the selected person
 */
public class BrowserPage {

    private static final String SEARCH_PAGE_URL = "http://www.google.com.sg/search?q=";

    private String url;

    public BrowserPage(String personName) {
        String newUrl = SEARCH_PAGE_URL + personName;
        formatStringUrl(newUrl);
    }

    /**
     * Constructs a valid string url for google search
     *
     * @param url to be formatted into a valid string url
     */
    private void formatStringUrl(String url) {

        this.url = url.replaceAll(" ", "%20");
    }

    /**
     * Loads the search page of the selected person
     *
     * @throws Exception if user default browser is not found or failed to be launched
     */
    public void loadPage() throws Exception {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI(url));
    }

    public String getUrl() {
        return url;
    }
}
