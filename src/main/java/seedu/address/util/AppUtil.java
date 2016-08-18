package seedu.address.util;

import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.model.datatypes.person.ReadOnlyPerson;

import java.net.URL;
import java.util.List;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static URL getResourceUrl(String resourcePath) {
        return MainApp.class.getResource(resourcePath);
    }

    public static Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

}
