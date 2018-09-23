package seedu.address.model;

import static java.util.Objects.requireNonNull;

import seedu.address.model.person.Nric;

/**
 * Stores the NRIC of the user who's logged in to the applicaion.
 * Also manages the logging in and out of the current session.
 */
public class SessionManager extends ModelManager{
    private static Nric loggedInNric = null;

    public static void loginToSession(Nric logInWithThisNric) {
        requireNonNull(logInWithThisNric);
        loggedInNric = logInWithThisNric;
    }

    public static void logOutSession() {
        loggedInNric = null;
    }

    public static Nric getLoggedInSessionNric() {
        return loggedInNric;
    }

    public static boolean isLoggedIn() {
        if(loggedInNric == null) {
            return false;
        }
        return true;
    }
}
