package seedu.todoList.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.todoList.commons.core.LogsCenter;
import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.commons.util.FileUtil;
import seedu.todoList.model.ReadOnlyTodoList;

/**
 * A class to access TodoList data stored as an xml file on the hard disk.
 */
public class XmlTodoListStorage implements TodoListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTodoListStorage.class);

    private String filePath;

    public XmlTodoListStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTodoListFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readTodoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTodoList> readTodoList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File TodoListFile = new File(filePath);

        if (!TodoListFile.exists()) {
            logger.info("TodoList file "  + TodoListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTodoList TodoListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(TodoListOptional);
    }

    /**
     * Similar to {@link #saveTodoList(ReadOnlyTodoList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTodoList(ReadOnlyTodoList TodoList, String filePath) throws IOException {
        assert TodoList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTodoList(TodoList));
    }

    @Override
    public Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException {
        return readTodoList(filePath);
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList TodoList) throws IOException {
        saveTodoList(TodoList, filePath);
    }
}
