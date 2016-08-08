package seedu.address.parser;

import seedu.address.model.ModelManager;

/**
 *
 */
public interface Command {


    public void execute(ModelManager modelManager);
}
