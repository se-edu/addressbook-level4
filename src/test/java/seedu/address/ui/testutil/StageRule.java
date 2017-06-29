package seedu.address.ui.testutil;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.testfx.api.FxToolkit;

/**
 * Properly sets up and tears down a JavaFx stage for our testing purposes.
 */
public class StageRule implements TestRule {
    protected void before() throws Throwable {
        FxToolkit.registerPrimaryStage();
    }

    protected void after() throws Throwable {
        FxToolkit.cleanupStages();
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
