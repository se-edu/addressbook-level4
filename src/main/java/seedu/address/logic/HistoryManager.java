package seedu.address.logic;

import java.util.ArrayList;
import java.util.Optional;

import seedu.address.logic.commands.ReversibleCommand;

public class HistoryManager implements History {
    private ArrayList<ReversibleCommand> commandHistory;
    private int pointer; // points to the command to be undone in commandHistory

    public HistoryManager() {
        commandHistory = new ArrayList<ReversibleCommand>();
        pointer = -1;
    }

    public void push(ReversibleCommand e) {
        // if after undoing a few commands, you did not redo all of them, and now you are executing
        // new command
        if (pointer != commandHistory.size() - 1) {
            commandHistory.subList(pointer + 1, commandHistory.size()).clear();
        }

        commandHistory.add(e);
        pointer = commandHistory.size() - 1;
    }

    public Optional<ReversibleCommand> getPreviousCommand() {
        return pointer >= 0 ? Optional.of(commandHistory.get(pointer--)) :
            Optional.empty();
    }

    public Optional<ReversibleCommand> getNextCommand() {
        return pointer < commandHistory.size() - 1 ? Optional.of(commandHistory.get(++pointer)) :
            Optional.empty();
    }
}
