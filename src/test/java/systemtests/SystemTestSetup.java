package systemtests;

import java.util.concurrent.TimeoutException;

import org.testfx.api.FxToolkit;

import guitests.guihandles.MainWindowHandle;
import seedu.address.TestApp;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains helper methods that system tests require.
 */
public class SystemTestSetup {
    private static TestApp testApp;
    private static MainWindowHandle mainWindowHandle;

    // prevents construction
    private SystemTestSetup() {}

    /**
     * Sets up the {@code TestApp} and returns it.
     */
    public static TestApp setupApplication() {
        try {
            FxToolkit.setupApplication(() -> testApp = new TestApp(TypicalPersons::getTypicalAddressBook,
                    TestApp.SAVE_LOCATION_FOR_TESTING));
        } catch (TimeoutException te) {
            throw new AssertionError("Application takes too long to set up.");
        }

        return testApp;
    }

    /**
     * Initializes the stage to be used by the tests.
     */
    public static void initializeStage() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Encapsulates the stage initialized by {@code initializeStage} in a {@code MainWindowHandle} and returns it.
     */
    public static MainWindowHandle setupMainWindowHandle() {
        try {
            FxToolkit.setupStage((stage) -> {
                mainWindowHandle = new MainWindowHandle(stage);
                mainWindowHandle.focus();
            });
            FxToolkit.showStage();
        } catch (TimeoutException te) {
            throw new AssertionError("Stage takes too long to set up.");
        }

        return mainWindowHandle;
    }

    /**
     * Tears down existing stages.
     */
    public static void tearDownStage() {
        try {
            FxToolkit.cleanupStages();
        } catch (TimeoutException te) {
            throw new AssertionError("Stage takes too long to tear down.");
        }
    }
}
