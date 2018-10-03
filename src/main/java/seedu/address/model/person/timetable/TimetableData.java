package seedu.address.model.person.timetable;


/**
 * timetable data
 */
public class TimetableData {

    private final String[][] timetable;
    private String[] timings = {"0800", "0900", "1000", "1100", "1200", "1300",
        "1400", "1500", "1600", "1700", "1800", "1900", "2000", "2100", "2200", "2300"};
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
        "Saturday", "Sunday"};
    private final int noOfTimings = timings.length;
    private final int noOfDays = days.length;


    /**
     *
     * @param format
     * @param locationOfFile
     */
    public TimetableData(String format,String locationOfFile) {
        String [][]aTimetable = generateNewHorizontalTimetable();
        if (format.equals("vertical")){
            aTimetable=readVerticalTimetableData(locationOfFile);
        }
        this.timetable=aTimetable;
    }




    public TimetableData(String format) {
        String [][]newTimetable = generateNewHorizontalTimetable();
        if (format.equals("vertical")){
            newTimetable =generateNewVerticalTimetable();
        }
        this.timetable = newTimetable;
    }

    /**
     *
     * @return
     */
    public int getNoOfTimings() {
        return noOfTimings;
    }

    /**
     *
     * @return
     */
    public int getNoOfDays() {
        return noOfDays;
    }

    private String[][] readHorizontalTimetableData(String locationOfFile) {
        return generateNewHorizontalTimetable();
    }
    private String[][] readVerticalTimetableData(String locationOfFile) {
        return generateNewVerticalTimetable();
    }
    /**
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @return
     */
    public String[][] getTimetable() {
        return timetable;
    }


}
