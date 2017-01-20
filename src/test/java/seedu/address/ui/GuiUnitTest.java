package seedu.address.ui;

import org.junit.Before;
import org.testfx.api.FxToolkit;

import guitests.GuiRobot;

/**
 * Serves as a base class for a GUI unit test, as it sets up
 * an application that allows testing for a single GUI component.
 */
public class GuiUnitTest {

    private GuiUnitTestApp testApp;
    private GuiRobot guiRobot;

    @Before
    public void setUp() throws Exception {
        int desiredWidth = 200;
        int desiredHeight = 120;

        guiRobot = new GuiRobot();

        FxToolkit.registerPrimaryStage();
        FxToolkit.hideStage();
        testApp = (GuiUnitTestApp) FxToolkit.setupApplication(() ->
                                                 new GuiUnitTestApp(guiRobot, desiredWidth, desiredHeight));
    }

    protected GuiUnitTestApp getTestApp() {
        return testApp;
    }

    protected GuiRobot getGuiRobot() {
        return guiRobot;
    }
}
