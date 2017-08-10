package systemtests;

/**
 * Utility class for system tests.
 */
public class SystemTestUtil {
    /**
     * Calls {@code BrowserPanelHandle}, {@code PersonListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    public static void rememberStates(AddressBookSystemTest addressBookSystemTest)
            throws Exception {

        addressBookSystemTest.getBrowserPanel().rememberUrl();
        addressBookSystemTest.getStatusBarFooter().rememberSaveLocation();
        addressBookSystemTest.getStatusBarFooter().rememberSyncStatus();
        addressBookSystemTest.getPersonListPanel().rememberSelectedPersonCard();
    }
}
