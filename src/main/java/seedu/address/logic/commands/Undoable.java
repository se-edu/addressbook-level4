package seedu.address.logic.commands;

public interface Undoable {

	public CommandResult unexecute();

	/**
	 * Push command to undo stack if not executed before.
	 * @param undoable command has not been executed
	 * 	      successfully before.
	 * @return true
	 */
	public boolean pushCmdToUndo(boolean isExecuted);
}
