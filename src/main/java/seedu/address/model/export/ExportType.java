package seedu.address.model.export;

/**
 * Types of valid exports that the user can use
 */
public enum ExportType {
    CALENDAR, PORTFOLIO;

    /**
     * @param exportType user's inputted ExportType
     * @return whether it is a valid ExportType
     */
    public static boolean isValidExportType(String exportType) {
        try {
            ExportType.valueOf(exportType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
