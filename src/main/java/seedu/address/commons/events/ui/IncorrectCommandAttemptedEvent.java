package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.Command;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    private Command command;

    public IncorrectCommandAttemptedEvent(Command command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
