package address.testutil;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.File;

/**
 * Capture screenshot after each failed test
 */
public class ScreenShotRule extends TestWatcher{

    @Override
    protected void succeeded(Description description) {
        new File(description.getClassName() + description.getMethodName() + ".png").delete();
    }
}
