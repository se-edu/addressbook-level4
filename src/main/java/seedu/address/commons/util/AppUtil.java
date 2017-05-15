package seedu.address.commons.util;

import static com.google.common.base.Preconditions.checkNotNull;

import javafx.scene.image.Image;
import seedu.address.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static Image getImage(String imagePath) {
        checkNotNull(imagePath);
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

}
