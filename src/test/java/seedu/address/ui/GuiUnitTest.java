package seedu.address.ui;

import org.junit.Rule;

import guitests.GuiRobot;
import seedu.address.ui.testutil.UiPartRule;

/**
 * A GUI unit test class for AddressBook.
 */
public abstract class GuiUnitTest {
    @Rule
    public final UiPartRule uiPartRule = new UiPartRule();

    protected final GuiRobot guiRobot = new GuiRobot();
}
