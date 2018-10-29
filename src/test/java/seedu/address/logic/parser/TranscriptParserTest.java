package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;
import seedu.address.testutil.ModuleUtil;

//@@author alexkmj
public class TranscriptParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TranscriptParser parser = new TranscriptParser();

    @Test
    public void parseCommandAddModule() throws Exception {
        Module module = new ModuleBuilder().build();
        AddModuleCommand command = (AddModuleCommand) parser.parseCommand(ModuleUtil.getAddModuleCommand(module));
        assertEquals(new AddModuleCommand(module), command);
    }
}
