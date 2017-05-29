package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;

public class ParserResult {
    public final Command command;
    public final String warning;

    public ParserResult(Command command) {
        this(command, "");
    }

    public ParserResult(Command command, String warning) {
        this.command = command;
        this.warning = warning;
    }

}
