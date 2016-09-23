package seedu.address.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.AddressBook;

import static org.junit.Assert.assertNotNull;

public class XmlAddressBookStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");
    public static String TYPICAL_DATA_FILE = TEST_DATA_FOLDER + "TypicalData.xml";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_assertionFailure() throws DataConversionException {
        thrown.expect(AssertionError.class);
        new XmlAddressBookStorage().readAddressBook(null);
    }

    @Test
    public void readAddressBook_allInOrder_success() throws DataConversionException {
        //AddressBook addressBook = new XmlAddressBookStorage().readAddressBook(TYPICAL_DATA_FILE).get();
        //assertNotNull(addressBook);
    }
}
