package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Scanner;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LoginCommand object,
 */
public class LoginCommandParser implements Parser<LoginCommand> {
    /**
     * Parses the given arguments in the context of LoginCommand.
     * @return a LoginCommand Object if the given format conforms to the expected format.
     * @throws ParseException if the given format does not conform to the expected format.
     */
    @Override
    public LoginCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        if (!isInputFormatCorrect(userInput)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        } else {
            String inputNric;
            String inputPassword;
            Scanner inputScanner = new Scanner(userInput).useDelimiter(" ");
            inputNric = inputScanner.next();
            inputPassword = inputScanner.next();
            inputScanner.close();
            return new LoginCommand(inputNric, inputPassword);
        }
    }

    /**
     * Returns true if exactly two parameters (NRIC and password) are provided.
     * Else return false;
     */
    private boolean isInputFormatCorrect(String userInput) {
        Scanner inputScanner = new Scanner(userInput).useDelimiter(" ");
        if (!inputScanner.hasNext()) {
            inputScanner.close();
            return false;
        }
        inputScanner.next();
        if (!inputScanner.hasNext()) {
            inputScanner.close();
            return false;
        }
        inputScanner.next();
        if (inputScanner.hasNext()) {
            inputScanner.close();
            return false;
        } else {
            inputScanner.close();
            return true;
        }
    }
}
