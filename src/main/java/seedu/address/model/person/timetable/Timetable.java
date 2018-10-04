package seedu.address.model.person.timetable;

import seedu.address.model.Entity;

/**
 * Represents a timetable in the address book. Guarantees: details are present
 */
public class Timetable extends Entity {

    // Identity fields
    private final String fileName;
    private final String locationOfFile;
    private final String format;

    // create timetable data
    private final TimetableData matrix;


    /**
     *
     * @param fileName
     * @param format
     */
    public Timetable(String fileName, String format, String locationFrom) {
        this.fileName = fileName + ".csv";
        this.format = format;
        locationOfFile = locationFrom.replace("\\", "/") + "/" + this.fileName;
        matrix = new TimetableData(format, locationOfFile);
    }

    public Timetable(String fileName, String format) {
        this.fileName = fileName + ".csv";
        this.format = format;
        locationOfFile = null;
        matrix = new TimetableData(format);
    }

    public String getFileName() {
        return fileName;
    }


    public String getFormat() {
        return format;
    }

    public TimetableData getTimetable() {
        return this.matrix;
    }

    /**
     * download timetable to the given location
     */
    public void downloadTimetable(String locationTo) {
        String filePath = locationTo.replace("\\", "/") + "/" + this.fileName;
        this.matrix.downloadTimetableData(filePath);
    }

    @Override
    public boolean isSame(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Timetable)) {
            return false;
        }
        Timetable otherTimetable = (Timetable) other;
        return otherTimetable.equals(other);
    }
}
