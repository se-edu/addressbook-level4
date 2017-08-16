package systemtests;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import seedu.address.ui.StatusBarFooter;

/**
 * This rule makes the time stamp in the status bar predictable during a test.
 * Before the test starts, the rule replaces the clock in the status bar with a fixed clock.
 * At the end of the test, the rule restores the original clock.
 * @see Clock#fixed(Instant, ZoneId)
 */
public class ClockRule implements TestRule {
    private Clock injectedClock;
    private final Clock originalClock = StatusBarFooter.getClock();

    protected void before() {
        setInjectedClockToCurrentTime();
    }

    protected void after() {
        StatusBarFooter.setClock(originalClock);
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                try {
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
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
