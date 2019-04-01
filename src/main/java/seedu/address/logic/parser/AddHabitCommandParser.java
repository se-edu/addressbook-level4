package seedu.address.logic.parser;

import seedu.address.logic.commands.AddHabitCommand;
import seedu.address.logic.commands.AddPurchaseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.habit.Habit;
import seedu.address.model.habit.HabitTitle;
import seedu.address.model.habit.Progress;
import seedu.address.model.purchase.Price;
import seedu.address.model.purchase.Purchase;
import seedu.address.model.purchase.PurchaseName;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class AddHabitCommandParser implements Parser<AddHabitCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddHabitCommand
     * and returns an AddHabitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddHabitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HABITTITLE, PREFIX_PROGRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_HABITTITLE, PREFIX_PROGRESS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHabitCommand.MESSAGE_USAGE));
        }

        HabitTitle name = ParserUtil.parseHabitTitle(argMultimap.getValue(PREFIX_HABITTITLE).get());
        Progress progress = ParserUtil.parseProgress(argMultimap.getValue(PREFIX_PROGRESS).get());

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Habit habit = new Habit(name, progress, tagList);

        return new AddHabitCommand(habit);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
