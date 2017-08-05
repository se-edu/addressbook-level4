package seedu.address.ui.testutil;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import seedu.address.commons.core.EventsCenter;
import seedu.address.testutil.EventsCollector;

/**
 * Sets up an {@code EventsCollector} and tears it down after each test.
 */
public class EventsCollectorRule implements TestRule {
    public final EventsCollector eventsCollector = new EventsCollector();

    protected void after() {
        EventsCenter.clearSubscribers();
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
    }
}
