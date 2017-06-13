package guitests;

import static guitests.GuiRobotUtil.MEDIUM_WAIT;

import java.util.function.BooleanSupplier;

import org.testfx.api.FxRobot;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 *
 * {@code GuiRobot} is designed to be a singleton as some functionalities in
 * {@code FxRobot} also acts like a singleton.
 */
public class GuiRobot extends FxRobot {
    private static final String PROPERTY_GUITESTS_HEADLESS = "guitests.headless";
    private static final GuiRobot INSTANCE = new GuiRobot();

    private final boolean isHeadlessMode;

    private GuiRobot() {
        String headlessPropertyValue = System.getProperty(PROPERTY_GUITESTS_HEADLESS);
        isHeadlessMode = (headlessPropertyValue != null && headlessPropertyValue.equals("true"));
    }

    public static GuiRobot getInstance() {
        return INSTANCE;
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

    /**
     * Wait for {@code event} to be true.
     * @throws AssertionError if the time taken exceeds {@code timeOut}.
     */
    public void waitForEvent(BooleanSupplier event, int timeOut) {
        int timePassed = 0;
        final int retryInterval = 50;

        while (!event.getAsBoolean()) {
            sleep(retryInterval);
            timePassed += retryInterval;

            if (timePassed > timeOut) {
                throw new EventTimeoutException();
            }
        }
    }

    /**
     * Returns true if the window with {@code stageTitle} is currently open.
     */
    public boolean isWindowActive(String stageTitle) {
        return (listTargetWindows().stream()
                .filter(window -> window instanceof Stage && ((Stage) window).getTitle().equals(stageTitle))
                .count() == 1);
    }

    /**
     * Presses the enter key.
     */
    public void pressEnter() {
        type(KeyCode.ENTER);
        pauseForHuman(MEDIUM_WAIT);
    }

    /**
     * Represents an error which occurs when a timeout occurs when waiting for an event.
     */
    private class EventTimeoutException extends RuntimeException {
    }
}
