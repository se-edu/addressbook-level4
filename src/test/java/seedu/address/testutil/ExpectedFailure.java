package seedu.address.testutil;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotates a JUnit test as expected to fail.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface ExpectedFailure {

}
