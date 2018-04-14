package seedu.address;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeoutException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import javafx.application.Application;
import systemtests.SystemTestSetupHelper;

public class AppParametersTest {
    private Application testApp;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    private void setApplicationParameters(String parameter) {
        try {
            testApp = FxToolkit.setupApplication(TestApp.class, "--config=" + parameter);
        } catch (TimeoutException te) {
            throw new AssertionError("Application takes too long to set up.");
        }
    }

    @Test
    public void parse() {

        // valid path
        String pathParameter = "config.json";
        setApplicationParameters(pathParameter);
        assertEquals(pathParameter, AppParameters.parse(testApp.getParameters()).getConfigPath().toString());
    }
}
