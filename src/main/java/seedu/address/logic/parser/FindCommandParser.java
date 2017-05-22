package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        Predicate<ReadOnlyPerson> predicate = (person -> false); // to allow chaining of predicate

        for (String name : nameKeywords) {
            predicate = predicate.or(nameContains(name));
        }

        return new FindCommand(predicate);
    }

    /**
     * Returns a predicate that returns true if the {@code ReadOnlyPerson}'s Name contains {@code keyword}
     */
    private Predicate<ReadOnlyPerson> nameContains(String keyword) {
        return person -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword);
    }
}
