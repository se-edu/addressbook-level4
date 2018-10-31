package seedu.address.logic.commands.MemberCommand;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.member.PostalcodeContainsKeywordPredicate;

import static java.util.Objects.requireNonNull;

public class FindPostalcodeCommand extends Command{

    public static final String COMMAND_WORD = "findpostalcode";
    public static final String COMMAND_ALIAS = "fc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a person who stays at a specific postalcode"
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 609653";

    private final PostalcodeContainsKeywordPredicate predicate;

    public FindPostalcodeCommand(PostalcodeContainsKeywordPredicate predicate) {
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
                || (other instanceof seedu.address.logic.commands.MemberCommand.FindPostalcodeCommand // instanceof handles nulls
                && predicate.equals(((seedu.address.logic.commands.MemberCommand.FindPostalcodeCommand) other).predicate)); // state check
    }
}

