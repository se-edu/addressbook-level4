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

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    private final String invalidCategory = "age";

    @Test
    public void parse_invalidArg_throwsParseException() {
        //empty input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //too many arguments
        assertParseFailure(parser, CATEGORY_GRADE + " " + CATEGORY_EDUCATION_LEVEL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //invalid category
        assertParseFailure(parser, invalidCategory,
                String.format(MESSAGE_INVALID_SORTER_CATEGORY, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // sort by name
        SortCommand expectedSortName = new SortCommand(CATEGORY_NAME);
        assertParseSuccess(parser, CATEGORY_NAME, expectedSortName);

        // sort by education level
        SortCommand expectedSortEducatonLevel = new SortCommand(CATEGORY_EDUCATION_LEVEL);
        assertParseSuccess(parser, CATEGORY_EDUCATION_LEVEL, expectedSortEducatonLevel);

        // sort by grade
        SortCommand expectedSortGrade = new SortCommand(CATEGORY_GRADE);
        assertParseSuccess(parser, CATEGORY_GRADE, expectedSortGrade);

        // sort by school
        SortCommand expectedSortSchool = new SortCommand(CATEGORY_SCHOOL);
        assertParseSuccess(parser, CATEGORY_SCHOOL, expectedSortSchool);

        // sort by subject
        SortCommand expectedSortSubject = new SortCommand(CATEGORY_SUBJECT);
        assertParseSuccess(parser, CATEGORY_SUBJECT, expectedSortSubject);

        // multiple whitespaces before and after sort category
        assertParseSuccess(parser, "   \n\t" + CATEGORY_NAME + "\n\t", expectedSortName);
    }
}
