package seedu.address.logic.parser;


import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.task.DeadlineDate;
import seedu.address.model.task.DeadlineTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

import java.util.stream.Stream;
import java.util.logging.Logger;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/* Add RemarkCommandParser that knows how to parse two arguments, one index and one with prefix 'r/' */
public class AddTaskCommandParser implements Parser<AddTaskCommand>{
    private final Logger logger = LogsCenter.getLogger(getClass());
    public AddTaskCommand parse(String args) throws ParseException{
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        TaskName taskName = ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_NAME).get());
        DeadlineDate deadlineDate = ParserUtil.parseDeadlineDate(argMultimap.getValue(PREFIX_DEADLINE_DATE).get());
        DeadlineTime deadlineTime = ParserUtil.parseDeadlineTime(argMultimap.getValue(PREFIX_DEADLINE_TIME).get());

        Task task = new Task(taskName, deadlineTime, deadlineDate);
       // logger.info("Task Name DeadlineTime DeadlineDate" + taskName.fullName + ' ' + deadlineTime + " " + deadlineDate);
        return new AddTaskCommand(task);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

