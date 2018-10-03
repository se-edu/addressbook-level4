package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Accounts {

    // Identity fields
    private final String username;
    private final String password;


    /**
     * Every field must be present and not null.
     */
    public Accounts(String username, String password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
