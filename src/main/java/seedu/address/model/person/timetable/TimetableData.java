package seedu.address.model.person.timetable;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * timetable data which will process the inputs and create a timetable
 */
public class TimetableData {

    private final String[][] timetable;
    private String[] timings = {"0800", "0900", "1000", "1100", "1200", "1300",
        "1400", "1500", "1600", "1700", "1800", "1900", "2000", "2100", "2200", "2300"};
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
        "Saturday", "Sunday"};
    private final int noOfTimings = timings.length;
    private final int noOfDays = days.length;
    private final int rows;
    private final int columns;


    /**
     * creates a timetable based on the format the user wants and timetable file user has
     */
    public TimetableData(String format, String locationOfFile) {
        String[][] aTimetable = generateNewHorizontalTimetable();
        int noOfRows = 0;
        int noOfColumns = 0;
        if (format.equals("vertical")) {
            noOfRows = noOfTimings;
            noOfColumns = noOfDays;
            aTimetable = readVerticalTimetableData(locationOfFile);
        } else if (format.equals("horizontal")) {
            noOfRows = noOfDays;
            noOfColumns = noOfTimings;
            aTimetable = readHorizontalTimetableData(locationOfFile);
        }
        this.rows = noOfRows;
        this.columns = noOfColumns;
        this.timetable = aTimetable;
    }

    /**
     * reates a timetable based on the format the user wants
     */
    public TimetableData(String format) {
        String[][] newTimetable = generateNewHorizontalTimetable();
        int noOfRows = 0;
        int noOfColumns = 0;
        if (format.equals("vertical")) {
            noOfRows = noOfTimings;
            noOfColumns = noOfDays;
            newTimetable = generateNewVerticalTimetable();
        } else if (format.equals("horizontal")) {
            noOfRows = noOfDays;
            noOfColumns = noOfTimings;
        }
        this.rows = noOfRows;
        this.columns = noOfColumns;
        this.timetable = newTimetable;
    }

    public int getRows() {
        return rows + 1;
    }

    public int getColumns() {
        return columns + 1;
    }

    /**
     * takes in a csv file via the location of the file and read the file
     *
     * @return string matrix of timetable in horizontal form
     */
    private String[][] readHorizontalTimetableData(String locationOfFile) {
        String[][] timetableMatrix;
        Scanner inputStream;
        //Solution below adapted from mikeL
        // from https://stackoverflow.com/questions/40074840/reading-a-csv-file-into-a-array
        timetableMatrix = generateNewHorizontalTimetable();
        try {
            File toRead = new File(locationOfFile);
            if (toRead.exists()) {
                inputStream = new Scanner(toRead);
                int i = 0;
                while (inputStream.hasNext()) {
                    String line = inputStream.next();
                    String[] entries = line.split(",");
                    timetableMatrix[i] = entries;
                    i++;
                }
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timetableMatrix;
    }

    /**
     * takes in a csv file via the location of the file and read the file
     *
     * @return string matrix of timetable in vertical form
     */
    private String[][] readVerticalTimetableData(String locationOfFile) {
        String[][] timetableMatrix;
        Scanner inputStream;
        //Solution below adapted from mikeL
        // from https://stackoverflow.com/questions/40074840/reading-a-csv-file-into-a-array
        timetableMatrix = generateNewVerticalTimetable();
        try {
            File toRead = new File(locationOfFile);
            if (!toRead.exists()) {
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
     * @return initialise a horizontal string matrix
     */
    private String[][] getNewHorizontalMatrix() {
        String[][] matrix = new String[noOfDays + 1][noOfTimings + 1];
        for (int i = 0; i < noOfDays + 1; i++) {
            for (int j = 0; j < noOfTimings + 1; j++) {
                matrix[i][j] = " ";
            }
        }
        return matrix;
    }

    /**
     * @return a string matrix of timetable in horizontal form
     */
    private String[][] generateNewHorizontalTimetable() {
        String[][] horizontalTimetable = getNewHorizontalMatrix();
        // set first row to be days
        for (int i = 1; i < noOfDays + 1; i++) {
            horizontalTimetable[i][0] = days[i - 1];
        }
        // set first column to be days
        for (int j = 1; j < noOfTimings + 1; j++) {
            horizontalTimetable[0][j] = timings[j - 1];
        }
        return horizontalTimetable;
    }

    /**
     * @return initialise a vertical string matrix
     */
    private String[][] getNewVerticalMatrix() {
        String[][] matrix = new String[noOfTimings + 1][noOfDays + 1];
        for (int i = 0; i < noOfTimings + 1; i++) {
            for (int j = 0; j < noOfDays + 1; j++) {
                matrix[i][j] = " ";
            }
        }
        return matrix;
    }

    /**
     * @return a string matrix of timetable in vertical form
     */
    private String[][] generateNewVerticalTimetable() {
        String[][] verticalTimetable = getNewVerticalMatrix();
        // set first row to be days
        for (int i = 1; i < noOfDays + 1; i++) {
            verticalTimetable[0][i] = days[i - 1];
        }
        // set first column to be days
        for (int j = 1; j < noOfTimings + 1; j++) {
            verticalTimetable[j][0] = timings[j - 1];
        }
        return verticalTimetable;
    }

    /**
     * @return a string matrix of a timetable
     */
    public String[][] getTimetable() {
        return timetable;
    }

    /**
     * download timetable data as csv
     * unable to download if same filename exists
     * @param locationTo location of where to save the file
     */
    public void downloadTimetableData(String locationTo) {
        // Solution below adapted from bit-question
        // from https://stackoverflow.com/questions/6271796/issues-of-saving-a-matrix-to-a-csv-file
        String filePath = locationTo;
        try {
            File toWrite = new File(filePath);
            if (!toWrite.exists()) {
                toWrite.createNewFile();
                FileWriter writer = new FileWriter(toWrite, true);
                for (int i = 0; i < this.getRows() + 1; i++) {
                    for (int j = 0; j < this.getColumns(); j++) {
                        writer.append(this.timetable[i][j]);
                        writer.flush();
                        writer.append(',');
                        writer.flush();
                    }
                    writer.append("\n");
                    writer.flush();
                }
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
