package seedu.address.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in SmartyDo whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
    
    /**
     *  non-undoable command, will not be executed 
     */
    @Override
    public CommandResult unexecute() {
        return null;
    }
}
