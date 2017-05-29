package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;

public class ParserResult<T extends Command> {
    public final T command;
    public final String warning;

    public ParserResult(T command) {
        this(command, "");
    }

    public ParserResult(T command, String warning) {
        this.command = command;
        this.warning = warning;
    }

}
