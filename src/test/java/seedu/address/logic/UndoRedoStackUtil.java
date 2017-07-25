package seedu.address.logic;

import java.util.Collections;
import java.util.List;

import seedu.address.logic.commands.UndoableCommand;

/**
 * Contains helper methods for testing {@code UndoRedoStack}.
 */
public class UndoRedoStackUtil {
    /**
     * Adds {@code undoElements} into {@code UndoRedoStack#undoStack} and adds {@code redoElements}
     * into {@code UndoRedoStack#redoStack}. The first element in both {@code undoElements} and {@code redoElements}
     * will be the bottommost element in the respective stack in {@code undoRedoStack}, while the last element will
     * be the topmost element.
     */
    public static UndoRedoStack prepareStack(List<UndoableCommand> undoElements,
                                             List<UndoableCommand> redoElements) {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        undoElements.forEach(undoRedoStack::push);

        Collections.reverse(redoElements);
        redoElements.forEach(undoRedoStack::push);
        redoElements.forEach(unused -> undoRedoStack.popUndo());

        return undoRedoStack;
    }
}
