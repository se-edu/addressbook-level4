package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.NumberFormat;
import java.util.Locale;


/**
 * Represents a Person's income in the address book
 * Guarantees: immutable; is valid as declare in {@link #isValidIncome(float)}}
 */
public class Income {
    public static final String MESSAGE_INCOME_CONSTRAITS =
            "Person income must be positive numerical numbers, floating point value";

    public final float income;

    /**
     * @param income a valid income
     */
    public Income(float income) {
        requireNonNull(income);
        checkArgument(this.isValidIncome(income), this.MESSAGE_INCOME_CONSTRAITS);
        this.income = income;
    }


    private static boolean isValidIncome(float income) {
        return (income >= 0);
    }

    @Override
    public String toString() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(this.income);
    }


    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Income
                && this.income == ((Income) other).income);
    }

}
