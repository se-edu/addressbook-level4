package seedu.address.ui.testutil;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;

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

    /**
     * A class that collects events raised by other classes.
     */
    public class EventsCollector {
        private List<BaseEvent> events = new ArrayList<BaseEvent>();

        public EventsCollector() {
            EventsCenter.getInstance().registerHandler(this);
        }

        /**
         * Collects any event raised by any class
         */
        @Subscribe
        public void collectEvent(BaseEvent event) {
            events.add(event);
        }

        /**
         * Removes collected events from the collected list
         */
        public void reset() {
            events.clear();
        }

        public int getSize() {
            return events.size();
        }

        /**
         * Returns the most recent event collected
         */
        public BaseEvent getMostRecent() {
            if (events.isEmpty()) {
                return null;
            }

            return events.get(events.size() - 1);
        }

        /**
         * Returns true if the collector did not receive any events
         */
        public boolean isEmpty() {
            return events.isEmpty();
        }
    }
}
