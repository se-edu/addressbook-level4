package seedu.address.testutil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

/**
 * A set of assertion methods useful for writing tests.
 */
public class Assert {

    /**
     * Asserts that the {@code executable} throws the {@code expectedType} Exception.
     * This is a wrapper method that invokes
     * {@code Assertions.assertThrows(Class<? extends Throwable>, Executable)}, to maintain consistency
     * with our custom {@see assertThrows(Class<? extends Throwable>, String, Executable)} method.
     * To standardize API calls in this project, users should use this method instead of JUnit#assertThrows.
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, Executable executable) {
        Assertions.assertThrows(expectedType, executable);
    }

    /**
     * Asserts that the {@code executable} throws the {@code expectedType} Exception with the
     * {@code expectedMessage}. If there's no need for the verification of the exception's error
     * message, call {@code assertThrows(Class<? extends Throwable>, Executable)} instead.
     * {@see assertThrows(Class<? extends Throwable>, Executable)}
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, String expectedMessage,
            Executable executable) {
        Throwable thrownException = Assertions.assertThrows(expectedType, executable);
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }

    /**
     * Represents a function which does not return anything and may throw an exception.
     */
    @FunctionalInterface
    public interface VoidCallable {
        void call() throws Exception;
    }

}
