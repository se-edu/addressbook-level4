package seedu.address.testutil;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 * Utility class for nodes testing.
 */
public class NodeTestUtil {

    /**
    * Returns a {@code Point2D} object containing the x and y coordinates
    * of the mid point on the screen that {@code node} is on.
    */
    public static Point2D getScreenMidPoint(Node node) {
        double x = getNodeBounds(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getNodeBounds(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x, y);
    }

    /**
    * Returns the bounds of a {@code node} relative to the screen.
    */
    private static Bounds getNodeBounds(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }

}
