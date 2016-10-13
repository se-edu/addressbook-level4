package seedu.address.logic.parser;

import org.junit.BeforeClass;
import org.junit.Test;

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
    public void parse_validAddCmdArgsNoTags() {
        ArgumentTokenizer argsParser = new ArgumentTokenizer(addCmdArgs);
        ParsedArguments result = argsParser.tokenize(
                "John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01");

        assertEquals("John Doe", result.getArgumentValue(nameArg).get());
        assertEquals("98765432", result.getArgumentValue(phoneNumberArg).get());
        assertEquals("johnd@gmail.com", result.getArgumentValue(Parser.emailArg).get());
        assertEquals("John street, block 123, #01-01", result.getArgumentValue(addressArg).get());
    }

    @Test
    public void parse_validAddCmdArgsWithTags() {
        ArgumentTokenizer argsParser = new ArgumentTokenizer(addCmdArgs);
        ArgumentTokenizer.ParsedArguments result = argsParser.tokenize(
                "Betsy Crowe p/1234567 e/betsycrowe@gmail.com a/Newgate Prison t/criminal t/friend");
        List<String> tags = new ArrayList<>();
        tags.add("criminal");
        tags.add("friend");

        assertEquals("Betsy Crowe", result.getArgumentValue(nameArg).get());
        assertEquals("1234567", result.getArgumentValue(phoneNumberArg).get());
        assertEquals("betsycrowe@gmail.com", result.getArgumentValue(Parser.emailArg).get());
        assertEquals("Newgate Prison", result.getArgumentValue(addressArg).get());
        assertEquals(tags, result.getArgumentValue(Parser.tagArgs).get());
    }

    @Test
    public void parse_validAddCmdArgsChangeOrder() {
        ArgumentTokenizer argsParser = new ArgumentTokenizer(addCmdArgs);
        ParsedArguments result = argsParser.tokenize(
                "John Doe e/johnd@gmail.com a/John street, block 123, #01-01 p/98765432");

        assertEquals("John Doe", result.getArgumentValue(nameArg).get());
        assertEquals("98765432", result.getArgumentValue(phoneNumberArg).get());
        assertEquals("johnd@gmail.com", result.getArgumentValue(Parser.emailArg).get());
        assertEquals("John street, block 123, #01-01", result.getArgumentValue(addressArg).get());
    }

    @Test
    public void parse_repeatedNonRepeatableArgument() {
        NonRepeatableArgument aArg = new NonRepeatableArgument("a", "a:");
        NonRepeatableArgument bArg = new NonRepeatableArgument("b", "b:");
        List<Argument> args = Arrays.asList(aArg, bArg);

        ArgumentTokenizer argsParser = new ArgumentTokenizer(args);
        ParsedArguments result = argsParser.tokenize("a: aaa b: bbb a: AAA");

        assertEquals("AAA", result.getArgumentValue(aArg).get());
        assertEquals("bbb", result.getArgumentValue(bArg).get());
    }

    @Test
    public void parse_interleavingRepeatableArguments() {
        NonRepeatableArgument aArg = new NonRepeatableArgument("a", "a\\");
        NonRepeatableArgument bArg = new NonRepeatableArgument("b", "b\\");
        RepeatableArgument cArgs = new RepeatableArgument("c", "c\\");
        List<Argument> args = Arrays.asList(aArg, bArg, cArgs);
        List<String> cVals = Arrays.asList("c1", "c2");

        ArgumentTokenizer argsParser = new ArgumentTokenizer(args);
        ParsedArguments result = argsParser.tokenize("a\\ aaa c\\ c1 b\\ bbb c\\ c2");

        assertEquals("aaa", result.getArgumentValue(aArg).get());
        assertEquals("bbb", result.getArgumentValue(bArg).get());
        assertEquals(cVals, result.getArgumentValue(cArgs).get());
    }

    @Test
    public void parse_noPrefixedArguments() {
        NonPrefixedArgument aArg = new NonPrefixedArgument("a");
        List<Argument> args = Arrays.asList(aArg);

        ArgumentTokenizer argsParser = new ArgumentTokenizer(args);
        ParsedArguments result = argsParser.tokenize("Micky Mouse p/12345");

        assertEquals("Micky Mouse p/12345", result.getArgumentValue(aArg).get());
    }

    @Test
    public void parse_duplicatedRepeatableArguments() {
        NonRepeatableArgument aArg = new NonRepeatableArgument("a", "<a>");
        NonRepeatableArgument bArg = new NonRepeatableArgument("b", "<b>");
        RepeatableArgument cArgs = new RepeatableArgument("c", "<c>");
        List<Argument> args = Arrays.asList(aArg, bArg, cArgs);
        List<String> cVals = Arrays.asList("c1", "c2", "c1");

        ArgumentTokenizer argsParser = new ArgumentTokenizer(args);
        ParsedArguments result = argsParser.tokenize("<a>aaa<c>c1<b>bbb<c>c2<c>c1");

        assertEquals("aaa", result.getArgumentValue(aArg).get());
        assertEquals("bbb", result.getArgumentValue(bArg).get());
        assertEquals(cVals, result.getArgumentValue(cArgs).get());
    }
}
