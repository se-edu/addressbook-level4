package seedu.address.logic.commands;

public interface RequiresConfirm{
    /**
     * Remember to confirm these commands if they are to be used in testing.
     */
    
    public static String PROMPT_MESSAGE = "Are you sure you want to execute ' %1$s ' ? Type ' yes ' to confirm.";    
    
    default CommandResult prompt(String commandWord){
        return new CommandResult(String.format(PROMPT_MESSAGE, commandWord));
    }

    
    /** This is for the injection of the COMMAND_WORD 
     * and updating the latest command that is waiting for confirmation.
     * Sample Code is given below for easy implementation.
     * @return CommandResult by calling prompt(String)
     */
    /* Sample Code
    public CommandResult prompt() {
        ConfirmCommand.AWAITINGCONFIRMATION = this;
        return prompt(COMMAND_WORD);
    }
    */
    public CommandResult prompt();
    
}
