package guitests;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import org.testfx.api.FxRobot;

import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 */
public class GuiRobot extends FxRobot {

    public static final String PROPERTY_GUITESTS_HEADLESS = "guitests.headless";

    private boolean isHeadlessMode = false;

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
        if (!isHeadlessMode) {
            sleep(duration);
        }
    }

    /**
     * Wait for event to be true.
     */
    public void waitForEvent(BooleanSupplier event, int timeout) {
        int timePassed = 0;
        int retryInterval = 1000;

        while (event.getAsBoolean() != true) {
            sleep(retryInterval);
            timePassed += retryInterval;

            if (timePassed > timeout) {
                throw new AssertionError("Event timeout.");
            }

            retryInterval += retryInterval;
        }
    }

    /**
     * Checks that the window with {@code stageTitle} is currently open.
     */
    public boolean isWindowActive(String stageTitle) {
        Optional<Window> window = listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        return window.isPresent();
    }
}
