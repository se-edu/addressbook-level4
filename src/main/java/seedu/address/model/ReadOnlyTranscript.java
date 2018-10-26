package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javafx.collections.ObservableList;

import seedu.address.model.capgoal.CapGoal;
import seedu.address.model.module.Module;
import seedu.address.storage.JsonTranscriptDeserializer;

//@@author jeremiah-ang
/**
 * Unmodifiable view of a Transcript.
 */
@JsonDeserialize(using = JsonTranscriptDeserializer.class)
public interface ReadOnlyTranscript {
    /**
     * Returns an unmodifiable view of the module list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

    /**
     * Returns the CapGoal of this transcript
     */
    CapGoal getCapGoal();

    /**
     * Returns the current CAP of this transcript
     */
    double getCurrentCap();

    /**
     * Filters a list of modules with Grade in state COMPLETE
     * @param modules
     * @return return filtered list of module
     */
    static List<Module> filterModulesWithCompleteGrade(List<Module> modules) {
        return filterModules(modules, Module::isGradeComplete);
    }

    /**
     * Filters a list of modules with Grade in state TARGET
     * @param modules
     * @return return filtered list of module
     */
    static List<Module> filterModulesWithTargetGrade(List<Module> modules) {
        return filterModules(modules, Module::isGradeTarget);
    }

    /**
     * Filters a list of modules with Grade in state ADJUST
     * @param modules
     * @return return filtered list of module
     */
    static List<Module> filterModulesWithAdjustGrade(List<Module> modules) {
        return filterModules(modules, Module::isGradeAdjust);
    }

    /**
     * Filters a list of modules with Grade in state INCOMPLETE
     * @param modules
     * @return return filtered list of module
     */
    static List<Module> filterModulesWithIncompleteGrade(List<Module> modules) {
        return filterModules(modules, Module::isGradeIncomplete);
    }

    /**
     * Filters a list of modules
     * @param modules
     * @param predicate
     * @return filtered list of module
     */
    static List<Module> filterModules(List<Module> modules, Predicate<Module> predicate) {
        return modules.stream().filter(predicate).collect(Collectors.toList());
    }
}
