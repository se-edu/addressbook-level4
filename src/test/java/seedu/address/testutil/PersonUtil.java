package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    public static String getPersonDetails(EditCommand.EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getName().isPresent()) {
            sb.append(PREFIX_NAME + descriptor.getName().get().fullName + " ");
        }
        if (descriptor.getPhone().isPresent()) {
            sb.append(PREFIX_PHONE + descriptor.getPhone().get().value + " ");
        }
        if (descriptor.getEmail().isPresent()) {
            sb.append(PREFIX_EMAIL + descriptor.getEmail().get().value + " ");
        }
        if (descriptor.getAddress().isPresent()) {
            sb.append(PREFIX_ADDRESS + descriptor.getAddress().get().value + " ");
        }
        if (descriptor.getTags().isPresent()) {
            if (descriptor.getTags().get().isEmpty()) {
                sb.append(PREFIX_TAG);
            }
            descriptor.getTags().get().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
            );
        }
        return sb.toString();
    }
}
