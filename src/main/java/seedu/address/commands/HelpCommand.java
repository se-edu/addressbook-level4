package seedu.address.commands;


/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public HelpCommand() {}

    @Override
    public CommandResult execute() {
        return new CommandResult(
                AddPersonCommand.MESSAGE_USAGE
                + "\n" + DeletePersonCommand.MESSAGE_USAGE
                + "\n" + ClearAddressBookCommand.MESSAGE_USAGE
                + "\n" + FindPersonsByWordsInNameCommand.MESSAGE_USAGE
                + "\n" + ListAllPersonsCommand.MESSAGE_USAGE
                + "\n" + HelpCommand.MESSAGE_USAGE
                + "\n" + ExitCommand.MESSAGE_USAGE
        );
    }
}
