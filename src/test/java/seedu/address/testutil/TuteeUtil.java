package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDUCATION_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddTuteeCommand;
import seedu.address.model.tutee.Tutee;

//@@author ChoChihTun
/**
 * A utility class for Tutee.
 */
public class TuteeUtil {

    /**
     * Returns an addtutee command string for adding the {@code tutee}.
     */
    public static String getAddTuteeCommand(Tutee tutee) {
        return AddTuteeCommand.COMMAND_WORD + " " + getTuteeDetails(tutee);
    }

    /**
     * Returns the part of command string for the given {@code tutee}'s details.
     */
    public static String getTuteeDetails(Tutee tutee) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + tutee.getName().fullName + " ");
        sb.append(PREFIX_PHONE + tutee.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + tutee.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + tutee.getAddress().value + " ");
        sb.append(PREFIX_SUBJECT + tutee.getSubject().subject + " ");
        sb.append(PREFIX_GRADE + tutee.getGrade().grade + " ");
        sb.append(PREFIX_EDUCATION_LEVEL + tutee.getEducationLevel().educationLevel + " ");
        sb.append(PREFIX_SCHOOL + tutee.getSchool().school + " ");
        tutee.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
