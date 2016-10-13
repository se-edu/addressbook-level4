package seedu.todoList.model.task.attributes;

public class EndTime {

public final String value;
    
    public EndTime(String endTime) {
        this.value = endTime;
    }
    
    @Override
    public String toString() {
        return value;
    }

}
