package seedu.address.ui;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;

import org.junit.Assert;
import org.junit.Test;

//@author ChoChihTun
public class BrowserPageTest {

    @Test
    public void constructor_validArgs_success() {
        String searchPageUrl = "http://www.google.com.sg/search?q=";

        // Valid normal name
        BrowserPage browserPage = new BrowserPage(VALID_NAME_AMY);
        Assert.assertEquals(searchPageUrl + VALID_NAME_AMY.replaceAll(" ", "%20"), browserPage.getUrl());

        // Special characters
        browserPage = new BrowserPage("!@#$%");
        Assert.assertEquals(searchPageUrl + "!@#$%", browserPage.getUrl());

        // Multiple whitespaces
        browserPage = new BrowserPage("Amy     Bee");
        Assert.assertEquals(searchPageUrl + "Amy%20%20%20%20%20Bee", browserPage.getUrl());

        // Null
        browserPage = new BrowserPage(null);
        Assert.assertEquals(searchPageUrl + null, browserPage.getUrl());

        // Empty string
        browserPage = new BrowserPage("");
        Assert.assertEquals(searchPageUrl + "", browserPage.getUrl());
    }
}
