package seedu.address.testutil;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.person.Person;

public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(AddCommand.COMMAND_WORD + " " + person.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_PHONE.getPrefix() + person.getPhone().value + " ");
        sb.append(CliSyntax.PREFIX_EMAIL.getPrefix() + person.getEmail().value + " ");
        sb.append(CliSyntax.PREFIX_ADDRESS.getPrefix() + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG.getPrefix() + s.tagName + " ")
        );
        return sb.toString();
    }
}
