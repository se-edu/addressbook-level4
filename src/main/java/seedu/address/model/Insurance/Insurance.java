package seedu.address.model.Insurance;

import seedu.address.model.tag.Tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Insurance plan in reInsurance.
 * Guarantees: immutable; commission is valid as declared in {@link #isValidCommission(String)}
 */
public class Insurance extends Tag {

    public static final String MESSAGE_COMMISSION_CONSTRAINTS =
        "Commission can only contain numbers, should be at least 1 digit long";
    public static final String COMMISSION_VALIDATION_REGEX = "\\d{1,}";
    private final String commission;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Insurance(String tagName, String commission) {
        super(tagName);
        requireNonNull(commission);
        checkArgument(isValidCommission(commission), MESSAGE_COMMISSION_CONSTRAINTS);
        this.commission = commission;
    }

    /**
     * Returns true if a given string is a valid commission.
     */
    private Boolean isValidCommission(String commission) {
        return commission.matches(COMMISSION_VALIDATION_REGEX);
    }

    public String getCommission() {
        return commission;
    }

}
