package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.HelpWindow.USERGUIDE_FILE_PATH;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.HelpWindowHandle;

public class HelpWindowTest extends GuiUnitTest {

    private HelpWindow helpWindow;
    private HelpWindowHandle helpWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> helpWindow = new HelpWindow());
        FxToolkit.setupStage((stage) -> stage.setScene(helpWindow.getRoot().getScene()));
        FxToolkit.showStage();
        helpWindowHandle = new HelpWindowHandle();
    }

    @Test
    public void display() throws Exception {
        URL expectedHelpPage = HelpWindow.class.getResource(USERGUIDE_FILE_PATH);
        assertEquals(expectedHelpPage, helpWindowHandle.getLoadedUrl());
    }
}
