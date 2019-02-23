package seedu.address.commons.util;

/**
 * Builds a string representation of an object that is suitable as the return value of {@link Object#toString()}.
 */
public class ToStringBuilder {
    private static final String OBJECT_PREFIX = "{";
    private static final String OBJECT_SUFFIX = "}";
    private static final String FIELD_SEPARATOR = ", ";
    private static final String FIELD_NAME_VALUE_SEPARATOR = "=";

    private final StringBuilder stringBuilder = new StringBuilder();
    private boolean hasField;

    /**
     * Constructs a {@code ToStringBuilder} whose formatted output will be prefixed with {@code objectName}.
     */
    public ToStringBuilder(String objectName) {
        stringBuilder.append(objectName).append(OBJECT_PREFIX);
    }

    /**
     * Constructs a {@code ToStringBuilder} whose formatted output will be prefixed with the
     * canonical class name of {@code object}.
     */
    public ToStringBuilder(Object object) {
        this(object.getClass().getCanonicalName());
    }

    /**
     * Adds a field name/value pair to the output string.
     */
    public ToStringBuilder add(String fieldName, Object fieldValue) {
        if (hasField) {
            stringBuilder.append(FIELD_SEPARATOR);
        }
        stringBuilder.append(fieldName).append(FIELD_NAME_VALUE_SEPARATOR).append(fieldValue);
        hasField = true;
        return this;
    }

    /**
     * Returns the built formatted string representation.
     */
    public String toString() {
        return stringBuilder.toString() + OBJECT_SUFFIX;
    }
}
