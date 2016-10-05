package guitests;

import guitests.guihandles.taskCardHandle;
import seedu.todoList.commons.core.Messages;
import seedu.todoList.logic.commands.AddCommand;
import seedu.todoList.testutil.TestUtil;
import seedu.todoList.testutil.Testtask;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TodoListGuiTest {

    @Test
    public void add() {
        //add one task
        Testtask[] currentList = td.getTypicaltasks();
        Testtask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addtasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addtasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_task);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(Testtask taskToAdd, Testtask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        taskCardHandle addedCard = taskListPanel.navigateTotask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        Testtask[] expectedList = TestUtil.addtasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
