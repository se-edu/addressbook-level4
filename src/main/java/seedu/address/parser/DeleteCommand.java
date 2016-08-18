package seedu.address.parser;

import seedu.address.model.ModelManager;

/**
 *
 */
public class DeleteCommand implements Command {

    private String targetName;

    @Override
    public void execute(ModelManager modelManager) {
        if (modelManager.getPersonList().size() == 0) {
            return;
        }
        targetName = modelManager.getPersonList().get(0).fullName();
        modelManager.deletePersonThroughUI(modelManager.getPersonList().get(0));
    }

    @Override
    public String getTargetName() {
        return targetName;
    }
}
