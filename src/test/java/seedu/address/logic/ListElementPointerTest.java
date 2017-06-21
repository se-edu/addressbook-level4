package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ListElementPointerTest {
    private final String first = "first";
    private final String second = "second";
    private final List<String> pointerElements = Arrays.asList(first, second);
    private ListElementPointer pointer;

    @Test
    public void testsAllPublicMethods() {
        pointer = new ListElementPointer(pointerElements);
        assertPointerUp(first, second);
        assertPointerUpFailure();

        assertPointerDown(second, first);
        assertPointerDownFailure();

        pointer = new ListElementPointer(pointerElements, 0);
        assertPointerUp(second);
        assertPointerDown(first);
    }

    /**
     * Calls {@code pointer#getSubsequentElement()} for {@code elements.length} number of times,
     * with each time checking that {@code pointer#hasSubsequentElement()} returns true and the
     * return value of {@code pointer#getSubsequentElement()} equals to the element.
     */
    private void assertPointerUp(String... elements) {
        for (String element : elements) {
            assertTrue(pointer.hasSubsequentElement());
            assertEquals(element, pointer.getSubsequentElement());
        }
    }

    /**
     * Calls {@code pointer#getPrecedingElement()} for {@code elements.length} number of times,
     * with each time checking that {@code pointer#hasPrecedingElement()} returns true and the
     * return value of {@code pointer#getPrecedingElement()} equals to the element.
     */
    private void assertPointerDown(String... elements) {
        for (String element : elements) {
            assertTrue(pointer.hasPrecedingElement());
            assertEquals(element, pointer.getPrecedingElement());
        }
    }

    /**
     * Asserts that {@code pointer#hasSubsequentElement()} returns false and the following
     * {@code pointer#getSubsequentElement()} call throws {@code ArrayIndexOutOfBoundsException}.
     */
    private void assertPointerUpFailure() {
        assertFalse(pointer.hasSubsequentElement());
        try {
            pointer.getSubsequentElement();
            fail("The expected ArrayIndexOutOfBoundsException was not thrown");
        } catch (Exception e) {
            assertEquals(ArrayIndexOutOfBoundsException.class, e.getClass());
        }
    }

    /**
     * Asserts that {@code pointer#hasPrecedingElement()} returns false and the following
     * {@code pointer#getPrecedingElement()} call throws {@code ArrayIndexOutOfBoundsException}.
     */
    private void assertPointerDownFailure() {
        assertFalse(pointer.hasPrecedingElement());
        try {
            pointer.getPrecedingElement();
            fail("The expected ArrayIndexOutOfBoundsException was not thrown");
        } catch (Exception e) {
            assertEquals(ArrayIndexOutOfBoundsException.class, e.getClass());
        }
    }
}
