package address.parser;

import address.model.datatypes.person.ReadOnlyPerson;
import address.model.datatypes.tag.Tag;
import address.parser.expr.Expr;
import address.testutil.PersonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParserTest {
    Parser parser;

    @Before
    public void setup() {
        parser = new Parser();
    }

    @Test
    public void parser_multipleQualifiers_correctExprProduced() throws ParseException {
        String filterString = "name:Mueller tag:friends";
        Expr expr = parser.parse(filterString);

        ReadOnlyPerson readOnlyViewablePerson = buildPerson(1, "John", "Mueller", "", "", "friends");

        assertTrue(expr.satisfies(readOnlyViewablePerson));
    }

    @Test
    public void parser_containsNotExprAndMultipleQualifiers_correctExprProduced() throws ParseException {
        String filterString = "!name:Mueller tag:friends";
        Expr expr = parser.parse(filterString);

        ReadOnlyPerson personOne = buildPerson(1, "John", "Mueller", "", "", "friends");
        ReadOnlyPerson personTwo = buildPerson(2, "John", "Tan", "", "", "friends");
        ReadOnlyPerson personThree = buildPerson(3, "John", "Lee", "", "", "colleagues");

        assertFalse(expr.satisfies(personOne));
        assertTrue(expr.satisfies(personTwo));
        assertFalse(expr.satisfies(personThree));
    }

    @Test
    public void parser_multipleNotExprAndMultipleQualifiers_correctExprProduced() throws ParseException {
        String filterString = "!name:Mueller !tag:friends !!city:Singapore";
        Expr expr = parser.parse(filterString);

        ReadOnlyPerson personOne = buildPerson(1, "John", "Mueller", "", "", "friends");
        ReadOnlyPerson personTwo = buildPerson(2, "John", "Tan", "", "Singapore", "friends");
        ReadOnlyPerson personThree = buildPerson(3, "Mull", "Lee", "", "Malaysia", "colleagues");
        ReadOnlyPerson personFour = buildPerson(4, "Jack", "Lim", "", "Singapore", "colleagues");

        assertFalse(expr.satisfies(personOne));
        assertFalse(expr.satisfies(personTwo));
        assertFalse(expr.satisfies(personThree));
        assertTrue(expr.satisfies(personFour));
    }

    @Test
    public void parser_allQualifiers_correctExprProduced() throws ParseException {
        String filterString = "name:Mueller tag:friends city:Singapore street:Victoria id:5";
        Expr expr = parser.parse(filterString);

        ReadOnlyPerson personOne = buildPerson(1, "John", "Tan", "", "Singapore", "friends");
        ReadOnlyPerson personTwo = buildPerson(2, "John", "Mueller", "Victoria Street", "Singapore",
                                                            "friends");
        ReadOnlyPerson personThree = buildPerson(3, "Mull", "Lee", "Johor Street", "Malaysia",
                                                              "colleagues");
        ReadOnlyPerson personFour = buildPerson(4, "Jack", "Lim", "Heng Mui Keng Terrace", "Singapore",
                                                             "colleagues");
        ReadOnlyPerson personFive = buildPerson(5, "Martin", "Mueller", "Victoria Street", "Singapore",
                                                             "friends");

        assertFalse(expr.satisfies(personOne));
        assertFalse(expr.satisfies(personTwo));
        assertFalse(expr.satisfies(personThree));
        assertFalse(expr.satisfies(personFour));
        assertTrue(expr.satisfies(personFive));
    }

    @Test
    public void parser_invalidFilterString_parseExceptionThrown() {
        // tag should not have s
        String filterString = "firstName:John lastName:Mueller tags:friends city:Singapore street:Victoria id:5";
        // missing qualifier value for city
        String filterStringTwo = "firstName:John lastName:Mueller tags:friends city: street:Victoria id:5";
        // unknown symbol
        String filterStringThree = "firstName:John lastName:Mueller & tags:friends city: street:Victoria id:5";
        // space after city colon
        String filterStringFour = "firstName:John lastName:Mueller tags:friends city: Singapore street:Victoria id:5";

        assertTrue(isParseExceptionThrown(filterString));
        assertTrue(isParseExceptionThrown(filterStringTwo));
        assertTrue(isParseExceptionThrown(filterStringThree));
        assertTrue(isParseExceptionThrown(filterStringFour));
    }

    @Test
    public void parser_invalidValue_parseExceptionThrown() {
        // not an integer
        String filterString = "id:one";
        assertTrue(isParseExceptionThrown(filterString));
    }

    private boolean isParseExceptionThrown(String filterString) {
        try {
            parser.parse(filterString);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    private ReadOnlyPerson buildPerson(int id, String firstName, String lastName, String street,
                                       String city, String... tags) {

        List<Tag> tagList = new ArrayList<>();
        for (String tagString : tags) {
            tagList.add(new Tag(tagString));
        }
        return new PersonBuilder(firstName, lastName, id).withStreet(street)
                                                         .withCity(city)
                                                         .withTags(tagList.toArray(new Tag[tagList.size()]))
                                                         .build();
    }
}
