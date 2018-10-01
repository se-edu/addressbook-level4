package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.commands.SetScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SetScheduleCommandParser implements Parser<SetScheduleCommand>{
    public SetScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        return new SetScheduleCommand(args);
    }
}
