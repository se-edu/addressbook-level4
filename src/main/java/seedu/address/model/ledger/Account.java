package seedu.address.model.ledger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Account {

    public static final String MESSAGE_BALANCE_CONSTRAINTS =
            "Account should only contain numeric characters and no spaces, and it should not be blank";

    public Double value;

    public Account(Double balance) {
        requireNonNull(balance);
        checkArgument(isValidBalance(balance.toString()), MESSAGE_BALANCE_CONSTRAINTS);
        value = balance;
    }

    public static boolean isValidBalance(String test) {
        try {
            Double.parseDouble(test);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }
}
