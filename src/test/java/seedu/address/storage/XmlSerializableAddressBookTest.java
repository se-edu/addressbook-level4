package seedu.address.storage;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;

public class XmlSerializableAddressBookTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validAddressBook.xml");
    private static final File ILLEGAL_VALUES_FILE = new File(TEST_DATA_FOLDER + "illegalValuesAddressBook.xml");

    @Test
    public void getPersonList_noNulls() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE,
                XmlSerializableAddressBook.class);
        dataFromFile.getPersonList().forEach(Assert::assertNotNull);

        dataFromFile = XmlUtil.getDataFromFile(ILLEGAL_VALUES_FILE, XmlSerializableAddressBook.class);
        dataFromFile.getPersonList().forEach(Assert::assertNotNull);
    }
}
