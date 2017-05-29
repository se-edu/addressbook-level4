package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Map;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Map<Prefix, String> PREFIX_TO_CLASS;

    static {
        PREFIX_TO_CLASS = new HashMap<>();
        PREFIX_TO_CLASS.put(PREFIX_PHONE, Phone.class.getSimpleName());
        PREFIX_TO_CLASS.put(PREFIX_EMAIL, Email.class.getSimpleName());
        PREFIX_TO_CLASS.put(PREFIX_ADDRESS, Address.class.getSimpleName());
    }
}
