package systemtests;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import seedu.address.ui.StatusBarFooter;

/**
 * Properly sets up and tears down a JavaFx stage for our testing purposes.
 */
public class ClockRule implements TestRule {
    public static final Clock INJECTED_CLOCK = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private static final Clock ORIGINAL_CLOCK = StatusBarFooter.getClock();

    protected void before() {
        // provides us a way to predict expected time more easily, to prevent scenarios whereby
        // a 1-second delay causes the verification to be wrong
        StatusBarFooter.setClock(INJECTED_CLOCK);
    }

    protected void after() {
        // restore original clock
        StatusBarFooter.setClock(ORIGINAL_CLOCK);
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
}
