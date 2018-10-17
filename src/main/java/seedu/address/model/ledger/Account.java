package seedu.address.model.ledger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an account in the Club Book
 *
 */

public class Account {

    public static final String MESSAGE_BALANCE_CONSTRAINTS =
            "Account should only contain numeric characters and no spaces, and it should not be blank";

    private static Double value;

    public Account(Double balance) {
        requireNonNull(balance);
        checkArgument(isValidBalance(balance.toString()), MESSAGE_BALANCE_CONSTRAINTS);
        value = balance;
    }

    /**
     * Returns true if the given argument is a valid Balance.
     */

    public static boolean isValidBalance(String test) {
        try {
            Double.parseDouble(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
