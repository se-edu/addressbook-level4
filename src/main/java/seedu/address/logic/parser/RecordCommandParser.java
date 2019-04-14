package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SETS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.stream.Stream;

import seedu.address.logic.commands.RecordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.workout.Exercise;
import seedu.address.model.workout.Reps;
import seedu.address.model.workout.Sets;
import seedu.address.model.workout.Time;
import seedu.address.model.workout.Workout;


/**
 * Parses input arguments and creates a new RecordCommand object
 */
public class RecordCommandParser implements Parser<RecordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RecordCommand
     * and returns an RecordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE, PREFIX_SETS, PREFIX_REPS, PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_EXERCISE, PREFIX_SETS, PREFIX_REPS, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordCommand.MESSAGE_USAGE));
        }

        Exercise exercise = ParserUtil.parseExercise(argMultimap.getValue(PREFIX_EXERCISE).get());
        Sets sets = ParserUtil.parseSets(argMultimap.getValue(PREFIX_SETS).get());
        Reps reps = ParserUtil.parseReps(argMultimap.getValue(PREFIX_REPS).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

        Workout workout = new Workout(exercise, sets, reps, time);

        return new RecordCommand(workout);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
