package seedu.todoList.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Todo App";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    
    private String todoListFilePath = "data/TodoList.xml";
    private String todoListName = "MyTodoList";
    private String eventListFilePath = "data/EventList.xml";
    private String eventListName = "MyEventList";
    private String deadlineListFilePath = "data/DeadlineList.xml";
    private String deadlineListName = "MyDeadlineList";


    public Config() {
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    
  //=========== TodoListFile Accessors ===============================================================
    public String getTodoListFilePath() {
        return todoListFilePath;
    }

    public void setTodoListFilePath(String todoListFilePath) {
        this.todoListFilePath = todoListFilePath;
    }

    public String getTodoListName() {
        return todoListName;
    }

    public void setTodoListName(String todoListName) {
        this.todoListName = todoListName;
    }
    
  //=========== EventListFile Accessors ===============================================================
    public String getEventListFilePath() {
        return eventListFilePath;
    }

    public void setEventListFilePath(String eventListFilePath) {
        this.eventListFilePath = eventListFilePath;
    }

    public String getEventListName() {
        return eventListName;
    }

    public void setEventListName(String eventListName) {
        this.eventListName = eventListName;
    }
    
  //=========== TodoListFile Accessors ===============================================================
    public String getDeadlineListFilePath() {
        return deadlineListFilePath;
    }

    public void setDeadlineListFilePath(String deadlineListFilePath) {
        this.deadlineListFilePath = deadlineListFilePath;
    }

    public String getDeadlineListName() {
        return deadlineListName;
    }

    public void setDeadlineListName(String deadlineListName) {
        this.deadlineListName = deadlineListName;
    }
    
    


    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof Config)){ //this handles null as well.
            return false;
        }

        Config o = (Config)other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(todoListFilePath, o.todoListFilePath)
                && Objects.equals(todoListName, o.todoListName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, todoListFilePath, todoListName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + todoListFilePath);
        sb.append("\nTodoList name : " + todoListName);
        return sb.toString();
    }

}