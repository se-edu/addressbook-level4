package seedu.address.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in SmartyDo whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose title contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
    		+ "To search for a tag, encapsulate the search term in [square brackets].\n"
            + "Parameters: KEYWORD <MORE_KEYWORDS> [TAGS]...\n"
            + "Example: " + COMMAND_WORD + " send card [Family]";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
}
