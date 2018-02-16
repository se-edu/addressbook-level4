package seedu.address.ui;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.StageHandle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seedu.address.commons.core.Config;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.LogicManager;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains tests for closing of the {@code MainWindow}.
 */
public class MainWindowCloseTest extends GuiUnitTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private MainWindow mainWindow;
    private EmptyMainWindowHandle mainWindowHandle;
    private Stage stage;

    @Before
    public void setUp() throws Exception {
        FxToolkit.setupStage(stage -> {
            this.stage = stage;
            mainWindow = new MainWindow(stage, new Config(), new UserPrefs(), new LogicManager(new ModelManager()));
            mainWindowHandle = new EmptyMainWindowHandle(stage);

            stage.setScene(mainWindow.getRoot().getScene());
            mainWindowHandle.focus();
        });
        FxToolkit.showStage();
    }

    @Test
    public void close_menuBarExitButton_exitAppRequestEventPosted() {
        mainWindowHandle.clickOnMenuExitButton();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void close_externalRequest_exitAppRequestEventPosted() {
        mainWindowHandle.closeMainWindowExternally();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * A handle for an empty {@code MainWindow}. The components in {@code MainWindow} are not initialized.
     */
    private class EmptyMainWindowHandle extends StageHandle {

        private EmptyMainWindowHandle(Stage stage) {
            super(stage);
        }

        /**
         * Closes the {@code MainWindow} by clicking on the menu bar's exit button.
         */
        private void clickOnMenuExitButton() {
            guiRobot.clickOn("File");
            guiRobot.clickOn("Exit");
        }

        /**
         * Closes the {@code MainWindow} through an external request {@code MainWindow} (e.g pressing the 'X' button on
         * the {@code MainWindow} or closing the app through the taskbar).
         */
        private void closeMainWindowExternally() {
            guiRobot.interact(() -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        }
    }
}
