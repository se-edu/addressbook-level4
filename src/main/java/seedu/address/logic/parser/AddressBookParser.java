package seedu.address.logic.parser;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.*;
import seedu.address.logic.commands.MemberCommand.*;
import seedu.address.logic.commands.ledger.AddLedgerCommand;
import seedu.address.logic.commands.ledger.CreditCommand;
import seedu.address.logic.commands.ledger.DebitCommand;
import seedu.address.logic.commands.ledger.OpenLedgerCommand;
import seedu.address.logic.parser.Member.AddCommandParser;
import seedu.address.logic.parser.Member.DeleteCommandParser;
import seedu.address.logic.parser.Member.EditCommandParser;
import seedu.address.logic.parser.Member.FindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.ledger.AddLedgerCommandParser;
import seedu.address.logic.parser.ledger.CreditCommandParser;
import seedu.address.logic.parser.ledger.DebitCommandParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class AddressBookParser {


    private final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddMemberCommand.COMMAND_WORD: case AddMemberCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditMemberCommand.COMMAND_WORD: case EditMemberCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD: case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteMemberCommand.COMMAND_WORD: case DeleteMemberCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD: case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindMemberCommand.COMMAND_WORD: case FindMemberCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListMemberCommand.COMMAND_WORD: case ListMemberCommand.COMMAND_ALIAS:
            return new ListMemberCommand();

        case HistoryCommand.COMMAND_WORD: case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD: case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD: case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case AddItemCommand.COMMAND_WORD:
            return new AddItemCommandParser().parse(arguments);

        case AddLedgerCommand.COMMAND_WORD:
            logger.info("Parsing");
            return new AddLedgerCommandParser().parse(arguments);

        case UndoAllCommand.COMMAND_WORD:
            return new UndoAllCommand();

        case RedoAllCommand.COMMAND_WORD:
            return new RedoAllCommand();

        case OpenLedgerCommand.COMMAND_WORD:
            return new OpenLedgerCommand();

        case CreditCommand.COMMAND_WORD:
            return new CreditCommandParser().parse(arguments);

        case DebitCommand.COMMAND_WORD:
            return new DebitCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
