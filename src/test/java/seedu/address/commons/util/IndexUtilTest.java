package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IndexUtilTest {

    @Test
    public void convertOneToZeroIndex() {
        assertEquals(0, IndexUtil.oneToZeroIndex(1));
        assertEquals(7, IndexUtil.oneToZeroIndex(8));
    }

}
