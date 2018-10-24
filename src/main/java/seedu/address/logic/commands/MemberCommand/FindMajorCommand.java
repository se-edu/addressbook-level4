package seedu.address.logic.commands.MemberCommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.member.MajorContainsKeywordsPredicate;


public class FindMajorCommand extends Command {

    public static final String COMMAND_WORD = "findmajor";
    public static final String COMMAND_ALIAS = "fm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose major contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " computer";

    private final MajorContainsKeywordsPredicate predicate;

    public FindMajorCommand(MajorContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMajorCommand // instanceof handles nulls
                && predicate.equals(((FindMajorCommand) other).predicate)); // state check
    }
}

