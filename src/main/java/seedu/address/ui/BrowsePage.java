package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;

/**
 * Searches the selected object in a pop up google search
 */
public class BrowsePage {

    private static final String SEARCH_PAGE_URL = "http://www.google.com.sg/search?q=";

    private String url;

    public BrowsePage(String searchObject) {
        String newUrl = SEARCH_PAGE_URL + searchObject;
        formatStringUrl(newUrl);
    }

    /**
     * Constructs a valid string url for google search
     *
     * @param newUrl url to be formatted into a valid string url
     */
    private void formatStringUrl(String newUrl) {
        this.url = newUrl.replaceAll(" ", "+");
    }

    /**
     * Loads the search page of the selected object
     * @throws Exception if user default browser is not found or failed to be launched
     */
    public void loadUrl() throws Exception {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI(url));
    }

}
