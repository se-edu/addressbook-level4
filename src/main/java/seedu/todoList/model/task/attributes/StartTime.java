package seedu.todoList.model.task.attributes;

public class StartTime {

  public final String value;
    
    public StartTime(String startTime) {
        this.value = startTime;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
