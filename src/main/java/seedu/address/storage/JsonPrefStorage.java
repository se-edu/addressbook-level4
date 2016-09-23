package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.UserPrefs;

import java.io.File;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonPrefStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPrefStorage.class);

    public Optional<UserPrefs> readPrefs(String prefsFilePath) {
        assert prefsFilePath != null;

        File prefsFile = new File(prefsFilePath);

        if (!prefsFile.exists()) {
            logger.info("Prefs file "  + prefsFile + " not found");
            return Optional.empty();
        }

        return null;
    }
}
