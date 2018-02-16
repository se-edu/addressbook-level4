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

public class MainWindowTest extends GuiUnitTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private MainWindow mainWindow;
    private MainWindowHandleUnit mainWindowHandle;

    @Before
    public void setUp() throws Exception {
        FxToolkit.setupStage(stage -> {
            mainWindow =
                    new MainWindow(stage, new Config(), new UserPrefs(), new LogicManager(new ModelManager()));
            stage.setScene(mainWindow.getRoot().getScene());
            mainWindowHandle = new MainWindowHandleUnit(stage);
            mainWindowHandle.focus();
        });
        FxToolkit.showStage();
    }

    @Test
    public void menuBar_exit_success() {
        mainWindowHandle.clickOnMenuExitButton();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void externalRequest_exit_success() {
        mainWindowHandle.closeMainWindowExternally();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * {@code MainWindow} unit test class without any other components to test closing of the {@code MainWindow}.
     */
    private class MainWindowHandleUnit extends StageHandle {
        private final Stage stage;

        private MainWindowHandleUnit(Stage stage) {
            super(stage);
            this.stage = stage;
        }

        /**
         * Closes the {@code MainWindow} using it's menu bar.
         */
        private void clickOnMenuExitButton() {
            guiRobot.clickOn("File");
            guiRobot.clickOn("Exit");
        }

        /**
         * Simulate an external request to close the {@code MainWindow}.
         */
        private void closeMainWindowExternally() {
            guiRobot.interact(() -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        }
    }
}
