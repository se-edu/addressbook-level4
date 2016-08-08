package seedu.address.parser;

import seedu.address.model.ModelManager;

/**
 *
 */
public class DeleteCommand implements Command {

    @Override
    public void execute(ModelManager modelManager) {
        if (modelManager.getPersonList().size() == 0) {
            return;
        }
        modelManager.deletePersonThroughUI(modelManager.getPersonList().get(0));
    }
}
