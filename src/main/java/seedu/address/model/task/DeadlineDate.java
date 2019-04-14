package seedu.address.model.task;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.ModelManager.isValidDate;

/**
 * DeadlineDate class
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadlineDate(String)}
 */
public class DeadlineDate {
    public static final String MESSAGE_CONSTRAINTS = "Deadline Date should only contain"
            + " 6 integers in the ddMMyy format";
    public static final String MESSAGE_CONSTRAINTS_INVALID_DATE = "The date "
            + "you have entered does not exist";
    public static final String VALIDATION_REGEX = "\\d{6}";
    public final String value;

    /**
     * Constructs a {@code DeadlineDate}.
     * @param deadlineDate a valid date
     */
    public DeadlineDate(String deadlineDate) {
        requireNonNull(deadlineDate);
        checkArgument(isValidDeadlineDateInput(deadlineDate), MESSAGE_CONSTRAINTS);
        checkArgument(isValidDeadlineDate(deadlineDate), MESSAGE_CONSTRAINTS_INVALID_DATE);
        value = deadlineDate;
    }

    /**
     *
     * @param deadlineDate a valid deadlineDate
     * @return Returns true if a given string is a valid time
     */
    public static boolean isValidDeadlineDateInput(String deadlineDate) {
        return deadlineDate.matches(VALIDATION_REGEX);
    }

    public static boolean isValidDeadlineDate(String deadlineDate) {
        return isValidDate(deadlineDate);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineDate // instanceof handles nulls
                && value.equals(((DeadlineDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
