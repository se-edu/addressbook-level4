package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person names should only contain spaces, letters or characters, numbers "
            + "or the following punctuation marks: . , ' -";
    /**
     * Names can contain spaces and the following:
     *   unicode letters (e.g. German name with accents, where accented letters are written using one character),
     *   unicode marks (e.g. accented names, where an accented letter is made up of a letter and an accent character)
     *   numbers,
     *   dots (e.g. John Paul Jr.),
     *   commas (e.g. Smith, John),
     *   apostrophes (e.g. d'Souza),
     *   dashes (e.g. Jolie-Pitt)
     */
    public static final String NAME_VALIDATION_REGEX = "^[ \\p{L}\\p{M}0-9.,'-]+$";
    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
