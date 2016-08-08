package guiunittests;

import guitests.GuiRobot;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationAdapter;
import org.testfx.framework.junit.ApplicationFixture;

import java.util.concurrent.TimeoutException;

/**
 * A test base class for GUI unit tests classes that does the basic low level initialization for GUI testing.
 */
public abstract class GuiUnitTestBase implements ApplicationFixture {

    public GuiRobot guiRobot = new GuiRobot();

    @BeforeClass
    public static void beforeClass() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public final void before()
            throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(() -> new ApplicationAdapter(this));
        guiRobot.sleep(1000);
    }

    @After
    public final void after()
            throws Exception {
        FxToolkit.cleanupApplication(new ApplicationAdapter(this));
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public abstract void start(Stage stage) throws Exception;

    @Override
    public void stop() throws Exception {}

}
