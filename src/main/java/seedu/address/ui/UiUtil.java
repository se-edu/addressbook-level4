//@@author RyanAngJY
package seedu.address.ui;

import static seedu.address.ui.CssSyntax.CSS_PROPERTY_BACKGROUND_COLOR;

import javafx.scene.control.Label;

/**
 * Contains utility methods for UI design.
 */
public class UiUtil {

    private static final char HEX_COLOR_PREFIX = '#';
    private static final String HEX_COLOR_BUFFER = "000000";
    private static final int HEX_COLOR_LENGTH = 7;

    /**
     * Returns a hexadecimal string representation of an integer.
     */
    public static String convertIntToHexadecimalString(int integer) {
        return Integer.toHexString(integer);
    }

    /**
     * Returns a valid CSS hexadecimal color code (eg. #f23b21)
     */
    public static String convertHexStringToValidColorCode(String hexString) {
        // HEX_COLOR_BUFFER ensures that the returned value has at least 6 digits
        String extendedHexString = hexString.concat(HEX_COLOR_BUFFER);
        return HEX_COLOR_PREFIX + extendedHexString.substring(0, HEX_COLOR_LENGTH - 1);
    }

    /**
     * Sets the background color of a label
     *
     * @params color Valid CSS hexadecimal color code (eg. #f23b21)
     */
    public static void setLabelBackgroundColor(Label label, String color) {
        assert HEX_COLOR_LENGTH == color.length();
        assert HEX_COLOR_PREFIX == color.charAt(0);

        label.setStyle(CSS_PROPERTY_BACKGROUND_COLOR + color + ";");
    }
}
//@@author RyanAngJY
