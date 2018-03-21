package seedu.address.model.export;

public enum ExportType {
    CALENDAR, PORTFOLIO;

    public static boolean isValidExportType(String exportType) {
        try {
            ExportType.valueOf(exportType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
