package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IndexUtilTest {

    @Test
    public void convertOneToZeroIndex() {
        int i = 1;
        assertEquals(i - 1, IndexUtil.oneToZeroIndex(i));
    }

}
