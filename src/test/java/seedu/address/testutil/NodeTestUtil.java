package seedu.address.testutil;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 * Utility class for nodes testing.
 */
public class NodeTestUtil {

    /**
     * Gets mid point of {@code node} relative to the screen.
     * @return a Point2D containing the x and y coordinates of the node's mid point on the screen.
     */
    public static Point2D getScreenMidPointOfNode(Node node) {
        double x = getScreenBoundsOfNode(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScreenBoundsOfNode(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x, y);
    }

    private static Bounds getScreenBoundsOfNode(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }

}
