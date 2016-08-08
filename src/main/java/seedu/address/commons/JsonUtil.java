package seedu.address.commons;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Converts a Java object instance to JSON and vice versa
 */
public class JsonUtil {
    private static class LevelDeserializer extends FromStringDeserializer<Level> {

        protected LevelDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        protected Level _deserialize(String value, DeserializationContext ctxt) throws IOException {
            return getLoggingLevel(value);
        }

        /**
         * Gets the logging level that matches loggingLevelString
         * <p>
         * Returns null if there are no matches
         *
         * @param loggingLevelString
         * @return
         */
        private Level getLoggingLevel(String loggingLevelString) {
            for (Level level : Level.values()) {
                if (level.toString().equals(loggingLevelString)) {
                    return level;
                }
            }
            return null;
        }

        @Override
        public Class<Level> handledType() {
            return Level.class;
        }
    }

    private static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .registerModule(new SimpleModule("SimpleModule")
                    .addSerializer(Level.class, new ToStringSerializer())
                    .addDeserializer(Level.class, new LevelDeserializer(Level.class)));

    /**
     * Converts a given string representation of a JSON data to instance of a class
     * @param <T> The generic type to create an instance of
     * @return The instance of T with the specified values in the JSON string
     */
    public static <T> T fromJsonString(String json, Class<T> instanceClass) throws IOException {
        return objectMapper.readValue(json, instanceClass);
    }

    /**
     * Converts a given instance of a class into its JSON data string representation
     * @param instance The T object to be converted into the JSON string
     * @param <T> The generic type to create an instance of
     * @return JSON data representation of the given class instance, in string
     */
    public static <T> String toJsonString(T instance) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(instance);
    }

    public static <V> List<V> fromJsonStringToList(String json, Class<V> referenceClass) throws IOException {
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return objectMapper.readValue(json, typeFactory.constructCollectionType(List.class, referenceClass));
    }

    public static <K, V> HashMap<K, V> fromJsonStringToHashMap(String json, Class<K> keyClass, Class<V> valueClass)
            throws IOException {
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return objectMapper.readValue(json, typeFactory.constructMapType(HashMap.class, keyClass, valueClass));
    }

    public static <T> T fromJsonStringToGivenType(String json, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(json, typeReference);
    }
}
