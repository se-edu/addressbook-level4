//@@author RyanAngJY
package seedu.address.ui;

import static seedu.address.ui.CssSyntax.CSS_PROPERTY_BACKGROUND_COLOR;

import java.util.regex.Pattern;

import javafx.scene.control.Label;

/**
 * Contains utility methods for UI design.
 */
public class UiUtil {

    public static final String HEX_REGEX = "[A-Fa-f0-9]*";

    public static final String HEX_COLOR_REGEX = "#([A-Fa-f0-9]{6})";
    private static final char HEX_COLOR_PREFIX = '#';
    private static final String HEX_COLOR_BUFFER = "000000";
    private static final int HEX_COLOR_LENGTH = 6;

    /**
     * Returns a hexadecimal string representation of an integer.
     */
    public static String convertIntToHexadecimalString(int integer) {
        return Integer.toHexString(integer);
    }

    /**
     * Returns a valid CSS hexadecimal color code (eg. #f23b21)
     *
     * @@params hexString Valid hexadecimal string or empty string.
     */
    public static String convertHexStringToValidColorCode(String hexString) {
        hexString = removeAllWhitespaceInString(hexString);
        assert Pattern.matches(HEX_REGEX, hexString);

        // HEX_COLOR_BUFFER ensures that the returned value has at least 6 hexadecimal digits
        String extendedHexString = hexString.concat(HEX_COLOR_BUFFER);
        return HEX_COLOR_PREFIX + extendedHexString.substring(0, HEX_COLOR_LENGTH);
    }

    /**
     * Sets the background color of a label
     *
     * @params color Valid CSS hexadecimal color code (eg. #f23b21)
     */
    public static void setLabelBackgroundColor(Label label, String color) {
        color = removeAllWhitespaceInString(color);
        assert Pattern.matches(HEX_COLOR_REGEX, color);
        label.setStyle(CSS_PROPERTY_BACKGROUND_COLOR + color + ";");
    }

    public static String removeAllWhitespaceInString(String string) {
        return string.replaceAll("\\s", "");
    }
}
//@@author RyanAngJY
