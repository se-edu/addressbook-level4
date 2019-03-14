package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

public class FileUtilTest {

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

}
