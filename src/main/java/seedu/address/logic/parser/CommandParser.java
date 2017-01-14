package seedu.address.logic.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Parses user input arguments in the context of the respective command classes
 */
public abstract class CommandParser {

    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    protected static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    protected static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    protected static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    /*
     * Sets the command word for this parser to the given command word.
     * This is mainly used for parsers that parses more than
     * one command.
     */
    public void setCommandWord(String commandWord) {
        // NO-OP
    }

    /**
     * Returns the specified index in the {@code command} if it is a positive unsigned integer
     * Returns an {@code Optional.empty()} otherwise.
     */
    protected Optional<Integer> parseIndex(String command) {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    public abstract Command prepareCommand(String args);
}
