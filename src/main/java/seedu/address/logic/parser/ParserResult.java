package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;

/**
 * Stores the generated command after parsing the input, and any associated warnings
 * as a result of generating the command.
 */
public class ParserResult<T extends Command> {
    public final T command;
    public final String warning;

    public ParserResult(T command) {
        this(command, null);
    }

    public ParserResult(T command, String warning) {
        this.command = requireNonNull(command);
        this.warning = warning != null ? warning : "";
    }

}
