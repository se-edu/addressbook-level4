package seedu.address.logic.commands;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.List;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    public final String feedbackToUser;
    private final List<? extends ReadOnlyPerson> relevantPersons;

    public CommandResult(String feedbackToUser) {
        CollectionUtil.assertNotNull(feedbackToUser);
        this.feedbackToUser = feedbackToUser;
        relevantPersons = null;
    }

    public CommandResult(String feedbackToUser, List<? extends ReadOnlyPerson> relevantPersons) {
        CollectionUtil.assertNotNull(feedbackToUser, relevantPersons);
        this.feedbackToUser = feedbackToUser;
        this.relevantPersons = relevantPersons;
    }

}
