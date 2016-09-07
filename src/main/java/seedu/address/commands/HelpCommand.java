package seedu.address.commands;


import seedu.address.events.EventManager;
import seedu.address.events.ui.ShowHelpEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public HelpCommand() {}

    @Override
    public CommandResult execute() {
        EventManager.getInstance().post(new ShowHelpEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
