//@@author RyanAngJY
package seedu.address.ui;

/**
 * Contains utility methods for UI design.
 */
public class UiUtil {

    private static final String HEX_COLOR_PREFIX = "#";
    private static final String HEX_COLOR_BUFFER = "000000";

    public UiUtil() {}

    /**
     * Returns a hexadecimal string representation of an integer.
     */
    public static String convertIntToHexadecimalString(int integer) {
        return Integer.toHexString(integer);
    }

    /**
     * Returns a valid CSS hexadecimal color code
     */
    public static String convertHexStringToValidColorCode(String hexString) {
        // HEX_COLOR_BUFFER ensures that the returned value has at least 6 digits
        String extendedHexString = hexString.concat(HEX_COLOR_BUFFER);
        return HEX_COLOR_PREFIX + extendedHexString.substring(0, 6);
    }
}
//@@author RyanAngJY
