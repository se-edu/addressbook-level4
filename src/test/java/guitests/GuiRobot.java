package guitests;

import org.testfx.api.FxRobot;

import javafx.scene.input.KeyCode;

/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 *
 * {@code GuiRobot} is designed to be a singleton as some functionalities in
 * {@code FxRobot} also acts like a singleton.
 */
public class GuiRobot extends FxRobot {

    private static GuiRobot instance;

    public static GuiRobot getInstance() {
        if (instance == null) {
            instance = new GuiRobot();
        }
        return instance;
    }

    /**
     * Presses the enter key.
     */
    public void pressEnter() {
        type(KeyCode.ENTER);
        sleep(500);
    }
}
