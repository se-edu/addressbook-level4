//@@author RyanAngJY
package seedu.address.ui;

import static org.junit.Assert.assertTrue;
import static seedu.address.ui.UiUtil.HEX_COLOR_BLACK;
import static seedu.address.ui.UiUtil.HEX_COLOR_WHITE;

import java.util.regex.Pattern;

import org.junit.Test;

public class UiUtilTest {

    private static final String LIGHT_COLOR_CODE = "#FFFFFF";
    private static final String DARK_COLOR_CODE = "#000000";

    @Test
    public void convertStringToValidColorCode() {

        // empty string
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("")));

        // valid hexadecimal
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("01fb45")));

        // valid hexadecimal with random whitespaces
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("  cd  eff  f")));

        // valid hexadecimal with varying lengths
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("1a2b")));
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("1a2bfc124ab")));

        // valid hexadecimal with varying lengths and with random whitespaces
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("  1  a  2b ")));
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode(" 1a2 bfc12  4ab ")));

        // invalid hexadecimal
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode("i#tov129nfoiZZ!!Za")));

        // invalid hexadecimal with random whitespaces
        assertTrue(Pattern.matches(UiUtil.HEX_COLOR_REGEX,
                UiUtil.convertStringToValidColorCode(" i t#ov   129 nfoi  ZZ !! Za ")));
    }

    @Test
    public void getMatchingColorFromGivenColor() {
        // invalid CSS color code
        assertTrue(HEX_COLOR_BLACK.equals(UiUtil.getMatchingColorFromGivenColor("asdio 42oi n")));

        // light CSS color code
        assertTrue(HEX_COLOR_BLACK.equals(UiUtil.getMatchingColorFromGivenColor(LIGHT_COLOR_CODE)));

        // dark CSS color code
        assertTrue(HEX_COLOR_WHITE.equals(UiUtil.getMatchingColorFromGivenColor(DARK_COLOR_CODE)));
    }
}
//@@author
