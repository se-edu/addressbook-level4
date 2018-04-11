package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SORTER_CATEGORY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_EDUCATION_LEVEL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_GRADE;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_NAME;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SCHOOL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SUBJECT;

import org.junit.Test;

import seedu.address.logic.commands.SortPersonCommand;

//@@author yungyung04
/**
 * Contains tests for {@code SortPersonCommandParser}.
 */
public class SortPersonCommandParserTest {
    private SortPersonCommandParser parser = new SortPersonCommandParser();

    private final String invalidCategory = "age";

    @Test
    public void parse_invalidArg_throwsParseException() {
        //empty input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE));

        //too many arguments
        assertParseFailure(parser, CATEGORY_GRADE + " " + CATEGORY_EDUCATION_LEVEL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE));

        //invalid category
        assertParseFailure(parser, invalidCategory,
                String.format(MESSAGE_INVALID_SORTER_CATEGORY, SortPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // sort by name
        SortPersonCommand expectedSortName = new SortPersonCommand(CATEGORY_NAME);
        assertParseSuccess(parser, CATEGORY_NAME, expectedSortName);

        // sort by education level
        SortPersonCommand expectedSortEducatonLevel = new SortPersonCommand(CATEGORY_EDUCATION_LEVEL);
        assertParseSuccess(parser, CATEGORY_EDUCATION_LEVEL, expectedSortEducatonLevel);

        // sort by grade
        SortPersonCommand expectedSortGrade = new SortPersonCommand(CATEGORY_GRADE);
        assertParseSuccess(parser, CATEGORY_GRADE, expectedSortGrade);

        // sort by school
        SortPersonCommand expectedSortSchool = new SortPersonCommand(CATEGORY_SCHOOL);
        assertParseSuccess(parser, CATEGORY_SCHOOL, expectedSortSchool);

        // sort by subject
        SortPersonCommand expectedSortSubject = new SortPersonCommand(CATEGORY_SUBJECT);
        assertParseSuccess(parser, CATEGORY_SUBJECT, expectedSortSubject);

        // multiple whitespaces before and after sort category
        assertParseSuccess(parser, "   \n\t" + CATEGORY_NAME + "\n\t", expectedSortName);
    }
}
