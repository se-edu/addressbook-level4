package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_POST;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Adds a recruitment post on the address book.
 */
public class AddRecruitmentPostCommand extends Command {

    public static final String COMMAND_WORD = "recruitmentPost";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Job Position "
            + PREFIX_POST;


    //public static final String MESSAGE_SUCCESS = "Recruitment Posts are added";
    public static final String MESSAGE_Failure = "Recruitment Posts are failed";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        throw new CommandException(MESSAGE_Failure);

    }

}
