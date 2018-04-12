package seedu.address;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import javafx.application.Application;

public class AppParametersTest {

    @Test
    public void parse() {
        // valid path
        ParametersStub parametersStub = new ParametersStub();
        parametersStub.namedParameters.put("config", "config.json");

        AppParameters expected = new AppParameters();
        expected.setConfigPath(Paths.get("config.json"));

        assertEquals(expected.getConfigPath(), AppParameters.parse(parametersStub).getConfigPath());

        // invalid path
        parametersStub.namedParameters.put("config", "a\0");
        expected.setConfigPath(null);
        assertEquals(expected.getConfigPath(), AppParameters.parse(parametersStub).getConfigPath());

        // null path
        parametersStub.namedParameters.put("config", null);
        expected.setConfigPath(null);
        assertEquals(expected.getConfigPath(), AppParameters.parse(parametersStub).getConfigPath());
    }

    private static class ParametersStub extends Application.Parameters {
        private Map<String, String> namedParameters = new HashMap<>();

        @Override
        public List<String> getRaw() {
            throw new AssertionError("should not be called");
        }

        @Override
        public List<String> getUnnamed() {
            throw new AssertionError("should not be called");
        }

        @Override
        public Map<String, String> getNamed() {
            return Collections.unmodifiableMap(namedParameters);
        }
    }
}
