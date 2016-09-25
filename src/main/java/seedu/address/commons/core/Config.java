package seedu.address.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    
    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Address App";
    private Level logLevel = Level.INFO;
    private String prefsFileLocation = "preferences.json";
    private String addressBookFileLocation = "data/addressbook.xml";
    private String addressBookName = "MyAddressBook";


    public Config() {
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getPrefsFileLocation() {
        return prefsFileLocation;
    }

    public void setPrefsFileLocation(String prefsFileLocation) {
        this.prefsFileLocation = prefsFileLocation;
    }

    public String getAddressBookFileLocation() {
        return addressBookFileLocation;
    }

    public void setAddressBookFileLocation(String addressBookFileLocation) {
        this.addressBookFileLocation = addressBookFileLocation;
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
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(prefsFileLocation, o.prefsFileLocation)
                && Objects.equals(addressBookFileLocation, o.addressBookFileLocation)
                && Objects.equals(addressBookName, o.addressBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, prefsFileLocation, addressBookFileLocation, addressBookName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + prefsFileLocation);
        sb.append("\nLocal data file location : " + addressBookFileLocation);
        sb.append("\nAddressBook name : " + addressBookName);
        return sb.toString();
    }

}
