package seedu.address.commons.core;

import java.io.IOException;
import java.util.logging.*;

/**
 * Configures and manages loggers and handlers, including their logging level
 */
public class LogsCenter {
    private static final int MAX_FILE_COUNT = 5;
    private static final int MAX_FILE_SIZE_IN_BYTES = (int) (Math.pow(2, 20) * 5); // 5MB
    private static final String LOG_FILE = "addressbook.log";
    private static Level currentLogLevel = Level.INFO;
    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);
    private static FileHandler fileHandler;
    private static ConsoleHandler consoleHandler;

    public static void init(Config config) {
        currentLogLevel = config.getLogLevel();
        logger.info("currentLogLevel: " + currentLogLevel);
    }

    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        removeHandlers(logger);
        addConsoleHandler(logger);
        addFileHandler(logger);

        return Logger.getLogger(name);
    }

    private static void addConsoleHandler(Logger logger) {
        if (consoleHandler == null) consoleHandler = createConsoleHandler();
        logger.addHandler(consoleHandler);
    }

    private static void removeHandlers(Logger logger) {
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
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
        FileHandler fileHandler = new FileHandler(LOG_FILE, MAX_FILE_SIZE_IN_BYTES, MAX_FILE_COUNT, true);
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
