package seedu.address.logic.parser;

import seedu.address.logic.commands.SetScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SetScheduleCommandParser implements Parser<SetScheduleCommand>{
    public SetScheduleCommand parse(String args) throws ParseException {

        return new SetScheduleCommand();
    }
}
