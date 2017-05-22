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

        Predicate<ReadOnlyPerson> predicate = createEmptyPredicate();

        for (String name : nameKeywords) {
            predicate = predicate.or(nameContains(name));
        }

        return new FindCommand(predicate);
    }

    /**
     * Returns a predicate that is true if the Name of the Person being tested contains {@code name} as a word
     */
    private Predicate<ReadOnlyPerson> nameContains(String name) {
        return p -> StringUtil.containsWordIgnoreCase(p.getName().fullName, name);
    }

    /**
     * Returns a false predicate
     */
    private Predicate<ReadOnlyPerson> createEmptyPredicate() {
        return p -> false;
    }
}
