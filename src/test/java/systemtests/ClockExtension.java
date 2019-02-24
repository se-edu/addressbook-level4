package systemtests;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import seedu.address.ui.StatusBarFooter;

/**
 * This rule makes the time stamp in the status bar predictable during a test.
 * Before the test starts, the rule replaces the clock in the status bar with a fixed clock.
 * At the end of the test, the rule restores the original clock.
 * @see Clock#fixed(Instant, ZoneId)
 */
public class ClockExtension implements BeforeEachCallback, AfterEachCallback {
    private Clock injectedClock;
    private final Clock originalClock = StatusBarFooter.getClock();

    @Override
    public void afterEach(ExtensionContext context) {
        StatusBarFooter.setClock(originalClock);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        setInjectedClockToCurrentTime();
    }

    public Clock getInjectedClock() {
        return injectedClock;
    }

    /**
     * Replaces the clock in the status bar with a fixed clock having the current time as its instance.
     */
    public void setInjectedClockToCurrentTime() {
        injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        StatusBarFooter.setClock(injectedClock);
    }
}
