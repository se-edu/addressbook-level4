package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalCalendarView.MONTH;
import static seedu.address.testutil.TypicalCalendarView.WEEK;
import static seedu.address.testutil.TypicalCalendarView.YEAR;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ChangeCommand;
import seedu.address.testutil.Assert;

public class ChangeCommandParserTest {

    private ChangeCommandParser parser = new ChangeCommandParser();
    private ChangeCommand changeCommand = new ChangeCommand("d"); // Set an initial time unit to check against

    @Test
    public void parse_validArgs_returnsChangeCommand() throws Exception {
        // Set initial time unit to d
        String initialTimeUnit = ChangeCommand.getTimeUnit();

        // Change time unit to w
        ChangeCommand expectedTimeUnit = new ChangeCommand(WEEK);
        ChangeCommand changeToInitialTimeUnit = new ChangeCommand(initialTimeUnit); // Change to initial time unit
        assertEquals(expectedTimeUnit, new ChangeCommand(WEEK));

        // Change time unit to m
        expectedTimeUnit = new ChangeCommand(MONTH);
        changeToInitialTimeUnit = new ChangeCommand(initialTimeUnit); // Change to initial time unit
        assertEquals(expectedTimeUnit, new ChangeCommand(MONTH));

        // Change time unit to y
        expectedTimeUnit = new ChangeCommand(YEAR);
        changeToInitialTimeUnit = new ChangeCommand(initialTimeUnit); // Change to initial time unit
        assertEquals(expectedTimeUnit, new ChangeCommand(YEAR));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "D", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "@", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
    }


    @Before
    public void isValidTimeUnit() {
        // null time unit
        Assert.assertThrows(NullPointerException.class, () -> ChangeCommandParser.isValidTimeUnit(null));

        // invalid time unit
        assertFalse(ChangeCommandParser.isValidTimeUnit("")); // empty string
        assertFalse(ChangeCommandParser.isValidTimeUnit(" ")); // space only
        assertFalse(ChangeCommandParser.isValidTimeUnit("#")); // special characters
        assertFalse(ChangeCommandParser.isValidTimeUnit("day")); // full time unit name
        assertFalse(ChangeCommandParser.isValidTimeUnit("1")); // numbers
        assertFalse(ChangeCommandParser.isValidTimeUnit("a")); // contains invalid alphabets
        assertFalse(ChangeCommandParser.isValidTimeUnit("D")); // Capital
        assertFalse(ChangeCommandParser.isValidTimeUnit(" d ")); // contains space

        // valid time unit
        assertTrue(ChangeCommandParser.isValidTimeUnit("d")); // day
        assertTrue(ChangeCommandParser.isValidTimeUnit("w")); // week
        assertTrue(ChangeCommandParser.isValidTimeUnit("m")); // month
        assertTrue(ChangeCommandParser.isValidTimeUnit("y")); // year
    }

    @Test
    public void isTimeUnitClash() {
        // All time units' validity are checked in isValidTimeUnit()

        // There is a clash of time unit
        assertTrue(ChangeCommandParser.isTimeUnitClash("d"));

        // No clash in time unit
        assertFalse(ChangeCommandParser.isTimeUnitClash("w"));
        assertFalse(ChangeCommandParser.isTimeUnitClash("m"));
        assertFalse(ChangeCommandParser.isTimeUnitClash("y"));

        // change current time unit to w
        changeCommand = new ChangeCommand("w");

        // There is a clash of time unit for w now
        assertTrue(ChangeCommandParser.isTimeUnitClash("w"));

        // d is no longer clash
        assertFalse(ChangeCommandParser.isTimeUnitClash("d"));
    }
}
