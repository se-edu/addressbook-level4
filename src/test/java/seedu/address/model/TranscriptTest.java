package seedu.address.model;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalModules.MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP;
import static seedu.address.testutil.TypicalModules.getModulesWithNonGradeAffectingModules;
import static seedu.address.testutil.TypicalModules.getModulesWithoutNonGradeAffectingModules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import javafx.collections.ObservableList;

import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;

//@@author jeremiah-ang
/**
 * Test {@code TranscriptTest} Class
 */
public class TranscriptTest {

    public static final String DELIMITER = " ";

    private static final Module GRADE_BMINUS_4MC_A = new ModuleBuilder()
            .withCode("BMINUSA")
            .withCredit(4)
            .withGrade("B-")
            .build();
    private static final Module GRADE_A_4MC_A = new ModuleBuilder()
            .withCode("AA")
            .withCredit(4)
            .withGrade("A")
            .build();
    private static final Module GRADE_A_4MC_B = new ModuleBuilder()
            .withCode("AB")
            .withCredit(4)
            .withGrade("A")
            .build();
    private static final Module INCOMPLETE_4MC_A = new ModuleBuilder()
            .withCode("INCOMPLETEA")
            .withCredit(4)
            .withCompleted(false)
            .noGrade()
            .build();
    private static final Module INCOMPLETE_4MC_B = new ModuleBuilder()
            .withCode("INCOMPLETEB")
            .withCredit(4)
            .withCompleted(false)
            .noGrade()
            .build();
    private static final Module INCOMPLETE_4MC_C = new ModuleBuilder()
            .withCode("INCOMPLETEC")
            .withCredit(4)
            .withCompleted(false)
            .noGrade()
            .build();
    private static final Module INCOMPLETE_5MC_A = new ModuleBuilder()
            .withCode("INCOMPLETE5A")
            .withCredit(5)
            .withCompleted(false)
            .noGrade()
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
        List<String> expectedTargetGrades = new ArrayList<>(Arrays.asList(
            "B+",
            "B+",
            "B+"
        ));
        assertTargetGradesEquals(modules, capGoal, expectedTargetGrades);

        modules = new ArrayList<>(Arrays.asList(
                INCOMPLETE_4MC_A,
                INCOMPLETE_4MC_B,
                INCOMPLETE_5MC_A,
                INCOMPLETE_4MC_C,
                GRADE_BMINUS_4MC_A
        ));
        capGoal = 4.5;
        expectedTargetGrades = new ArrayList<>(Arrays.asList(
                "A",
                "A",
                "A",
                "A-"
        ));
        assertTargetGradesEquals(modules, capGoal, expectedTargetGrades);

        capGoal = 5.0;
        assertCapGoalImpossible(modules, capGoal);

        modules = new ArrayList<>(Arrays.asList(
                GRADE_BMINUS_4MC_A
        ));
        assertTargetGradesEquals(modules, capGoal, new ArrayList<>());

        modules = new ArrayList<>(Arrays.asList(
                INCOMPLETE_4MC_A,
                INCOMPLETE_4MC_B,
                INCOMPLETE_4MC_C,
                GRADE_A_4MC_A,
                GRADE_A_4MC_B
        ));
        capGoal = 4.0;
        expectedTargetGrades = new ArrayList<>(Arrays.asList(
                "B",
                "B",
                "B-"
        ));
        assertTargetGradesEquals(modules, capGoal, expectedTargetGrades);
    }

    @Test
    public void testTriggersForTargetGradeCalculation() {
        Transcript transcript = new Transcript();
        transcript.addModule(GRADE_A_4MC_A);
        assertTargetGradesEquals(transcript, "");

        transcript.addModule(GRADE_BMINUS_4MC_A);
        assertTargetGradesEquals(transcript, "");

        transcript.setCapGoal(5.0);
        assertTargetGradesEquals(transcript, "");

        transcript.addModule(INCOMPLETE_4MC_A);
        assertTrue(transcript.isCapGoalImpossible());

        transcript.setCapGoal(4.0);
        assertTargetGradesEquals(transcript, "B+");

        transcript.addModule(INCOMPLETE_4MC_B);
        assertTargetGradesEquals(transcript, "B+ B+");
    }

    /**
     * Assert that the modules will have the CAP score of expectedCapScore
     * @param modules
     * @param expectedCapScore
     */
    private void assertCapScoreEquals(List<Module> modules, Double expectedCapScore) {
        Transcript transcript = new Transcript();
        transcript.setModules(modules);
        double cap = transcript.getCurrentCap();
        assertEquals(Double.valueOf(cap), expectedCapScore);
    }

    /**
     * Gets target grades as a delimited String
     * @param transcript
     * @return
     */
    private String getTargetGradesStringFromTranscript(Transcript transcript) {
        ObservableList<Module> targetModules = transcript.getTargetedModulesList();
        List<String> targetGrades = new ArrayList<>();
        targetModules.forEach(module -> targetGrades.add(module.getGrade().value));
        String targetGradesString = String.join(DELIMITER, targetGrades);
        return targetGradesString;
    }

    /**
     * Asserts that the given modules and cap goal will result in expected target grades
     * @param modules
     * @param capGoal
     * @param expectedTargetGrades
     */
    private void assertTargetGradesEquals(
            List<Module> modules, Double capGoal, List<String> expectedTargetGrades) {
        Transcript transcript = setUpTranscript(modules, capGoal);
        String expectedTargetGradesString = String.join(DELIMITER, expectedTargetGrades);
        assertTargetGradesEquals(transcript, expectedTargetGradesString);
    }

    /**
     * Asserts that the given transcript will result in expected target grades
     * @param transcript
     * @param expectedTargetGrades
     */
    public void assertTargetGradesEquals(Transcript transcript, String expectedTargetGrades) {
        String targetGrades = getTargetGradesStringFromTranscript(transcript);
        assertEquals(targetGrades, expectedTargetGrades);
    }

    /**
     * Sets modules and capGoal of a new Transcript
     * @param modules
     * @param capGoal
     * @return
     */
    private Transcript setUpTranscript(List<Module> modules, Double capGoal) {
        Transcript transcript = new Transcript();
        transcript.setModules(modules);
        transcript.setCapGoal(capGoal);
        return transcript;
    }

    /**
     * Asserts that it is impossible to achieve the CapGoal
     * @param modules
     * @param capGoal
     */
    private void assertCapGoalImpossible(
            List<Module> modules, Double capGoal) {
        Transcript transcript = setUpTranscript(modules, capGoal);
        assertTrue(transcript.isCapGoalImpossible());
    }

}
