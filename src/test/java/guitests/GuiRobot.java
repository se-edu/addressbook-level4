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

    private static final int PAUSE_FOR_HUMAN_DELAY_MILLISECONDS = 250;
    private static final int DEFAULT_WAIT_FOR_EVENT_TIMEOUT_MILLISECONDS = 5000;

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
     * Returns true if tests are run in headless mode.
     */
    public boolean isHeadlessMode() {
        return isHeadlessMode;
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
        return getNumberOfWindowsShown(stageTitle) >= 1;
    }

    /**
     * Returns the number of windows with {@code stageTitle} that are currently open.
     */
    public int getNumberOfWindowsShown(String stageTitle) {
        return (int) listTargetWindows().stream()
                .filter(window -> window instanceof Stage && ((Stage) window).getTitle().equals(stageTitle))
                .count();
    }

    /**
     * Returns the first stage, ordered by proximity to the current target window, with the stage title.
     * The order that the windows are searched are as follows (proximity): current target window,
     * children of the target window, rest of the windows.
     *
     * @throws StageNotFoundException if the stage is not found.
     */
    public Stage getStage(String stageTitle) {
        Optional<Stage> targetStage = listTargetWindows().stream()
                .filter(Stage.class::isInstance) // checks that the window is of type Stage
                .map(Stage.class::cast)
                .filter(stage -> stage.getTitle().equals(stageTitle))
                .findFirst();

        return targetStage.orElseThrow(StageNotFoundException::new);
    }

    /**
     * Represents an error which occurs when a timeout occurs when waiting for an event.
     */
    private class EventTimeoutException extends RuntimeException {
    }
}
