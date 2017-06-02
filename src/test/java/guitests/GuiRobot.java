package guitests;

import org.testfx.api.FxRobot;

/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 */
public class GuiRobot extends FxRobot {

    private static final String PROPERTY_TESTFX_HEADLESS = "testfx.headless";

    private final boolean isHeadlessMode;

    public GuiRobot() {
        String headlessPropertyValue = System.getProperty(PROPERTY_TESTFX_HEADLESS);
        isHeadlessMode = headlessPropertyValue != null && headlessPropertyValue.equals("true");
    }

    /**
     * Pauses execution for a human to examine the effects of the test. This method will be disabled
     * when the GUI tests are executed in headless mode to avoid unnecessary delays.
     *
     * @param duration in milliseconds
     */
    public void pauseForHuman(int duration) {
        if (isHeadlessMode) {
            return;
        }

        sleep(duration);
    }
}
