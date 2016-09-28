package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.Command;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    private final Command command;

    public IncorrectCommandAttemptedEvent(Command command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * Returns true if both events intended the selection to jump to the same item in the list.
     */
    @Override
    public boolean hasSameIntentionAs(BaseEvent other){
        return (other != null)
            && (other instanceof IncorrectCommandAttemptedEvent)
            && (((IncorrectCommandAttemptedEvent) other).command.equals(this.command));
    }
}
