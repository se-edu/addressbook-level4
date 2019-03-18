package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

public class DeadlineDate {
    public static final String MESSAGE_CONSTRAINTS =  "Date should only be in integers and should not be blank";
    public final String DeadlineDate;
    /**
     * Constructs a {@code Name}.
     *
     * @para time a valid time.
     */
    public DeadlineDate(String deadlineDate) {
        requireNonNull(deadlineDate);
        // checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
//        fullName = name;
        DeadlineDate = deadlineDate;
    }
    @Override
    public String toString() {
        return DeadlineDate;
    }

}
