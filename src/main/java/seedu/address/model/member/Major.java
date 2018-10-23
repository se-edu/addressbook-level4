package seedu.address.model.member;

import javax.swing.plaf.ColorUIResource;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Major {

    public static final String MESSAGE_MAJOR_CONSTRAINTS =
            "Major should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MAJOR_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String Course;
    public String value;

    /**
     * Constructs a {@code Major}.
     *
     * @param major A valid name.
     */
    public Major(String major) {
        requireNonNull(major);
        checkArgument(isValidMajor(major), MESSAGE_MAJOR_CONSTRAINTS);
        Course = major;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidMajor(String test) {
        return test.matches(MAJOR_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return Course;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Major// instanceof handles nulls
                && Course.equals(((Major) other).Course)); // state check
    }

    @Override
    public int hashCode() {
        return Course.hashCode();
    }
}
