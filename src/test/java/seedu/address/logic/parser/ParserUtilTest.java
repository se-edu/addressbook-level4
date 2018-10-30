package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import static seedu.address.testutil.TypicalModules.ASKING_QUESTIONS;
import static seedu.address.testutil.TypicalModules.CODE_ASKING_QUESTIONS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
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
}
