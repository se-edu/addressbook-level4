package seedu.address.commands;

import seedu.address.model.person.ReadOnlyPerson;

import java.util.regex.Pattern;

/**
 * Superclass for commands targeting a person last listed in the ui.
 * Provides convenience methods to validate arguments and retrieve the targeted person from the last viewed list.
 */
public abstract class TargetLastListedPersonCommand extends Command {

    public static final Pattern ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    public final ReadOnlyPerson targetPerson;

    /**
     * @param targetPerson last visible listing index of the target person
     */
    protected TargetLastListedPersonCommand(ReadOnlyPerson targetPerson) {
        this.targetPerson = targetPerson;
    }


}
