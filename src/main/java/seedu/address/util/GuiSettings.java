package seedu.address.util;

import java.awt.*;
import java.io.Serializable;

/**
 * A Serializable class that contains the GUI settings.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;

    private Double windowWidth;
    private Double windowHeight;
    private Point windowCoordinates;

    public GuiSettings() {
        this.windowWidth = DEFAULT_WIDTH;
        this.windowHeight = DEFAULT_HEIGHT;
        this.windowCoordinates = null; // null represent no coordinates
    }

    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
    }

    public Double getWindowWidth() {
        return windowWidth;
    }

    public Double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates;
    }
}
