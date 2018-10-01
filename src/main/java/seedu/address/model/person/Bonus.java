package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Bonus in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBonus(String)}
 */
public class Bonus {
    public static final String MESSAGE_BONUS_CONSTRAINTS =
            "Bonus should only contain numbers and one dot, and it should not be blank";
    public static final String BONUS_VALIDATION_REGEX = "[0-9]*\\.[0-9]*";
    public final String value;

    /**
     * Constructs a {@code bonus}.
     *
     * @param bonus A valid bonus.
     */

    public Bonus(String bonus) {
        requireNonNull(bonus);
        checkArgument(isValidBonus(bonus), MESSAGE_BONUS_CONSTRAINTS);
        value = bonus;
    }

    /**
     * Returns true if a given string is a valid bonus.
     */
    public static boolean isValidBonus(String test) {
        return test.matches(BONUS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Bonus // instanceof handles nulls
                && value.equals(((Bonus) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
