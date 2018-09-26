package seedu.address.model;

import org.junit.Test;
import seedu.address.model.module.Module;

import java.util.List;

import static org.junit.Assert.assertEquals;

import static seedu.address.testutil.TypicalModules.TYPICAL_MODULES_CAP;
import static seedu.address.testutil.TypicalModules.getTypicalModules;
import static seedu.address.testutil.TypicalModules.getModulesWithoutNonGradeAffectingModules;

public class TranscriptTest {

    @Test
    public void typicalModulesCapScore() {
        List<Module> modules = getModulesWithoutNonGradeAffectingModules();
        assertCapScoreEquals(modules, TYPICAL_MODULES_CAP);
    }

    @Test
    public void calculateCapScoreWithSuModule() {
        List<Module> modules = getTypicalModules();
        assertCapScoreEquals(modules, TYPICAL_MODULES_CAP);

    }

    private void assertCapScoreEquals(List<Module> modules, Double expectedCapScore) {
        Transcript transcript = new Transcript();
        transcript.setModules(modules);
        double cap = transcript.calculateCapScore();
        assertEquals(Double.valueOf(cap), expectedCapScore);
    }

}
