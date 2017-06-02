package guitests;

import java.util.function.BooleanSupplier;

import org.testfx.api.FxRobot;

import javafx.stage.Stage;

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
     * Pause execution for a human to examine the effects of the test. This method will be disabled
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

    /**
     * Waits for {@code event} to be true.
     *
     * @param timeOut in milliseconds
     * @throws EventTimeoutException if the time taken exceeds {@code timeOut}.
     */
    public void waitForEvent(BooleanSupplier event, int timeOut) {
        int timePassed = 0;
        final int retryInterval = 50;

        while (!event.getAsBoolean()) {
            sleep(retryInterval);
            timePassed += retryInterval;

            if (timePassed >= timeOut) {
                throw new EventTimeoutException();
            }
        }
    }

    /**
     * Returns true if the window with {@code stageTitle} is currently open.
     */
    public boolean isWindowShown(String stageTitle) {
        return listTargetWindows().stream()
                .filter(window -> window instanceof Stage && ((Stage) window).getTitle().equals(stageTitle))
                .count() >= 1;
    }

    /**
     * Represents an error which occurs when a timeout occurs when waiting for an event.
     */
    private class EventTimeoutException extends RuntimeException {
    }
}
