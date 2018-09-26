package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Leave;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

public class AddLeaveCommand extends Command {

    public static final String COMMAND_WORD = "leave";
    private Path leaveBookFilePath = Paths.get("data" , "leavebook.xml");


    public static final String MESSAGE_SUCCESS = "Leave application requested.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This request already exists in the database";

    private final Leave toAdd;

    public AddLeaveCommand(Leave leave){
        requireNonNull(leave);
        toAdd = leave;
    }



    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.hasLeave(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addLeave(toAdd);
        model.commitLeaveList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }
}
