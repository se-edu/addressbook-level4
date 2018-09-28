package seedu.address.model.timetable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import seedu.address.model.person.Name;

/**
 * Represents a timetable in the address book. Guarantees: nothing at the moment
 */
public class Timetable {

    // Identity fields
    private final Name name;
    private final String timetableFolder = "/src/main/java/seedu/address/model/timetable/timetables/";

    public Timetable(Name name) {
        this.name = name;
    }

    public void addTimetable(String locationFrom, String fileName) {
        File ans = new File("");
        String destination =
            ans.getAbsolutePath().replace("\\", "/") + this.timetableFolder + fileName;
        String source = locationFrom.replace("\\", "/") + "/" + fileName + ".csv";
        Path from = Paths.get(source);
        Path to = Paths.get(destination);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (
            IOException e) {
            e.printStackTrace();
        }
    }

    public void getNewTimetable(String locationTo, String fileName, String mode) {
        TimetableData Timetable = new TimetableData(mode);
        String[][] newTimetable;

        newTimetable = Timetable.getNewTimetable();
        // used code from https://stackoverflow.com/questions/6271796/issues-of-saving-a-matrix-to-a-csv-file
        String filePath = locationTo.replace("\\", "/") + "/" + fileName + ".csv";
        try {
            File toWrite = new File(filePath);
            if (!toWrite.exists() && mode.equals("horizontal")) {
                toWrite.createNewFile();
                FileWriter writer = new FileWriter(toWrite, true);
                for (int i = 0; i < Timetable.getNoOfDays() + 1; i++) {
                    for (int j = 0; j < Timetable.getNoOfTimings() + 1; j++) {
                        if (j == Timetable.getNoOfTimings()) {
                            writer.append(newTimetable[i][j]);
                            writer.flush();
                            writer.append('\n');
                            writer.flush();
                        } else {
                            writer.append(newTimetable[i][j]);
                            writer.flush();
                            writer.append(',');
                            writer.flush();
                        }

                    }
                }
                writer.close();
            } else {
                if (!toWrite.exists() && mode.equals("vertical")) {
                    toWrite.createNewFile();
                    FileWriter writer = new FileWriter(toWrite, true);
                    for (int i = 0; i < Timetable.getNoOfTimings() + 1; i++) {
                        for (int j = 0; j < Timetable.getNoOfDays() + 1; j++) {
                            if (j == Timetable.getNoOfDays()) {
                                writer.append(newTimetable[i][j]);
                                writer.flush();
                                writer.append('\n');
                                writer.flush();
                            } else {
                                writer.append(newTimetable[i][j]);
                                writer.flush();
                                writer.append(',');
                                writer.flush();
                            }

                        }
                    }
                    writer.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
