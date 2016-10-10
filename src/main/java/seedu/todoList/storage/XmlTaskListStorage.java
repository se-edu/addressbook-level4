package seedu.todoList.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.todoList.commons.core.LogsCenter;
import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.commons.util.FileUtil;
import seedu.todoList.model.ReadOnlyTaskList;

/**
 * A class to access TodoList data stored as an xml file on the hard disk.
 */
public class XmlTaskListStorage implements TaskListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskListStorage.class);

    private String filePath;

    public XmlTaskListStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskListFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readTodoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File TaskListFile = new File(filePath);

        if (!TaskListFile.exists()) {
            logger.info("TaskList file "  + TaskListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskList TaskListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(TaskListOptional);
    }

    /**
     * Similar to {@link #saveTodoList(ReadOnlyTodoList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskList(ReadOnlyTaskList taskList, String filePath) throws IOException {
        assert taskList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskList(taskList));
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        return readTaskList(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {
        saveTaskList(taskList, filePath);
    }
}
