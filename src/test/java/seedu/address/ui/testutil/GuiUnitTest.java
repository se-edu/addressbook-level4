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

    private static final int UI_UPDATE_SLEEP_DELAY = 1000;

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
    protected GuiHandle addUiPart(UiPart<Region> part) {
        testApp.addUiPart(part);

        // give time for UI addition to take effect
        sleep(UI_UPDATE_SLEEP_DELAY);

        return getGuiHandle(part);
    }

    /**
     * Clears all Ui parts that were inserted into the application.
     */
    protected void clearUiParts() {
        testApp.clearUiParts();

        // give time for UI addition to take effect
        sleep(UI_UPDATE_SLEEP_DELAY);
    }

    /**
     * Creates an associated GuiHandle for the particular Ui component under test.
     */
    protected abstract GuiHandle getGuiHandle(UiPart<Region> part);

    /**
     * Sleeps this thread, allowing JavaFx threads to have a chance to run.
     */
    protected void sleep(int sleepDelayInMillis) {
        try {
            Thread.sleep(sleepDelayInMillis);
        } catch (InterruptedException exception) {
            /*
             * Since sleep() must be called intentionally for JavaFx
             * threads to have a chance to run, its failure will cause
             * that to not happen at all, which is undesired behavior.
             */
            throw new RuntimeException(exception);
        }
    }
}
