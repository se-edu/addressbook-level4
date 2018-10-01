package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalModules.MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP;
import static seedu.address.testutil.TypicalModules.getModulesWithNonGradeAffectingModules;
import static seedu.address.testutil.TypicalModules.getModulesWithoutNonGradeAffectingModules;

import java.util.List;

import org.junit.Test;

import seedu.address.model.module.Module;

/**
 * Test {@code TranscriptTest} Class
 */
public class TranscriptTest {

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

}
