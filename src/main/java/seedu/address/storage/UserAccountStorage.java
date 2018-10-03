package seedu.address.storage;

import java.util.HashMap;

/**
 * Storage of username and passwords
 */
public class UserAccountStorage {

    private static HashMap<String, String> userHashMap = new HashMap<>();

    public UserAccountStorage() {
    }

    public static void addNewAccount(String username, String password) {
        userHashMap.put(username, password);
    }

    public static boolean checkPasswordMatch(String username, String password) {
        return userHashMap.get(username).equals(password);
    }

    public static boolean checkDuplicateUser(String username) {
        return userHashMap.containsKey(username);
    }

}

/**
 * Class for username and password
 */
class Account {
    private String username;
    private String password;

    public Account(String username, String password) {
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
