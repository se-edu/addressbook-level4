package seedu.address.logic.commands;

public class ConfirmCommand extends Command {
    public static Command AWAITINGCONFIRMATION = null;
    public static final  String MESSAGE = "Yes?";
    public static final String COMMAND_WORD = "yes";
    
    public ConfirmCommand(){
        
    }
    
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
