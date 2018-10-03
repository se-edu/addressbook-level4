package seedu.address.model.person.timetable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import seedu.address.model.Entity;
import seedu.address.model.person.Name;

/**
 * Represents a timetable in the address book. Guarantees: nothing at the moment
 */
public class Timetable extends Entity {

    // Identity fields
    private final String fileName;
    private final String defaultLocation;
    private final String locationOfFile;
    private final String timetableFolder = "/src/main/java/seedu/address/formatl/timetable/timetables/";
    private final String format;

    // create timetable data
    private final TimetableData matrix;


    /**
     *
     * @param fileName
     * @param format
     */
    public Timetable( String fileName, String format, String locationFrom) {
        this.fileName = fileName + ".csv";
        this.format = format;
        File ans = new File("");
        this.defaultLocation =
            ans.getAbsolutePath().replace("\\", "/") + this.timetableFolder + this.fileName;
        locationOfFile = locationFrom + this.fileName;
        matrix = new TimetableData(format,locationOfFile);
    }

    public Timetable( String fileName, String format) {
        this.fileName = fileName + ".csv";
        this.format = format;
        File ans = new File("");
        this.defaultLocation =
            ans.getAbsolutePath().replace("\\", "/") + this.timetableFolder + this.fileName;
        locationOfFile = null;
        matrix = new TimetableData(format);
    }

    public String getFileName() { return fileName; }

    public String getFormat() {
        return format;
    }

    public TimetableData getTimetable() {
        return this.matrix;
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
