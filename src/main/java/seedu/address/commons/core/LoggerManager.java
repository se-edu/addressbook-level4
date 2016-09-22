package seedu.address.commons.core;

import java.io.IOException;
import java.util.logging.*;

/**
 * Configures and manages loggers and handlers, including their logging level
 */
public class LoggerManager {
    private static Level currentLogLevel = Level.INFO;
    private static final Logger logger = LoggerManager.getLogger(LoggerManager.class);
    private static final String LOG_FILE = "addressbook.log";
    private static FileHandler fileHandler;
    private static ConsoleHandler consoleHandler;

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
        if (consoleHandler == null) consoleHandler = createConsoleHandler();
        logger.addHandler(consoleHandler);
    }

    private static void addFileHandler(Logger logger) {
        try {
            if (fileHandler == null) fileHandler = createFileHandler();
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.warning("Error adding file handler for logger.");
        }
    }

    private static FileHandler createFileHandler() throws IOException {
        FileHandler fileHandler = new FileHandler(LOG_FILE);
        fileHandler.setFormatter(new SimpleFormatter());
        fileHandler.setLevel(currentLogLevel);
        return fileHandler;
    }

    private static ConsoleHandler createConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(currentLogLevel);
        return consoleHandler;
    }

    public static <T> Logger getLogger(Class<T> clazz) {
        if (clazz == null) return Logger.getLogger("");
        return getLogger(clazz.getSimpleName());
    }
}
