package seedu.address.model.timetable;

public class TimetableData {

    private final String [][] horizontalTimetable;
    private final String [][] verticalTimetable;
    private String [] timings ={"0800","0900","1000","1100","1200","1300",
        "1400","1500","1600","1700","1800","1900","2000","2100","2200","2300"};
    private String [] days ={"Monday","Tuesday","Wednesday","Thursday","Friday"
        ,"Saturday","Sunday"};
    final int noOfTimings = timings.length;
    final int noOfDays=days.length;



    public TimetableData()  {
        this.horizontalTimetable=generateNewHorizontalTimetable();
        this.verticalTimetable=generateNewVerticalTimetable();
    }
    private String[][] generateNewHorizontalTimetable(){
        String[][] horizontalTimetable = new String [noOfDays+1][noOfTimings+1];
        // set first row to be days
        for (int i=1 ;i < noOfDays+1; i++){
            horizontalTimetable[i][0]=days[i];
        }
        // set first column to be days
        for (int j=1 ;j < noOfTimings+1; j++){
            horizontalTimetable[0][j]=timings[j];
        }
        return horizontalTimetable;
    }
    private String[][] generateNewVerticalTimetable(){
        String[][] verticalTimetable = new String [noOfDays+1][noOfTimings+1];
        // set first row to be days
        for (int i=1 ;i < noOfDays+1; i++){
            verticalTimetable[0][i]=days[i];
        }
        // set first column to be days
        for (int j=1 ;j < noOfTimings+1; j++){
            verticalTimetable[j][0]=timings[j];
        }
        return verticalTimetable;
    }
    public String[][] getHorizontalTimetable() {
        return horizontalTimetable;
    }

    public String[][] getVerticalTimetable() {
        return verticalTimetable;
    }

}
