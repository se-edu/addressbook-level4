//@@author RyanAngJY
package seedu.address.ui;

import static seedu.address.ui.CssSyntax.CSS_PROPERTY_BACKGROUND_COLOR;
import static seedu.address.ui.CssSyntax.CSS_PROPERTY_TEXT_COLOR;

import java.util.regex.Pattern;

import javafx.scene.control.Label;

/**
 * Contains utility methods for UI design.
 */
public class UiUtil {

    public static final String HEX_REGEX = "[A-Fa-f0-9]*";
    public static final String HEX_COLOR_BLACK = "#000000";
    public static final String HEX_COLOR_WHITE = "#FFFFFF";

    public static final String HEX_COLOR_REGEX = "#([A-Fa-f0-9]{6})";
    private static final char HEX_COLOR_PREFIX = '#';
    private static final String HEX_COLOR_BUFFER = "000000";
    private static final int HEX_COLOR_LENGTH = 6;

    private static final String NEUTRAL_COLOR_DENSITY = "88";


    /**
     * Returns a hexadecimal string representation of an integer.
     */
    public static String convertIntToHexadecimalString(int integer) {
        return Integer.toHexString(integer);
    }

    /**
     * Returns a valid CSS hexadecimal color code that is as similar
     * as possible to the given string (eg. #f23b21).
     */
    public static String convertStringToValidColorCode(String string) {
        string = removeAllWhitespaceInString(string);

        // HEX_COLOR_BUFFER ensures that the returned value has at least 6 hexadecimal digits
        String extendedHexString = string.concat(HEX_COLOR_BUFFER);

        if (Pattern.matches(HEX_REGEX, string)) {
            return HEX_COLOR_PREFIX + extendedHexString.substring(0, HEX_COLOR_LENGTH);
        } else {
            return HEX_COLOR_WHITE;
        }
    }

    /**
     * Sets the color of a label given a particular background color
     *
     * @@param backgroundColor Preferably a valid CSS hexadecimal color code (eg. #f23b21)
     */
    public static void setLabelColor(Label label, String backgroundColor) {
        backgroundColor = removeAllWhitespaceInString(backgroundColor);
        backgroundColor = isValidHexColorCode(backgroundColor) ? backgroundColor : HEX_COLOR_WHITE;

        String textColor = getMatchingColorFromGivenColor(backgroundColor);
        label.setStyle(CSS_PROPERTY_BACKGROUND_COLOR + backgroundColor + "; "
                + CSS_PROPERTY_TEXT_COLOR + textColor + ";");
    }

    /**
     * Sets the text color of a label
     *
     * @@param backgroundColor Preferably a valid CSS hexadecimal color code (eg. #f23b21)
     */
    public static String getMatchingColorFromGivenColor(String backgroundColor) {
        backgroundColor = removeAllWhitespaceInString(backgroundColor);
        backgroundColor = isValidHexColorCode(backgroundColor) ? backgroundColor : HEX_COLOR_WHITE;

        if (isColorDark(backgroundColor)) {
            return HEX_COLOR_WHITE;
        } else {
            return HEX_COLOR_BLACK;
        }
    }

    /**
     * Returns true if a given color is dark
     *
     * @@param color Preferably a valid CSS hexadecimal color code (eg. #f23b21)
     */
    public static boolean isColorDark(String color) {
        color = removeAllWhitespaceInString(color);
        color = isValidHexColorCode(color) ? color : HEX_COLOR_WHITE;

        int darknessCount = 0;

        for (int i = 1; i < 6; i = i + 2) {
            String colorDensity = color.substring(i, i + 2);
            if (colorDensity.compareToIgnoreCase(NEUTRAL_COLOR_DENSITY) < 0) {
                darknessCount++;
            }
        }

        if (darknessCount >= 2) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if a String is a valid CSS hexadecimal color code (eg. #f23b21)
     */
    public static boolean isValidHexColorCode (String string) {
        return Pattern.matches(HEX_COLOR_REGEX, string);
    }

    /**
     * Returns the given string without whitespaces
     */
    public static String removeAllWhitespaceInString(String string) {
        return string.replaceAll("\\s", "");
    }
}
//@@author
