package seedu.address.testutil;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.CliSyntax;
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
     * Returns an edit command string for editing the person at {@code zeroBasedIndex} to match {@code editedPerson}.
     */
    public static String getEditCommand(int zeroBasedIndex, Person editedPerson) {
        return EditCommand.COMMAND_WORD + " " + (zeroBasedIndex + 1) + " " + getPersonDetails(editedPerson);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    private static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(person.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_PHONE.getPrefix() + person.getPhone().value + " ");
        sb.append(CliSyntax.PREFIX_EMAIL.getPrefix() + person.getEmail().value + " ");
        sb.append(CliSyntax.PREFIX_ADDRESS.getPrefix() + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG.getPrefix() + s.tagName + " ")
        );
        return sb.toString();
    }
}
