package seedu.address.commons.util;


import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getPath() {

        // valid case
        assertEquals("folder" + File.separator + "sub-folder", FileUtil.getPath("folder/sub-folder"));

        // null value
        thrown.expect(NullPointerException.class);
        FileUtil.getPath(null);

        // IllegalArgument: no forward slash
        thrown.expect(IllegalArgumentException.class);
        FileUtil.getPath("folder");
    }

}
