package seedu.address.logic.commands.EventCommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Events.Event;

public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "addEvent";

    public static final String COMMAND_USEGE = COMMAND_WORD + ":add an event to the Event" + "parameters";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";

    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";

    private final Event NewEvent;

    public AddEventCommand(Event event) {
        requireNonNull(event);
        NewEvent = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, NewEvent));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && NewEvent.equals(((AddCommand) other).NewEvent));
    }
}

