package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Transcript;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;

public class AdjustCommandTest {
    private Model model = new ModelManager(new Transcript(), new UserPrefs());
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructorNullModuleThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AdjustCommand(null, null);
    }

    @Test
    public void executeSuccess() {
        Module module = new ModuleBuilder().withCode("ABC").noGrade().build();
        Grade expectedGrade = module.getGrade().adjustGrade("A");
        Module expectedModule = new Module(module, expectedGrade);
        model.addModule(module);

        Transcript expectedTranscript = new Transcript();
        expectedTranscript.addModule(expectedModule);
        Model expectedModel = new ModelManager(expectedTranscript, new UserPrefs());
        AdjustCommand adjustCommand = new AdjustCommand(module.getCode(), new Grade().adjustGrade("A"));
        String expectedMessage = String.format(AdjustCommand.MESSAGE_SUCCESS, expectedModule);
        assertCommandSuccess(adjustCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
