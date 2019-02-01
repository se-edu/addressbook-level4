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
     * {@code Assertions.assertThrows(Class<? extends Throwable>, Executable)}, to maintain
     * consistency with our custom
     * {@see assertThrowsWithMessage(Class<? extends Throwable>, String, Executable)} method.
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, Executable executable) {
        Assertions.assertThrows(expectedType, executable);
    }

    /**
     * Asserts that the {@code executable} throws the {@code expectedType} Exception. Otherwise, display
     * {@code feedbackMessage} in the error log.
     * This is a wrapper method that invokes
     * {@code Assertions.assertThrows(Class<? extends Throwable>, Executable}, to maintain
     * consistency with our custom
     * {@see assertThrowsWithMessage(Class<? extends Throwable>, String, Executable)} method.
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, Executable executable,
                                    String feedbackMessage) {
        Assertions.assertThrows(expectedType, executable, feedbackMessage);
    }

    /**
     * Asserts that the {@code callable} throws the {@code expectedException} and the {@code expectedMessage}.
     *
     * @deprecated use {@see assertThrowsWithMessage(Class<? extends Throwable>, String, Executable)} instead.
     */
    @Deprecated
    public static void assertThrows(Class<? extends Throwable> expectedException, String expectedMessage,
                                    VoidCallable callable) {
        try {
            callable.call();
        } catch (Throwable actualException) {
            String errorMessage;

            if (!actualException.getClass().isAssignableFrom(expectedException)) {
                errorMessage = String.format("Expected exception thrown: %s, actual: %s",
                        expectedException.getName(), actualException.getClass().getName());
            } else if (expectedMessage != null && !expectedMessage.equals(actualException.getMessage())) {
                errorMessage = String.format(
                        "Expected message thrown: %s, actual: %s", expectedMessage, actualException.getMessage());
            } else {
                return;
            }

            throw new AssertionError(errorMessage, actualException);
        }

        throw new AssertionError(String.format(
                "Expected %s to be thrown, but nothing was thrown.", expectedException.getName()));
    }


    /**
     * Asserts that the {@code executable} throws the {@code expectedType} Exception with the
     * {@code expectedMessage} message. If there's no need for the verification of the exception's error
     * message, call {@code assertThrows(Class<? extends Throwable>, Executable)} instead.
     * {@see assertThrows(Class<? extends Throwable>, Executable}}
     */
    public static void assertThrowsWithMessage(Class<? extends Throwable> expectedType, String expectedMessage,
                                    Executable executable) {
        Throwable thrownException = Assertions.assertThrows(expectedType, executable);
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }

    /**
     * Asserts that the {@code executable} throws the {@code expectedType} Exception with the
     * {@code expectedMessage} message. Otherwise, display {@code feedbackMessage}} in the error log.
     * If there's no need for the verification of the exception's error message, call
     * {@code assertThrows(Class<? extends Throwable>, Executable)} instead.
     * {@see assertThrows(Class<? extends Throwable>, Executable}}
     */
    public static void assertThrowsWithMessage(Class<? extends Throwable> expectedType, String expectedMessage,
                                    Executable executable, String feedbackMessage) {
        Throwable thrownException = Assertions.assertThrows(expectedType, executable, feedbackMessage);
        Assertions.assertEquals(expectedMessage, thrownException.getMessage(), feedbackMessage);
    }

    /**
     * Represents a function which does not return anything and may throw an exception.
     *
     * @deprecated use {@code org.junit.jupiter.api.function.Executable} instead
     */
    @FunctionalInterface
    @Deprecated
    public interface VoidCallable {
        void call() throws Exception;
    }

}
