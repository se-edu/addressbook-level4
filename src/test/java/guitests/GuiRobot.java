package guitests;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import org.testfx.api.FxRobot;

import guitests.guihandles.exceptions.StageNotFoundException;
import javafx.stage.Stage;

/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 */
public class GuiRobot extends FxRobot {

    public static final int PAUSE_FOR_HUMAN_DELAY_MILLISECONDS = 250;
    public static final int DEFAULT_WAIT_FOR_EVENT_TIMEOUT_MILLISECONDS = 5000;

    private static final String PROPERTY_TESTFX_HEADLESS = "testfx.headless";

    private final boolean isHeadlessMode;

    public GuiRobot() {
        String headlessPropertyValue = System.getProperty(PROPERTY_TESTFX_HEADLESS);
        isHeadlessMode = headlessPropertyValue != null && headlessPropertyValue.equals("true");
    }

    /**
     * Pauses execution for {@code PAUSE_FOR_HUMAN_DELAY_MILLISECONDS} milliseconds for a human to examine the
     * effects of the test. This method will be disabled when the GUI tests are executed in headless mode to avoid
     * unnecessary delays.
     */
    public void pauseForHuman() {
        if (isHeadlessMode) {
            return;
        }

        sleep(PAUSE_FOR_HUMAN_DELAY_MILLISECONDS);
    }

    /**
     * Waits for {@code event} to be true by {@code DEFAULT_WAIT_FOR_EVENT_TIMEOUT_MILLISECONDS} milliseconds.
     *
     * @throws EventTimeoutException if the time taken exceeds {@code DEFAULT_WAIT_FOR_EVENT_TIMEOUT_MILLISECONDS}
     * milliseconds.
     */
    public void waitForEvent(BooleanSupplier event) {
        waitForEvent(event, DEFAULT_WAIT_FOR_EVENT_TIMEOUT_MILLISECONDS);
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

        pauseForHuman();
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
     * Returns the stage with the stage title.
     */
    public Optional<Stage> getStage(String stageTitle) throws StageNotFoundException {
        return listTargetWindows().stream()
                .filter(window -> window instanceof Stage && ((Stage) window).getTitle().equals(stageTitle))
                .map(window -> (Stage) window)
                .findFirst();
    }

    /**
     * Represents an error which occurs when a timeout occurs when waiting for an event.
     */
    private class EventTimeoutException extends RuntimeException {
    }
}
