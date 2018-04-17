package seedu.address;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import javafx.application.Application;

public class AppParametersTest {

    private ParametersStub parametersStub = new ParametersStub();
    private AppParameters expected = new AppParameters();

    @Test
    public void parse_validParameters_success() {
        parametersStub.namedParameters.put("config", "config.json");
        expected.setConfigPath("config.json");
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_nullPath_success() {
        parametersStub.namedParameters.put("config", null);
        expected.setConfigPath(null);
        assertEquals(expected, AppParameters.parse(parametersStub));
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
