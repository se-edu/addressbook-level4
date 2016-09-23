package seedu.address.commons.core;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {
    // Default values
    private static final Level DEFAULT_LOGGING_LEVEL = Level.INFO;
    private static final String DEFAULT_LOCAL_DATA_FILE_PATH = "data/addressbook.xml";
    private static final String DEFAULT_ADDRESS_BOOK_NAME = "MyAddressBook";
    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values
    private String appTitle = "Address App";
    // Customizable through config file
    private Level currentLogLevel = DEFAULT_LOGGING_LEVEL;
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


    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof Config)){ //this handles null as well.
            return false;
        }

        Config o = (Config)other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(currentLogLevel, o.currentLogLevel)
                && Objects.equals(prefsFileLocation, o.prefsFileLocation)
                && Objects.equals(localDataFilePath, o.localDataFilePath)
                && Objects.equals(addressBookName, o.addressBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, currentLogLevel, prefsFileLocation, localDataFilePath, addressBookName);
    }


}
