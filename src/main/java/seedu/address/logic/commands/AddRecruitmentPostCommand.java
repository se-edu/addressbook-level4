package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINIMUM_EXPERIENCE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a recruitment post on the address book.
 */
public class AddRecruitmentPostCommand extends Command {

    public static final String COMMAND_WORD = "recruitmentPost";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Available Jobs "
            + PREFIX_JOB_POSITION + "[Job Position:]"
            + PREFIX_MINIMUM_EXPERIENCE + "[min working experience(Integer):]"
            + PREFIX_JOB_DESCRIPTION + "[Job Description:]\n"
            + "Example: " + COMMAND_WORD + PREFIX_JOB_POSITION
            + "IT Manager" + PREFIX_MINIMUM_EXPERIENCE + "3"
            + PREFIX_JOB_DESCRIPTION + "To maintain the network server in company";

    public static final String MESSAGE_FAILURE = "Recruitment Posts are failed";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_FAILURE);
    }

}
