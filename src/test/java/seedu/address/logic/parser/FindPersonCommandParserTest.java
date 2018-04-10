package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILTER_CATEGORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_EDUCATION_LEVEL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_GRADE;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_NAME;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SCHOOL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SUBJECT;

import org.junit.Test;

import seedu.address.logic.commands.FindPersonCommand;

//@@author yungyung04
/**
 * Contains tests for {@code FindPersonCommandParser}.
 */
public class FindPersonCommandParserTest {
    private static final int INDEX_FIRST_ELEMENT = 0;
    public static final String VALID_FIRST_NAME_BOB = VALID_NAME_BOB.toLowerCase().split("\\s+")[INDEX_FIRST_ELEMENT];
    private FindPersonCommandParser parser = new FindPersonCommandParser();

    private final String[] nameKeywords = {VALID_FIRST_NAME_BOB};
    private final String[] educationLevelKeywords = {VALID_EDUCATION_LEVEL_AMY.toLowerCase()};
    private final String[] gradeKeywords = {VALID_GRADE_AMY.toLowerCase(), VALID_GRADE_BOB.toLowerCase()};
    private final String[] schoolKeywords = VALID_SCHOOL_AMY.toLowerCase().split("\\s+");
    private final String[] subjectKeywords = {VALID_SUBJECT_AMY.toLowerCase(), VALID_SUBJECT_BOB.toLowerCase()};

    private final String invalidCategory = "age";

    @Test
    public void parse_invalidArg_throwsParseException() {
        //empty input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));

        //not enough arguments
        assertParseFailure(parser, CATEGORY_GRADE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));

        //invalid category
        assertParseFailure(parser, invalidCategory + " " + schoolKeywords[INDEX_FIRST_ELEMENT],
                String.format(MESSAGE_INVALID_FILTER_CATEGORY, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // filter by name using a single keyword
        FindPersonCommand expectedFindName = new FindPersonCommand(CATEGORY_NAME, nameKeywords);
        assertParseSuccess(parser, CATEGORY_NAME + " Bob", expectedFindName);

        // filter by education level using a single keyword
        FindPersonCommand expectedFindEducatonLevel =
                new FindPersonCommand(CATEGORY_EDUCATION_LEVEL, educationLevelKeywords);
        assertParseSuccess(parser,
                CATEGORY_EDUCATION_LEVEL + " " + VALID_EDUCATION_LEVEL_AMY, expectedFindEducatonLevel);

        // filter by grade using 2 different keywords
        FindPersonCommand expectedFindGrade = new FindPersonCommand(CATEGORY_GRADE, gradeKeywords);
        assertParseSuccess(parser, CATEGORY_GRADE + " " + VALID_GRADE_AMY
                + " " + VALID_GRADE_BOB, expectedFindGrade);

        // filter by school using multiple keywords from a single school
        FindPersonCommand expectedFindSchool = new FindPersonCommand(CATEGORY_SCHOOL, schoolKeywords);
        assertParseSuccess(parser, CATEGORY_SCHOOL + " " + VALID_SCHOOL_AMY, expectedFindSchool);

        // filter by subject using 2 different keywords
        FindPersonCommand expectedFindSubject = new FindPersonCommand(CATEGORY_SUBJECT, subjectKeywords);
        assertParseSuccess(parser, CATEGORY_SUBJECT + " " + VALID_SUBJECT_AMY
                + " " + VALID_SUBJECT_BOB, expectedFindSubject);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, CATEGORY_NAME + " \n\t  " + "Bob", expectedFindName);
    }
}
