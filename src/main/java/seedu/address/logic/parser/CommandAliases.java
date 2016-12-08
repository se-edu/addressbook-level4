package seedu.address.logic.parser;

import java.util.HashMap;

import seedu.address.logic.commands.*;

/**
 * Container for alias to actual command word mapping and a method for the translation logic between alias and command,
 * and translation from non-alias inputs.
 */
public class CommandAliases {
    
    private static final HashMap<String, String> MAPPING;
    static {
        String[] addAliases = {"a"};
        String[] clearAliases = {"c", "clr", "reset"};
        String[] deleteAliases = {"d", "del", "dlt", "remove"};
        String[] exitAliases = {"e", "q", "quit"};
        String[] findAliases = {"f", "get", "search"};
        String[] helpAliases = {"h", "sos"};
        String[] listAliases = {"l"};
        String[] selectAliases = {"s", "sel"};
        
        String[] commandOrder = {
                AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
                FindCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD
                };
        String[][] aliasOrder = {
                addAliases, clearAliases, deleteAliases, exitAliases,
                findAliases, helpAliases, listAliases, selectAliases
                };
        
        MAPPING = new HashMap<>();
        for (int i = 0; i < commandOrder.length; i++) {
            String command = commandOrder[i]; 
            for (String alias : aliasOrder[i]) {
                MAPPING.put(alias, command);
            }
        }
    }

    /**
     * Returns the actual command word from the alias, if there is a mapping between the input alias and a command word.
     * For input aliases with no corresponding translation, the method returns the input alias parameter.
     * 
     * @param alias
     * @return translated alias, i.e. command
     */
    public static String translate(String alias) {
        return MAPPING.getOrDefault(alias, alias);
    }

}
