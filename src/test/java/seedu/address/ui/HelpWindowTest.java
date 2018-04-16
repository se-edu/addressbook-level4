package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.HelpWindow.USERGUIDE_FILE_PATH;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.HelpWindowHandle;
import javafx.stage.Stage;

public class HelpWindowTest extends GuiUnitTest {

    private HelpWindow helpWindow;
    private HelpWindowHandle helpWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> helpWindow = new HelpWindow());
        Stage helpWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(helpWindow.getRoot().getScene()));
        FxToolkit.showStage();
        helpWindowHandle = new HelpWindowHandle(helpWindowStage);
    }

    @Test
    public void display() {
        URL expectedHelpPage = HelpWindow.class.getResource(USERGUIDE_FILE_PATH);
        assertEquals(expectedHelpPage, helpWindowHandle.getLoadedUrl());
    }

    @Test
    public void isShowing_helpWindowIsShowing_returnsTrue() {
        guiRobot.interact(helpWindow::show);
        assertTrue(helpWindow.isShowing());
    }

    @Test
    public void isShowing_helpWindowIsHiding_returnsFalse() {
        guiRobot.interact(helpWindow.getRoot()::hide);
        assertFalse(helpWindow.isShowing());
    }

    @Test
    public void focus_helpWindowNotFocused_focused() throws Exception {
        guiRobot.interact(helpWindow::show);

        // Focus on another stage to remove focus from the helpWindow
        FxToolkit.setupStage(Stage::requestFocus);
        assertFalse(helpWindow.getRoot().isFocused());

        guiRobot.interact(helpWindow::focus);
        assertTrue(helpWindow.getRoot().isFocused());
    }
}
