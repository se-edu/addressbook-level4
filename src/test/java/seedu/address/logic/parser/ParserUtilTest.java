package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import static seedu.address.testutil.TypicalModules.ASKING_QUESTIONS;
import static seedu.address.testutil.TypicalModules.CODE_ASKING_QUESTIONS;
import static seedu.address.testutil.TypicalModules.YEAR_FIVE;
import static seedu.address.testutil.TypicalModules.YEAR_FOUR;
import static seedu.address.testutil.TypicalModules.YEAR_ONE;
import static seedu.address.testutil.TypicalModules.YEAR_THREE;
import static seedu.address.testutil.TypicalModules.YEAR_TWO;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Semester;
import seedu.address.testutil.Assert;

public class ParserUtilTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseTokenizeNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.tokenize(null));
    }

    @Test
    public void parseTokenizeSplitsSuccess() {
        String[] expected = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        String[] tokenized = ParserUtil.tokenize("   a                        "
                + "b \t "
                + "c \r"
                + "d \n"
                + "e \t\r"
                + "f \t\n"
                + "g \r\n h"
                + "\t\r\n i");

        assertEquals(expected[0], tokenized[0]);
        assertEquals(expected[1], tokenized[1]);
        assertEquals(expected[2], tokenized[2]);
        assertEquals(expected[3], tokenized[3]);
        assertEquals(expected[4], tokenized[4]);
        assertEquals(expected[5], tokenized[5]);
        assertEquals(expected[6], tokenized[6]);
        assertEquals(expected[7], tokenized[7]);
        assertEquals(expected[8], tokenized[8]);
    }

    @Test
    public void validateNumOfArgs() throws Exception {
        String[] tokenized = {"a", "b", "c", "d"};

        ParserUtil.validateNumOfArgs(tokenized, 4);
        ParserUtil.validateNumOfArgs(tokenized, Integer.MIN_VALUE, 4);
        ParserUtil.validateNumOfArgs(tokenized, 4, Integer.MAX_VALUE);

        thrown.expect(ParseException.class);
        ParserUtil.validateNumOfArgs(tokenized, 5, Integer.MAX_VALUE);

        thrown.expect(ParseException.class);
        ParserUtil.validateNumOfArgs(tokenized, Integer.MAX_VALUE, 3);
    }

    @Test
    public void validateParseArgsMap() throws Exception {
        String[] tokenized = {"random", "-a", "a", "random", "-b", "b", "-c", "c"};
        Map<String, String> argsMap = ParserUtil.mapArgs(tokenized);
        assertEquals(argsMap.get("a"), "a");
        assertEquals(argsMap.get("b"), "b");
        assertEquals(argsMap.get("c"), "c");
    }

    @Test
    public void parseCodeNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCode(null));
    }

    @Test
    public void parseCodeValid() throws Exception {
        Code geqCode = ParserUtil.parseCode(CODE_ASKING_QUESTIONS);
        assertEquals(geqCode, ASKING_QUESTIONS.getCode());

        Code geqCodeLowerCase = ParserUtil.parseCode(CODE_ASKING_QUESTIONS.toLowerCase());
        assertEquals(geqCodeLowerCase, ASKING_QUESTIONS.getCode());
    }

    @Test
    public void parseCodeInvalid() throws Exception {
        Code geqCode = ParserUtil.parseCode(CODE_ASKING_QUESTIONS);
        assertEquals(geqCode, ASKING_QUESTIONS.getCode());
    }

    @Test
    public void parseYearNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseYear(null));
    }

    @Test
    public void parseYearTest() throws Exception {
        ParserUtil.parseYear(YEAR_ONE + "");
        ParserUtil.parseYear(YEAR_TWO + "");
        ParserUtil.parseYear(YEAR_THREE + "");
        ParserUtil.parseYear(YEAR_FOUR + "");
        ParserUtil.parseYear(YEAR_FIVE + "");

        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseYear("0"));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseYear("6"));
    }

    @Test
    public void parseSemesterNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSemester(null));
    }

    @Test
    public void parseSemesterTest() throws Exception {
        ParserUtil.parseSemester(Semester.SEMESTER_ONE);
        ParserUtil.parseSemester(Semester.SEMESTER_TWO);
        ParserUtil.parseSemester(Semester.SEMESTER_SPECIAL_ONE);
        ParserUtil.parseSemester(Semester.SEMESTER_SPECIAL_TWO);

        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseSemester("0"));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseSemester("3"));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseSemester("s0"));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseSemester("s3"));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseSemester(""));
    }

    @Test
    public void parseCreditNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCredit(null));
    }

    @Test
    public void parseCreditTest() throws Exception {
        ParserUtil.parseCredit("1");
        ParserUtil.parseCredit("20");

        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCredit("0"));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCredit("21"));
    }

    @Test
    public void parseGradeNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseGrade(null));
    }

    @Test
    public void parseGradeTest() throws Exception {
        ParserUtil.parseGrade("A+");

        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseGrade("G"));
    }
}
