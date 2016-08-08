package address.parser;

import address.parser.expr.AndExpr;
import address.parser.expr.Expr;
import address.parser.expr.NotExpr;
import address.parser.expr.PredExpr;
import address.parser.qualifier.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final String NEGATION_REGEX = "!(!*\\w+)";
    private static final String EXPR_REGEX = "\\s*(!*\\w+)\\s*:\\s*(\\w+)\\s*";

    /**
     * Parses the given input and returns a representative predicate
     *
     * @param input
     * @return
     * @throws ParseException if input has incorrect syntax and/or qualifiers
     */
    public Expr parse(String input) throws ParseException {
        Expr result = PredExpr.TRUE;
        if (input.isEmpty()) return result;

        Pattern pattern = Pattern.compile(EXPR_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        while (!matcher.hitEnd()) {
            if (!matcher.find()) throw new ParseException("Part of filter unrecognised");
            Expr intermediate = getPredicate(matcher.group(1), matcher.group(2));
            result = new AndExpr(intermediate, result);
        }

        return result;
    }

    /**
     * Gets the predicate which represents the qualifier name and content
     *
     * @param qualifierName may be prefixed with multiple ! characters to signify negations
     * @param qualifierContent
     * @return
     * @throws ParseException if qualifier name without prefixed ! is not a valid qualifier string
     */
    private Expr getPredicate(String qualifierName, String qualifierContent) throws ParseException {
        Matcher matcher = Pattern.compile(NEGATION_REGEX, Pattern.CASE_INSENSITIVE).matcher(qualifierName);
        if (matcher.matches()) {
            return new NotExpr(getPredicate(matcher.group(1), qualifierContent));
        }
        return new PredExpr(getQualifier(qualifierName, qualifierContent));
    }

    /**
     * Gets the Qualifier which represents the qualifier name and content
     *
     * @param qualifierName should match one of the qualifier strings
     * @param content
     * @return
     * @throws ParseException if qualifier name does not match any of the qualifier strings
     */
    private Qualifier getQualifier(String qualifierName, String content) throws ParseException {
        switch (qualifierName) {
            case "city":
                return new CityQualifier(content);
            case "lastName":
                return new LastNameQualifier(content);
            case "firstName":
                return new FirstNameQualifier(content);
            case "name":
                return new NameQualifier(content);
            case "street":
                return new StreetQualifier(content);
            case "tag":
                return new TagQualifier(content);
            case "id":
                return new IdQualifier(parseInt(content));
            default:
                throw new ParseException("Unrecognised qualifier " + qualifierName);
        }
    }

    private Integer parseInt(String content) throws ParseException {
        try {
            return Integer.valueOf(content);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid integer: " + content);
        }
    }
}
