package seedu.address.commons.util;

import javafx.stage.Stage;

/**
 * Contains utility methods for JavaFX views
 */
public class FxViewUtil {

    /**
     * Sets the given image as the icon for the given stage.
     *
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    public static void setStageIcon(Stage stage, String iconSource) {
        stage.getIcons().setAll(AppUtil.getImage(iconSource));
    }

}
