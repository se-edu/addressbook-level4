//@@author RyanAngJY
package seedu.address.ui;

import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

public class UiUtilTest {

    @Test
    public void convertHexStringToValidColorCode() {

        // empty string
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertHexStringToValidColorCode("")));

        // valid hexadecimal color code
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertHexStringToValidColorCode("01fb45")));

        // valid hexadecimal color code with whitespace
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertHexStringToValidColorCode("  cd  eff  f")));

        // invalid hexadecimal color code valid hexadecimal number
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertHexStringToValidColorCode("1a2b")));
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertHexStringToValidColorCode("1a2bfc124ab")));

        // invalid hexadecimal color code with random whitespace
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertHexStringToValidColorCode("  1  a  2b ")));
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertHexStringToValidColorCode(" 1a2 bfc12  4ab ")));
    }
}
//@@author RyanAngJY
