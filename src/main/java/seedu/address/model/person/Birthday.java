package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {


    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday should be in the format DD-MM-YYYY";
    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}";
    public final String value;

    /**
     * Constructs a {@code Birthday}.
     *
     * @param birthday A valid Birthday date.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthday(birthday), MESSAGE_BIRTHDAY_CONSTRAINTS);
        this.value = birthday;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
