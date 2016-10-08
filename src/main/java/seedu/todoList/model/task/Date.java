package seedu.todoList.model.task;

public class Date {
    
public final String date;
    
    public Date(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return date;
    }
}
