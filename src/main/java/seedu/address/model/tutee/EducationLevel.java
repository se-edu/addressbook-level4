package seedu.address.model.tutee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author ChoChihTun
/**
 * Represents a Tutee's education level in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEducationLevel(String)}
 */
public class EducationLevel {

    public static final String MESSAGE_EDUCATION_LEVEL_CONSTRAINTS =
            "Education level should only be either primary, secondary or junior college, and it should not be blank";
    public static final String EDUCATION_LEVEL_VALIDATION_REGEX = "(?i)\\b(primary|secondary|(junior\\scollege))\\b";

    public final String educationLevel;

    /**
     * Constructs a {@code education level}.
     *
     * @param educationLevel A valid education level.
     */
    public EducationLevel(String educationLevel) {
        requireNonNull(educationLevel);
        checkArgument(isValidEducationLevel(educationLevel), MESSAGE_EDUCATION_LEVEL_CONSTRAINTS);
        this.educationLevel = educationLevel;
    }

    /**
     * Returns true if a given string is a valid education level.
     */
    public static boolean isValidEducationLevel(String test) {
        return test.matches(EDUCATION_LEVEL_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return educationLevel;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EducationLevel // instanceof handles nulls
                && this.educationLevel.equals(((EducationLevel) other).educationLevel)); // state check
    }

    @Override
    public int hashCode() {
        return educationLevel.hashCode();
    }
}
