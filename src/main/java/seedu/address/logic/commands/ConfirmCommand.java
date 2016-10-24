package seedu.address.logic.commands;

public class ConfirmCommand extends Command {
    public static Command AWAITINGCONFIRMATION = null;
    public static String MESSAGE = "Yes?";
    public static String COMMAND_WORD = "yes";
    
    @Override
    public CommandResult execute() {
        if(AWAITINGCONFIRMATION!=null){
            return AWAITINGCONFIRMATION.execute();
        }else{
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE);
        }
    }

}
