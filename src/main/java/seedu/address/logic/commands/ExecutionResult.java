package seedu.address.logic.commands;

import seedu.address.logic.parser.ParserResult;

/**
 * Represents the result of the entire execution process (parsing of input and execution of command).
 */
public class ExecutionResult {
    public final String message;
    public final String warning;

    public ExecutionResult(ParserResult parserResult, CommandResult commandResult) {
        message = commandResult.feedbackToUser;
        warning = parserResult.warning;
    }
}
