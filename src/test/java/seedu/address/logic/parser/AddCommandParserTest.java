package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.StringJoiner;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {

    @Test
    public void parse_missingFields_returnsIncorrectCommand() throws Exception {
        Command actualCommand = new AddCommandParser().parse("Name");
        Command expectedCommand = new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                        AddCommand.MESSAGE_USAGE));

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_oneInvalidField_returnsIncorrectCommand() throws Exception {
        Command actualCommand = new AddCommandParser().parse("Name a/123 Street p/123456 e/");
        Command expectedCommand = new IncorrectCommand(Email.MESSAGE_EMAIL_CONSTRAINTS);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_multipleInvalidFields_returnsIncorrectCommand() throws Exception {
        Command actualCommand = new AddCommandParser().parse("Name a/123 Street p/ e/");

        StringJoiner expectedMessage = new StringJoiner(System.lineSeparator());
        expectedMessage.add(Phone.MESSAGE_PHONE_CONSTRAINTS).add(Email.MESSAGE_EMAIL_CONSTRAINTS);
        Command expectedCommand = new IncorrectCommand(expectedMessage.toString());

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_allValidFields_returnsAddCommand() throws Exception {
        Command actualCommand = new AddCommandParser().parse("Name a/123 Street p/123456 e/123@email.com");
        Person person = new PersonBuilder().withName("Name").withAddress("123 Street")
                .withPhone("123456").withEmail("123@email.com").build();
        Command expectedCommand = new AddCommand(person);

        assertEquals(expectedCommand, actualCommand);
    }
}
