package seedu.address.util;

import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.HashMap;

/**
 * Config values used by the app
 */
public class Config {
    // Default values
    private static final Level DEFAULT_LOGGING_LEVEL = Level.INFO;
    private static final HashMap<String, Level> DEFAULT_SPECIAL_LOG_LEVELS = new HashMap<>();
    private static final String DEFAULT_LOCAL_DATA_FILE_PATH = "data/addressbook.xml";
    private static final String DEFAULT_ADDRESS_BOOK_NAME = "MyAddressBook";

    // Config values
    private String appTitle = "Address App";
    // Customizable through config file
    private Level currentLogLevel = DEFAULT_LOGGING_LEVEL;
    private HashMap<String, Level> specialLogLevels = DEFAULT_SPECIAL_LOG_LEVELS;
    private File prefsFileLocation = new File("preferences.json"); //Default user preferences file
    private String localDataFilePath = DEFAULT_LOCAL_DATA_FILE_PATH;
    private String addressBookName = DEFAULT_ADDRESS_BOOK_NAME;


    public Config() {
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getCurrentLogLevel() {
        return currentLogLevel;
    }

    public void setCurrentLogLevel(Level currentLogLevel) {
        this.currentLogLevel = currentLogLevel;
    }

    public HashMap<String, Level> getSpecialLogLevels() {
        return specialLogLevels;
    }

    public void setSpecialLogLevels(HashMap<String, Level> specialLogLevels) {
        this.specialLogLevels = specialLogLevels;
    }

    public File getPrefsFileLocation() {
        return prefsFileLocation;
    }

    public void setPrefsFileLocation(File prefsFileLocation) {
        this.prefsFileLocation = prefsFileLocation;
    }

    public String getLocalDataFilePath() {
        return localDataFilePath;
    }

    public void setLocalDataFilePath(String localDataFilePath) {
        this.localDataFilePath = localDataFilePath;
    }

    public String getAddressBookName() {
        return addressBookName;
    }

    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
    }


}
