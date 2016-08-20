package seedu.address.commands;

import seedu.address.commons.Utils;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.List;
import java.util.Optional;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    public final String feedbackToUser;
    private final List<? extends ReadOnlyPerson> relevantPersons;

    public CommandResult(String feedbackToUser) {
        Utils.assertNotNull(feedbackToUser);
        this.feedbackToUser = feedbackToUser;
        relevantPersons = null;
    }

    public CommandResult(String feedbackToUser, List<? extends ReadOnlyPerson> relevantPersons) {
        Utils.assertNotNull(feedbackToUser, relevantPersons);
        this.feedbackToUser = feedbackToUser;
        this.relevantPersons = relevantPersons;
    }

    /**
     * Get any relevant persons identified by the command. Returns empty optional if the idea of
     * relevant persons makes no sense in the command's context..
     *
     * @return empty optional if relevant persons is N/A, optional containing persons otherwise
     */
    public Optional<List<? extends ReadOnlyPerson>> getRelevantPersons() {
        return Optional.ofNullable(relevantPersons);
    }

}
