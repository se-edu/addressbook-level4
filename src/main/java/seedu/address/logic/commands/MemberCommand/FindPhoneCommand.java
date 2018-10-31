package seedu.address.logic.commands.MemberCommand;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.member.PhoneContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

public class FindPhoneCommand extends Command {

        public static final String COMMAND_WORD = "findphone";
        public static final String COMMAND_ALIAS = "fp";

        public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a person with a specific phone number"
                + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
                + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
                + "Example: " + COMMAND_WORD + " 95241236";

        private final PhoneContainsKeywordsPredicate predicate;

        public FindPhoneCommand(PhoneContainsKeywordsPredicate predicate) {
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
                    || (other instanceof seedu.address.logic.commands.MemberCommand.FindPhoneCommand // instanceof handles nulls
                    && predicate.equals(((seedu.address.logic.commands.MemberCommand.FindPhoneCommand) other).predicate)); // state check
        }
    }


