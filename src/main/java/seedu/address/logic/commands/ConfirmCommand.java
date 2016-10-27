package seedu.address.logic.commands;
//@@author A0135812L
/**
 * Confirms the RequiresConfirm implemented Command
 * @author A0135812L
 *
 */
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
