package seedu.address;

import java.util.Map;
import java.util.Objects;

import javafx.application.Application;

/**
 * Represents the parsed command-line parameters given to the application.
 */
public class AppParameters {

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
        return Objects.equals(getConfigPath(), otherAppParameters.getConfigPath());
    }

    @Override
    public int hashCode() {
        return configPath.hashCode();
    }
}
