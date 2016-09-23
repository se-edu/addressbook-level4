package seedu.address.storage;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class for accessing the Config File.
 */
public class ConfigStorage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    /**
     * Returns the Config object from the given file or an empty object if the file is not found.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<Config> readConfig(String configFilePath) throws DataConversionException {

        File configFile = new File(configFilePath);

        if (!configFile.exists()) {
            logger.info("Config file "  + configFile + " not found");
            return Optional.empty();
        }

        Config config;

        try {
            config = FileUtil.deserializeObjectFromJsonFile(configFile, Config.class);
        } catch (IOException e) {
            logger.warning("Error reading from config file " + configFile + ": " + e);
            throw new DataConversionException(e);
        }

        return Optional.of(config);
    }
}
