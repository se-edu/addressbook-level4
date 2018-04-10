package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.ui.CalendarPanel;

//@@author ChoChihTun
/**
 * Changes the Calendar Time Unit View of the Application
 */
public class ChangeCommand extends Command {
    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the calendar view "
            + "Parameters: "
            + "TIME_UNIT (Only d, w, m or y) "
            + "Example: " + COMMAND_WORD + " d";

    public static final String MESSAGE_CONSTRAINT = "Time unit can only be d, w, m or y for day, week, month and year"
            + " respectively";

    public static final String MESSAGE_SUCCESS = "Calendar view changed";
    public static final String MESSAGE_SAME_VIEW = "Calendar is already in the requested view";
    private static final int INDEX_OF_TIME_UNIT = 0;
    private static final String INITIAL_TIME_UNIT = "d";

    private static String timeUnit = INITIAL_TIME_UNIT;

    /**
     * Creates an ChangeCommand to change calendar into the specified view page time unit {@code timeUnit}
     */
    public ChangeCommand(String timeUnit) {
        requireNonNull(timeUnit);
        this.timeUnit = timeUnit;
    }

    public static String getTimeUnit() {
        return timeUnit;
    }

    @Override
    public CommandResult execute() {
        CalendarPanel.changeViewPage(timeUnit.charAt(INDEX_OF_TIME_UNIT));
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeCommand // instanceof handles nulls
                && timeUnit.equals(((ChangeCommand) other).timeUnit));
    }

}
