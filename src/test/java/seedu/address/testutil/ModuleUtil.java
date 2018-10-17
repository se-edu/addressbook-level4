package seedu.address.testutil;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.model.module.Module;

//@@author alexkmj
/**
 * A utility class for Module.
 */
public class ModuleUtil {

    /**
     * Returns an add command string for adding the {@code module}.
     */
    public static String getAddModuleCommand(Module module) {
        return AddModuleCommand.COMMAND_WORD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(module.getCode().value + " ");
        sb.append(module.getYear().value + " ");
        sb.append(module.getSemester().value + " ");
        sb.append(module.getCredits().value + " ");
        sb.append(module.getGrade().value + " ");
        return sb.toString();
    }
}
