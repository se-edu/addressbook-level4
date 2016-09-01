package seedu.address.util;

import java.io.IOException;
import java.util.logging.*;

public class LoggerManager {
    private static Level currentLogLevel = Level.INFO;
    private static final Logger logger = LoggerManager.getLogger(LoggerManager.class);
    private static final String LOG_FILE = "addressbook.log";
    private static FileHandler fileHandler;

    public static void init(Config config) {
        currentLogLevel = config.getCurrentLogLevel();
        logger.info("currentLogLevel: " + currentLogLevel);
    }

    public static Logger getLogger(String className) {
        Logger logger = Logger.getLogger(className);
        logger.setUseParentHandlers(false);

        addConsoleHandler(logger);
        addFileHandler(logger);

        return Logger.getLogger(className);
    }

    private static void addConsoleHandler(Logger logger) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(currentLogLevel);
        logger.addHandler(consoleHandler);
    }

    private static void addFileHandler(Logger logger) {
        try {
            if (fileHandler == null) {
                fileHandler = new FileHandler(LOG_FILE);
                fileHandler.setFormatter(new SimpleFormatter());
            }
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.warning("Error adding file handler for logger.");
        }
    }

    public static <T> Logger getLogger(Class<T> clazz) {
        if (clazz == null) return Logger.getLogger("");
        return getLogger(clazz.getSimpleName());
    }
}
