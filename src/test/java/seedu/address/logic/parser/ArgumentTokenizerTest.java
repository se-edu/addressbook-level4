package seedu.address.logic.parser;

import org.junit.BeforeClass;
import org.junit.Test;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.Parser.*;

public class ArgumentTokenizerTest {
    /**
     * Makes sure that static members of Parser are initialized
     */
    @BeforeClass
    public static void initParser() {
        Parser parser = new Parser();
    }

    @Test
    public void tokenize_validAddCmdArgsNoTags() {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(phoneNumberPrefix, emailPrefix,
                                                                addressPrefix, tagsPrefix);
        argsTokenizer.tokenize("John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01");

        assertEquals("John Doe", argsTokenizer.getPreamble().get());
        assertEquals("98765432", argsTokenizer.getValue(phoneNumberPrefix).get());
        assertEquals("johnd@gmail.com", argsTokenizer.getValue(Parser.emailPrefix).get());
        assertEquals("John street, block 123, #01-01", argsTokenizer.getValue(addressPrefix).get());
    }

    @Test
    public void tokenize_validAddCmdArgsWithTags() {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(phoneNumberPrefix, emailPrefix,
                                                                addressPrefix, tagsPrefix);
        argsTokenizer.tokenize("Betsy Crowe p/1234567 e/betsycrowe@gmail.com a/Newgate Prison t/criminal t/friend");
        List<String> tags = new ArrayList<>();
        tags.add("criminal");
        tags.add("friend");

        assertEquals("Betsy Crowe", argsTokenizer.getPreamble().get());
        assertEquals("1234567", argsTokenizer.getValue(phoneNumberPrefix).get());
        assertEquals("betsycrowe@gmail.com", argsTokenizer.getValue(Parser.emailPrefix).get());
        assertEquals("Newgate Prison", argsTokenizer.getValue(addressPrefix).get());
        assertEquals(tags, argsTokenizer.getValues(tagsPrefix).get());
    }

    @Test
    public void tokenize_validAddCmdArgsChangeOrder() {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(phoneNumberPrefix, emailPrefix,
                                                                addressPrefix, tagsPrefix);
        argsTokenizer.tokenize("John Doe e/johnd@gmail.com a/John street, block 123, #01-01 p/98765432");

        assertEquals("John Doe", argsTokenizer.getPreamble().get());
        assertEquals("98765432", argsTokenizer.getValue(phoneNumberPrefix).get());
        assertEquals("johnd@gmail.com", argsTokenizer.getValue(Parser.emailPrefix).get());
        assertEquals("John street, block 123, #01-01", argsTokenizer.getValue(addressPrefix).get());
    }

    @Test
    public void tokenize_repeatedPrefixes() {
        Prefix aArg = new Prefix("a:");
        Prefix bArg = new Prefix("b:");

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(aArg, bArg);
        argsTokenizer.tokenize("a: aaa b: bbb a: AAA");

        assertEquals("AAA", argsTokenizer.getValue(aArg).get());
        assertEquals("bbb", argsTokenizer.getValue(bArg).get());
    }

    @Test
    public void tokenize_interleavingRepeatedPrefixes() {
        Prefix aArg = new Prefix("a\\");
        Prefix bArg = new Prefix("b\\");
        Prefix cArgs = new Prefix("c\\");
        List<String> cVals = Arrays.asList("c1", "c2");

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(aArg, bArg, cArgs);
        argsTokenizer.tokenize("a\\ aaa c\\ c1 b\\ bbb c\\ c2");

        assertEquals("aaa", argsTokenizer.getValue(aArg).get());
        assertEquals("bbb", argsTokenizer.getValue(bArg).get());
        assertEquals(cVals, argsTokenizer.getValues(cArgs).get());
    }

    @Test
    public void tokenize_noPrefix() {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer();
        argsTokenizer.tokenize("Micky Mouse p/12345");

        assertEquals("Micky Mouse p/12345", argsTokenizer.getPreamble().get());
    }

    @Test
    public void tokenize_duplicatedRepeatedPrefix() {
        Prefix aArg = new Prefix("<a>");
        Prefix bArg = new Prefix("<b>");
        Prefix cArgs = new Prefix("<c>");
        List<String> cVals = Arrays.asList("c1", "c2", "c1");

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(aArg, bArg, cArgs);
        argsTokenizer.tokenize("<a>aaa<c>c1<b>bbb<c>c2<c>c1");

        assertEquals("aaa", argsTokenizer.getValue(aArg).get());
        assertEquals("bbb", argsTokenizer.getValue(bArg).get());
        assertEquals(cVals, argsTokenizer.getValues(cArgs).get());
    }
}
