package guitests;

import javafx.scene.input.KeyCodeCombination;
import org.testfx.api.FxRobot;
import seedu.address.testutil.TestUtil;

/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 */
public class GuiRobot extends FxRobot {

    public GuiRobot push(KeyCodeCombination keyCodeCombination){
        return (GuiRobot) super.push(TestUtil.scrub(keyCodeCombination));
    }

}
