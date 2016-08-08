package seedu.address.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.util.HashMap;

public class LoggerManager {
    private static final AppLogger logger = LoggerManager.getLogger(LoggerManager.class);
    private static Level currentLogLevel = Level.INFO;
    private static HashMap<String, Level> specialLogLevels = new HashMap<>();

    public static void init(Config config) {
        currentLogLevel = config.getCurrentLogLevel();
        specialLogLevels = config.getSpecialLogLevels();

        logger.info("currentLogLevel: {}", currentLogLevel);
        logger.info("specialLogLevels: {}", specialLogLevels);

        LoggerContext loggerContext = getLoggerContext();
        AbstractConfiguration loggersConfig = getLoggersConfig(loggerContext);
        updateExistingLoggersLevel(loggersConfig);
        loggerContext.updateLoggers(loggersConfig);
    }

    public static AppLogger getLogger(String className, Level loggingLevel) {
        setClassLoggingLevel(className, loggingLevel);
        return new AppLogger(LogManager.getLogger(className));
    }

    public static AppLogger getLogger(String className) {
        Level loggingLevelToSet = determineLoggingLevelToSet(className);
        setClassLoggingLevel(className, loggingLevelToSet);
        return new AppLogger(LogManager.getLogger(className));
    }

    public static <T> AppLogger getLogger(Class<T> clazz) {
        if (clazz == null) return new AppLogger(LogManager.getRootLogger());
        return getLogger(clazz.getSimpleName());
    }

    private static LoggerContext getLoggerContext() {
        return (LoggerContext) LogManager.getContext(false);
    }

    private static AbstractConfiguration getLoggersConfig(LoggerContext loggerContext) {
        return (AbstractConfiguration) loggerContext.getConfiguration();
    }

    private static void updateExistingLoggersLevel(AbstractConfiguration absConfig) {
        absConfig.getLoggers().forEach((loggerName, loggerConfig) -> {
            loggerConfig.setLevel(determineLoggingLevelToSet(loggerName));
        });
    }

    private static void setClassLoggingLevel(String className, Level loggingLevel) {
        LoggerContext loggerContext = getLoggerContext();
        AbstractConfiguration loggersConfig = getLoggersConfig(loggerContext);
        setLoggingLevel(loggersConfig, className, loggingLevel);
        loggerContext.updateLoggers(loggersConfig);
    }

    private static Level determineLoggingLevelToSet(String className) {
        if (specialLogLevels != null && specialLogLevels.containsKey(className)) {
            return specialLogLevels.get(className);
        }
        return currentLogLevel;
    }

    private static void setLoggingLevel(AbstractConfiguration config, String className, Level loggingLevel) {
        if (config.getLogger(className) != null) {
            config.getLogger(className).setLevel(loggingLevel);
            return;
        }

        config.addLogger(className, new LoggerConfig(className, loggingLevel, true));
    }
}
