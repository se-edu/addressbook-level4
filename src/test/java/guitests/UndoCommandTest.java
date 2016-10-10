package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.undoredomanager.UndoRedoManager;
import seedu.address.model.task.Location;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Time;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import java.util.Date;

public class UndoCommandTest extends AddressBookGuiTest{

	public static final int STACK_SIZE = UndoRedoManager.STACK_LIMIT;

	@Test
    public void undo() {

		//add and undo one person
		TestTask[] currentList = td.getTypicalTask();
		commandBox.runCommand(td.hoon.getAddCommand());
		assertUndoSuccess(currentList);

	    //undo on max limit
        TestTask generatedName = new TestTask();
        generatedName = generateTasks(generatedName,65); //generate letter A as Task
        commandBox.runCommand(generatedName.getAddCommand());
        currentList = TestUtil.addTasksToList(currentList,generatedName);
        TestTask[] expectedList = currentList;
        undo_max(currentList, expectedList);

        //ensure undo fail on Max Limit + 1
        assertUndoSuccess(currentList);
        assertResultMessage(UndoCommand.MESSAGE_NO_UNDOABLE_COMMAND);

        //undo on a sequence A,B,undo,C,undo,undo
        expectedList = currentList;
        undo_sequence(currentList, expectedList);

	    //ensure undo fails on select command
        commandBox.runCommand("select 1");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_UNDOABLE_COMMAND);

		//ensure undo fails on clear command
		commandBox.runCommand("clear");
		commandBox.runCommand("undo");
		assertResultMessage(UndoCommand.MESSAGE_NO_UNDOABLE_COMMAND);

		//ensure undo fails on help command
		commandBox.runCommand("help");
		HelpWindowHandle hwh = commandBox.runHelpCommand();
		hwh.closeWindow();
		commandBox.runCommand("undo");
		assertResultMessage(UndoCommand.MESSAGE_NO_UNDOABLE_COMMAND);

		//ensure undo fails on find command
		commandBox.runCommand("find george");
		commandBox.runCommand("undo");
		assertResultMessage(UndoCommand.MESSAGE_NO_UNDOABLE_COMMAND);
	}


    private void undo_max(TestTask[] currentList, TestTask[] expectedList) {
        TestTask generatedName = new TestTask();
        for(int i = 66; i< 66+STACK_SIZE; i++){ //start from letter B
            generatedName = generateTasks(generatedName,i);
            commandBox.runCommand(generatedName.getAddCommand());
            currentList = TestUtil.addTasksToList(currentList,generatedName);
        }
        for(int j = 0; j< STACK_SIZE; j++){
            commandBox.runCommand("undo");
        }
        assertTrue(taskListPanel.isListMatching(expectedList));
	}

    private void undo_sequence(TestTask[] currentList, TestTask[] expectedList){
        int seq1 = 2;
        int seq2 = 3;
        int undos = 1;
        TestTask generatedName = new TestTask();
        for(int i = 66; i < 66+seq1; i++){ //start from letter B
            generatedName = generateTasks(generatedName,i);
            commandBox.runCommand(generatedName.getAddCommand());
            currentList = TestUtil.addTasksToList(currentList,generatedName);
        }
        commandBox.runCommand("undo");
        for(int j = 66+seq1; j < 66+seq1+seq2; j++){
            generatedName = generateTasks(generatedName,j);
            commandBox.runCommand(generatedName.getAddCommand());
            currentList = TestUtil.addTasksToList(currentList,generatedName);
        }
        for(int k = 0; k < seq1+seq2-undos; k++){
            commandBox.runCommand("undo");
        }
        assertTrue(taskListPanel.isListMatching(expectedList));
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_UNDOABLE_COMMAND);
    }

	private TestTask generateTasks(TestTask generatedName,int i){
		try {
            generatedName = new TaskBuilder().withName(Character.toString((char)i))
                    .withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd's description").withPhone("9876")
                    .withTags("owesMoney", "friends").build();
		} catch (IllegalValueException e) {
			assert false : "impossible";
		}
		return generatedName;
	}

    private void assertUndoSuccess(TestTask[] currentList) {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));
    }
}
