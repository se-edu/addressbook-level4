package guitests;

import org.testfx.api.FxRobot;

/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 *
 * {@code GuiRobot} is designed to be a singleton as some functionalities in
 * {@code FxRobot} also acts like a singleton, such as the {@code windowFinder}
 * and {@code nodeFinder} of {@code FxRobot}.
 */
public class GuiRobot extends FxRobot {

    private static GuiRobot instance;

    private GuiRobot() {
        // to prevent instances being created
    }

    public static GuiRobot getInstance() {
        if (instance == null) {
            instance = new GuiRobot();
        }
        return instance;
    }
}
