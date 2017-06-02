package guitests;

import org.testfx.api.FxRobot;

/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 */
public class GuiRobot extends FxRobot {

    private static final String PROPERTY_GUITESTS_HEADLESS = "guitests.headless";

    private final boolean isHeadlessMode;

    public GuiRobot() {
        String headlessPropertyValue = System.getProperty(PROPERTY_GUITESTS_HEADLESS);
        isHeadlessMode = (headlessPropertyValue != null && headlessPropertyValue.equals("true"));
    }

    /**
     * Allows GUI tests to pause its execution for a human to examine the effects of the test.
     * This method should be disabled when a non-human is executing the GUI test to avoid
     * unnecessary delay.
     */
    public void pauseForHuman(int duration) {
        if (isHeadlessMode) {
            return;
        }

        sleep(duration);
    }
}
