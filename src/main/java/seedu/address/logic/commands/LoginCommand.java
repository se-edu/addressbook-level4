package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.SessionManager;
import seedu.address.model.person.Nric;


/**
 * Enables the use of application by logging in using the specified personnel's NRIC and password.
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enables the use of the application by logging in"
            + " using the specified personnel's NRIC and password.\n"
            + "Parameters: NRIC PASSWORD\n"
            + "Example:" + COMMAND_WORD + " S9724688J NeUeR2018";

    public static final String INVALID_LOGIN_CREDENTIALS = "Login failed. Incorrect NRIC and/or password.";
    public static final String LOGIN_SUCCESS = "Login successful. You are logged in as: %s";

    //Dummy UserID and Password for incremental testing purpose
    private String dummyNric = "S9123456Q";
    private String dummyPassword = "qwerty";
    private Nric loginNric;
    private String loginPassword;

    public LoginCommand(Nric loginNric, String loginPassword) {
        requireNonNull(loginNric);
        requireNonNull(loginPassword);

        this.loginNric = loginNric;
        this.loginPassword = loginPassword;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!isLoginCredentialsValid()) {
            throw new CommandException(INVALID_LOGIN_CREDENTIALS);
        } else {
            SessionManager.loginToSession(loginNric);
            return new CommandResult(String.format(LOGIN_SUCCESS, loginNric));
        }
    }

    private boolean isLoginCredentialsValid(){
        if (!loginNric.toString().equals(dummyNric) || !loginPassword.equals(dummyPassword)) {
            return false;
        } else {
            return true;
        }
    }
}
