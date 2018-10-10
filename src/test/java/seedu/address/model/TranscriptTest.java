package seedu.address.model;

import static org.junit.Assert.assertEquals;

import static seedu.address.testutil.TypicalModules.MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP;
import static seedu.address.testutil.TypicalModules.getModulesWithNonGradeAffectingModules;
import static seedu.address.testutil.TypicalModules.getModulesWithoutNonGradeAffectingModules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.model.module.Module;
import seedu.address.testutil.ModuleBuilder;

import javafx.collections.ObservableList;

/**
 * Test {@code TranscriptTest} Class
 */
public class TranscriptTest {

    public static final Module GRADE_BMINUS_4MC_A = new ModuleBuilder()
            .withCredit(4)
            .withGrade("B-")
            .build();
    public static final Module INCOMPLETE_4MC_A = new ModuleBuilder()
            .withCredit(4)
            .withCompleted(false)
            .build();
    public static final Module INCOMPLETE_4MC_B = new ModuleBuilder()
            .withCredit(4)
            .withCompleted(false)
            .build();
    public static final Module INCOMPLETE_4MC_C = new ModuleBuilder()
            .withCredit(4)
            .withCompleted(false)
            .build();
    public static final Module INCOMPLETE_5MC_A = new ModuleBuilder()
            .withCredit(5)
            .withCompleted(false)
            .build();

    @Test
    public void typicalModulesCapScore() {
        List<Module> modules = getModulesWithoutNonGradeAffectingModules();
        assertCapScoreEquals(modules, MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP);
    }

    @Test
    public void calculateCapScoreWithSuModule() {
        List<Module> modules = getModulesWithNonGradeAffectingModules();
        assertCapScoreEquals(modules, MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP);

    }

    @Test
    public void calculateTargetGrades() {
        List<Module> modules = new ArrayList<>(Arrays.asList(
            INCOMPLETE_4MC_A,
            INCOMPLETE_4MC_B,
            INCOMPLETE_4MC_C
        ));
        double capGoal = 4.0;
        List<String> expectedGrades = new ArrayList<>(Arrays.asList(
            "B+",
            "B+",
            "B+"
        ));
        assertTargetGradesEquals(modules, capGoal, expectedGrades);

        modules = new ArrayList<>(Arrays.asList(
                INCOMPLETE_4MC_A,
                INCOMPLETE_4MC_B,
                INCOMPLETE_5MC_A,
                INCOMPLETE_4MC_C,
                GRADE_BMINUS_4MC_A
        ));
        capGoal = 4.5;
        expectedGrades = new ArrayList<>(Arrays.asList(
                "A",
                "A",
                "A",
                "A-"
        ));
        assertTargetGradesEquals(modules, capGoal, expectedGrades);

        capGoal = 5.0;
        assertTargetGradesEquals(modules, capGoal, null);

        modules = new ArrayList<>(Arrays.asList(
                GRADE_BMINUS_4MC_A
        ));
        assertTargetGradesEquals(modules, capGoal, new ArrayList<>());
    }

    /**
     * Assert that the modules will have the CAP score of expectedCapScore
     * @param modules
     * @param expectedCapScore
     */
    private void assertCapScoreEquals(List<Module> modules, Double expectedCapScore) {
        Transcript transcript = new Transcript();
        transcript.setModules(modules);
        double cap = transcript.getCap();
        assertEquals(Double.valueOf(cap), expectedCapScore);
    }

    /**
     * Assert that the given modules and cap goal will result in expected target grades
     * @param modules
     * @param capGoal
     * @param expectedTargetGrades
     */
    private void assertTargetGradesEquals(List<Module> modules, Double capGoal, List<String> expectedTargetGrades) {
        Transcript transcript = new Transcript();
        transcript.setModules(modules);
        transcript.setCapGoal(capGoal);
        ObservableList<Module> targetModules = transcript.getTargetModuleGrade();

        if (expectedTargetGrades == null) {
            assertEquals(targetModules, null);
            return;
        }

        List<String> targetGrades = new ArrayList<>();
        targetModules.forEach(module -> targetGrades.add(module.getGrade().value));
        String targetGradesString = String.join(" ", targetGrades);
        String expectedTargetGradesString = String.join(" ", expectedTargetGrades);
        assertEquals(targetGradesString, expectedTargetGradesString);
    }

}
