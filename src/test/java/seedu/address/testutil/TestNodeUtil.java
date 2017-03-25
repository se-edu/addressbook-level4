package seedu.address.testutil;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
* Utility class for nodes testing.
*/
public class TestNodeUtil {
    /**
     * Gets mid point of a node relative to the screen.
     * @return a Point2D containing the x and y coordinates of the node's mid point on the screen.
     */
    public static Point2D getScreenMidPoint(Node node) {
        double x = getScreenPos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScreenPos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x, y);
    }

    private static Bounds getScreenPos(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }
}
