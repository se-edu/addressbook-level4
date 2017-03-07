package seedu.address.ui.testutil;

import org.testfx.framework.junit.ApplicationRule;

import javafx.stage.Stage;

/**
 * An application rule that launches a GuiUnitTestApp.
 */
public class GuiUnitTestApplicationRule extends ApplicationRule {

    private GuiUnitTestApp testApp;

    public GuiUnitTestApplicationRule() {
        super(null);
    }

    @Override
    public void start(Stage stage) throws Exception {
        testApp = GuiUnitTestApp.spawnApp(stage);
    }

    public GuiUnitTestApp getTestApp() {
        return testApp;
    }
}
