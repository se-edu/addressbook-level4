package seedu.address.ui;

import java.util.Optional;

import org.junit.Rule;

import guitests.GuiRobot;
import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.testutil.UiPartRule;

/**
 * A GUI unit test class for AddressBook.
 */
public abstract class GuiUnitTest {
    @Rule
    public final UiPartRule uiPartRule = new UiPartRule();

    protected final GuiRobot guiRobot = new GuiRobot();

    /**
     * Raises an {@code event}.
     */
    protected void raise(BaseEvent event) {
        guiRobot.interact(() -> EventsCenter.getInstance().post(event));
    }

    /**
     * Retrieves the {@code query} node owned by the {@code rootNode}.
     *
     * @param query name of the CSS selector for the node to retrieve.
     * @throws NodeNotFoundException if no such node exists.
     */
    protected <T extends Node> T getChildNode(Node rootNode, String query) {
        Optional<T> node = guiRobot.from(rootNode).lookup(query).tryQuery();
        return node.orElseThrow(NodeNotFoundException::new);
    }
}
