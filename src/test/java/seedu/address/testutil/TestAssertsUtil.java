package seedu.address.testutil;

import junit.framework.AssertionFailedError;

/**
* Utility class for test asserts.
*/
public class TestAssertsUtil {

    /**
     * Asserts that executable throws an expected type of exception.
     * Checks that the thrown exception is assignable from the expected exception class: 
     * it is of the same class as the expected exception, 
     * or it is a superclass of the expected exception class.
     * Assertion fails if executable throws an exception that cannot be 
     * assigned to the expected exception class,
     * or if executable does not throw any exception.
     * 
     * @param expected class of the expected exception thrown
     * @param executable to check the throwing of exception on
     */
    public static void assertThrows(Class<? extends Throwable> expected, Runnable executable) {
        try {
            executable.run();
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
    
}
