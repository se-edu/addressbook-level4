package seedu.address.model.timetable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

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

    /**
     *
     * @param locationFrom
     * @param fileName
     */
    public void addTimetable(String locationFrom, String fileName) {
        File ans = new File("");
        String destination =
            ans.getAbsolutePath().replace("\\", "/") + this.timetableFolder + fileName;
        String source = locationFrom.replace("\\", "/") + "/" + fileName + ".csv";
        Path from = Paths.get(source);
        Path to = Paths.get(destination);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param locationFrom
     * @param fileName
     * @param mode
     * @return
     */
    public String[][] readTimetable(String locationFrom, String fileName, String mode) {
        String[][] timetableMatrix;
        Scanner inputStream;
        //used code from https://stackoverflow.com/questions/40074840/reading-a-csv-file-into-a-array
        String filePath = locationFrom.replace("\\", "/") + "/" + fileName + ".csv";
        TimetableData timetable = new TimetableData(mode);
        timetableMatrix = timetable.getNewTimetable();
        try {
            File toRead = new File(filePath);
            if (!toRead.exists() && mode.equals("horizontal")) {
                inputStream = new Scanner(toRead);
                int i = 0;
                while (inputStream.hasNext()) {
                    String line = inputStream.next();
                    String[] entries = line.split(",");
                    timetableMatrix[i] = entries;
                }
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timetableMatrix;
    }

    /**
     *
     * @param locationTo
     * @param fileName
     * @param mode
     */
    public void getNewTimetable(String locationTo, String fileName, String mode) {
        TimetableData timetable = new TimetableData(mode);
        String[][] newTimetable;

        newTimetable = timetable.getNewTimetable();
        // used code from https://stackoverflow.com/questions/6271796/issues-of-saving-a-matrix-to-a-csv-file
        String filePath = locationTo.replace("\\", "/") + "/" + fileName + ".csv";
        try {
            File toWrite = new File(filePath);
            if (!toWrite.exists() && mode.equals("horizontal")) {
                toWrite.createNewFile();
                FileWriter writer = new FileWriter(toWrite, true);
                for (int i = 0; i < timetable.getNoOfDays() + 1; i++) {
                    for (int j = 0; j < timetable.getNoOfTimings() + 1; j++) {
                        if (j == timetable.getNoOfTimings()) {
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
                    for (int i = 0; i < timetable.getNoOfTimings() + 1; i++) {
                        for (int j = 0; j < timetable.getNoOfDays() + 1; j++) {
                            if (j == timetable.getNoOfDays()) {
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
