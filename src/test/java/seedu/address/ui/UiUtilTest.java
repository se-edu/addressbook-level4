//@@author RyanAngJY
package seedu.address.ui;

import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

public class UiUtilTest {

    @Test
    public void convertStringToValidColorCode() {

        // empty string
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("")));

        // valid hexadecimal
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("01fb45")));

        // valid hexadecimal with whitespace
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("  cd  eff  f")));

        // valid hexadecimal with varying lengths
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("1a2b")));
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("1a2bfc124ab")));

        // valid hexadecimal with varying lengths and with random whitespace
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("  1  a  2b ")));
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode(" 1a2 bfc12  4ab ")));
    }
}
//@@author RyanAngJY
