package seedu.address.testutil;

import junit.framework.AssertionFailedError;

/**
 * A set of assertion methods useful for writing tests.
 */
public class Assert {

    /**
     * Asserts that the {@code callable} throws the {@code expected} Exception.
     */
    public static void assertThrows(Class<? extends Throwable> expected, VoidCallable callable) {
        try {
            callable.call();
        } catch (Throwable actualException) {
            if (actualException.getClass().isAssignableFrom(expected)) {
                return;
            }
            String message = String.format("Expected thrown: %s, actual: %s", expected.getName(),
                    actualException.getClass().getName());
            throw new AssertionFailedError(message);
        }
        throw new AssertionFailedError(
                String.format("Expected %s to be thrown, but nothing was thrown.", expected.getName()));
    }

    /**
     * Asserts that the {@code callable} throws the {@code expectedException} and the {@code expectedMessage}.
     */
    public static void assertThrows(Class<? extends Throwable> expectedException, String expectedMessage,
                                    VoidCallable callable) {
        assertThrows(expectedException, callable);
        try {
            callable.call();
        } catch (Throwable actualException) {
            if (actualException.getMessage().equals(expectedMessage)) {
                return;
            }
            String errorMessage = String.format("Expected message thrown: %s, actual: %s", expectedMessage,
                        actualException.getMessage());
            throw new AssertionFailedError(errorMessage);
        }

    }

    /**
     * Represents a function which does not return anything and may throw an exception.
     */
    @FunctionalInterface
    public interface VoidCallable {
        void call() throws Exception;
    }

}
