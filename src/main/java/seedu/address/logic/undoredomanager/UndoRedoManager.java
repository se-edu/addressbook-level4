package seedu.address.logic.undoredomanager;

import seedu.address.logic.commands.Command;

/**
 * Stack containers for undo and redo commands.
 * Undoable commands that are successfully executed will be added to the undoStack.
 * Each time an undoable command is added to the undoStack, the redoStack will be cleared.
 */
public class UndoRedoManager {
    
    public static final int STACK_LIMIT = 10;
    
    FixedStack<Command> undoStack;
    FixedStack<Command> redoStack;
    
    public UndoRedoManager() {
        undoStack = new FixedStack<Command>(STACK_LIMIT);
        redoStack = new FixedStack<Command>(STACK_LIMIT);
    }
    
    public FixedStack<Command> getUndo() {
        return undoStack;
    }
    
    public FixedStack<Command> getRedo() {
        return redoStack;
    }
    
    /**
     * Pushes the command to the undoStack if successfully executed
     * @param command successfully executed
     * @return command added to UndoStack
     */
    public void addToUndo(Command command) {
        undoStack.push(command);
        redoStack.clear();
    }
    
    public void transferToRedo(Command command) {
        redoStack.push(command);
    }
    
    public void transferToUndo(Command command) {
        undoStack.push(command);
    }

    public void resetData() {
        undoStack.clear();
        redoStack.clear();
    }
}
