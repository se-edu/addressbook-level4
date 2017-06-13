package guitests.guihandles;

import static org.junit.Assert.assertFalse;

import java.util.Optional;

import guitests.GuiRobot;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class HelpWindowHandle extends WindowHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    public HelpWindowHandle() {
        super(GUI_ROBOT.window(HELP_WINDOW_TITLE));
    }

    public static boolean isWindowPresent() {
        GuiRobot guiRobot = new GuiRobot();
        Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage
                        && ((Stage) w).getTitle().equals(HelpWindowHandle.HELP_WINDOW_TITLE)).findAny();

        return window.isPresent();
    }


    public void closeWindow() {
        super.closeWindow();
        GUI_ROBOT.sleep(500);

        assertFalse(isWindowPresent());
    }
}
