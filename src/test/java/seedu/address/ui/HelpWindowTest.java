package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
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
        FxToolkit.registerStage(helpWindow::getRoot);
        helpWindowHandle = new HelpWindowHandle(helpWindow.getRoot());
    }

    @Test
    public void display() throws Exception {
        FxToolkit.showStage();
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
        assertFalse(helpWindow.isShowing());
    }

    @Test
    public void focus_helpWindowNotFocused_focused() throws Exception {
        // TODO: This test skip can be removed once this bug is fixed:
        // https://github.com/javafxports/openjdk-jfx/issues/50
        //
        // When there are two stages (stage1 and stage2) shown,
        // stage1 is in focus and stage2.requestFocus() is called,
        // we expect that stage1.isFocused() will return false while
        // stage2.isFocused() returns true. However, as reported in the bug report,
        // both stage1.isFocused() and stage2.isFocused() returns true,
        // which fails the test.
        assumeFalse("Test skipped in headless mode: Window focus behavior is buggy.", guiRobot.isHeadlessMode());
        guiRobot.interact(helpWindow::show);

        // Focus on another stage to remove focus from the helpWindow
        guiRobot.interact(() -> {
            Stage temporaryStage = new Stage();
            temporaryStage.show();
            temporaryStage.requestFocus();
        });
        assertFalse(helpWindow.getRoot().isFocused());

        guiRobot.interact(helpWindow::focus);
        assertTrue(helpWindow.getRoot().isFocused());
    }
}
