package seedu.address;

import java.util.Map;
import java.util.logging.Logger;

import javafx.application.Application;
import seedu.address.commons.core.LogsCenter;

/**
 * Represents the parsed command-line parameters given to the application.
 */
public class AppParameters {
    private static final Logger logger = LogsCenter.getLogger(AppParameters.class);

    private String configPath;

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    /**
     * Parses the application command-line parameters.
     */
    public static AppParameters parse(Application.Parameters parameters) {
        AppParameters appParameters = new AppParameters();
        Map<String, String> namedParameters = parameters.getNamed();

        String configPathParameter = namedParameters.get("config");
        appParameters.setConfigPath(configPathParameter);

        return appParameters;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppParameters)) {
            return false;
        }

        AppParameters otherAppParameters = (AppParameters) other;
        return (otherAppParameters.getConfigPath() == null && this.getConfigPath() == null)
                || otherAppParameters.getConfigPath().equals(this.getConfigPath());
    }
}
