package seedu.address.testutil;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Changes test failures to successes for tests annotated with {@link ExpectedFailure}.
 */
public class ExpectedFailureRule implements TestRule {
    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                if (description.getAnnotation(ExpectedFailure.class) == null) {
                    base.evaluate();
                }

                try {
                    base.evaluate();
                } catch (Throwable e) {
                    System.out.println(String.format("Test %s failed as expected.", description));
                }
            }
        };
    }
}
