package guitests;

import org.junit.Test;

import seedu.todoList.model.task.ReadOnlyTask;

import static org.junit.Assert.assertEquals;

public class SelectCommandTest extends TodoListGuiTest {


    @Test
    public void selecttask_nonEmptyList() {

        assertSelectionInvalid(10); //invalid index
        assertNotaskSelected();

        assertSelectionSuccess(1); //first task in the list
        int taskCount = td.getTypicaltasks().length;
        assertSelectionSuccess(taskCount); //last task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); //a task in the middle of the list

        assertSelectionInvalid(taskCount + 1); //invalid index
        asserttaskSelected(middleIndex); //assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selecttask_emptyList(){
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected task: "+index);
        asserttaskSelected(index);
    }

    private void asserttaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedtasks().size(), 1);
        ReadOnlyTask selectedtask = taskListPanel.getSelectedtasks().get(0);
        assertEquals(taskListPanel.gettask(index-1), selectedtask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNotaskSelected() {
        assertEquals(taskListPanel.getSelectedtasks().size(), 0);
    }

}
