package seedu.address.ui.testutil;

import org.junit.Before;
import org.testfx.api.FxToolkit;

import guitests.guihandles.GuiHandle;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * Serves as a base class for a GUI unit test, as it sets up
 * an application that allows testing for a single GUI component.
 */
public abstract class GuiUnitTest {

    protected GuiUnitTestApp testApp;

    @Before
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.hideStage();
        testApp = (GuiUnitTestApp) FxToolkit.setupApplication(() -> new GuiUnitTestApp());
    }

    /**
     * Adds a new Ui part that is being tested into the application, and returns
     * an associated GuiHandle for the Ui part.
     */
    protected GuiHandle addUiPart(UiPart<Region> part) throws InterruptedException {
        testApp.addUiPart(part);
        return getGuiHandle(part);
    }

    /**
     * Clears all Ui parts that were inserted into the application.
     */
    protected void clearUiParts() throws InterruptedException {
        testApp.clearUiParts();
    }

    /**
     * Creates an associated GuiHandle for the particular Ui component under test.
     */
    protected abstract GuiHandle getGuiHandle(UiPart<Region> part);
}
