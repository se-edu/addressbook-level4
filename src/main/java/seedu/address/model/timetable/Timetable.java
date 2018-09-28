package seedu.address.model.timetable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import seedu.address.model.person.Name;

/**
 * Represents a timetable in the address book.
 * Guarantees: nothing at the moment
 */
public class Timetable {

    // Identity fields
    private final Name name;
    private final String timetableFolder = "/src/main/java/seedu/address/model/timetable/timetables/";
    public Timetable(Name name) {
        this.name = name;
    }

    public void addTimetable(String locationFrom, String fileName){
        File ans = new File("");
        String destination =
            ans.getAbsolutePath().replace("\\", "/") + this.timetableFolder  + fileName;
        String source = locationFrom.replace("\\", "/") + "/" + fileName;
        Path from = Paths.get(source);
        Path to = Paths.get(destination);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (
            IOException e) {
            e.printStackTrace();
        }
    }
}
