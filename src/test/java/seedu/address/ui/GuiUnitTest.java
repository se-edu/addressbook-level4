package seedu.address.ui;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import guitests.GuiRobot;
import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;
import seedu.address.ui.testutil.StageExtension;
import seedu.address.ui.testutil.UiPartExtension;

/**
 * A GUI unit test class for AddressBook.
 */
@ExtendWith(StageExtension.class)
public abstract class GuiUnitTest {

    @RegisterExtension
    public final UiPartExtension uiPartExtension = new UiPartExtension();

    protected final GuiRobot guiRobot = new GuiRobot();

    /**
     * Retrieves the {@code query} node owned by the {@code rootNode}.
     *
     * @param query name of the CSS selector of the node to retrieve.
     * @throws NodeNotFoundException if no such node exists.
     */
    protected <T extends Node> T getChildNode(Node rootNode, String query) {
        Optional<T> node = guiRobot.from(rootNode).lookup(query).tryQuery();
        return node.orElseThrow(NodeNotFoundException::new);
    }
}
