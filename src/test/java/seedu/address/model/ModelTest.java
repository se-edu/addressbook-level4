package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;



public class ModelTest {
    private Module complete = new ModuleBuilder().withCode("COMPLETE").withGrade("A").build();
    private Module incomplete = new ModuleBuilder().withCode("INCOMPLETE").noGrade().build();
    private Module adjust = new Module(
            new ModuleBuilder().withCode("ADJUST").noGrade().build(), new Grade().adjustGrade("A"));
    private Module target = new Module(
            new ModuleBuilder().withCode("TARGET").noGrade().build(), new Grade().targetGrade("A"));
    private ObservableList<Module> fullModules = FXCollections.unmodifiableObservableList(
            FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(
                    complete,
                    incomplete,
                    adjust,
                    target
            )))
    );

    private List<Module> emptyModules = new ArrayList<>();

    @Test
    public void assertFiltersSuccess() {
        List<Module> completedModules = Model.filterModulesWithCompleteGrade(fullModules);
        assertEquals(complete, completedModules.get(0));
        List<Module> targetedModules = Model.filterModulesWithTargetGrade(fullModules);
        assertEquals(target, targetedModules.get(0));
        List<Module> adjustedModules = Model.filterModulesWithAdjustGrade(fullModules);
        assertEquals(adjust, adjustedModules.get(0));
        List<Module> incompleteModules = Model.filterModulesWithIncompleteGrade(fullModules);
        assertEquals(incomplete, incompleteModules.get(0));

        completedModules = Model.filterModulesWithCompleteGrade(emptyModules);
        assertTrue(completedModules.isEmpty());
        targetedModules = Model.filterModulesWithTargetGrade(emptyModules);
        assertTrue(targetedModules.isEmpty());
        adjustedModules = Model.filterModulesWithAdjustGrade(emptyModules);
        assertTrue(adjustedModules.isEmpty());
        incompleteModules = Model.filterModulesWithIncompleteGrade(emptyModules);
        assertTrue(incompleteModules.isEmpty());
    }


}
