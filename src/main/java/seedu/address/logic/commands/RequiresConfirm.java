package seedu.address.logic.commands;

public interface RequiresConfirm{
    public static String PROMPT_MESSAGE = "Are you sure you want to execute ' %1$s ' ? Type ' yes ' to confirm.";    
    default CommandResult prompt(String commandWord){
        return new CommandResult(String.format(PROMPT_MESSAGE, commandWord));
    }

    public CommandResult prompt();
}
