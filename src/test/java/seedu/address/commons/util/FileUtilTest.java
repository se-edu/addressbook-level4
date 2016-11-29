package seedu.address.commons.util;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class FileUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getPath() {

        // valid case
        assertEquals("folder" + File.separator + "sub-folder", FileUtil.getPath("folder/sub-folder"));

        // null parameter -> assertion failure
        thrown.expect(AssertionError.class);
        FileUtil.getPath(null);

        // no forwards slash -> assertion failure
        thrown.expect(AssertionError.class);
        FileUtil.getPath("folder");
    }

}
