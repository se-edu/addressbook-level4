package seedu.address.parser;

import seedu.address.model.ModelManager;
import seedu.address.model.datatypes.person.Person;

import java.util.Optional;

/**
 *
 */
public class AddCommand implements Command{

    @Override
    public void execute(ModelManager modelManager) {
        modelManager.createPersonThroughUI(Optional.of(new Person("John", "Smith", -1)));
    }

    @Override
    public String getTargetName() {
        return "John Smith";
    }
}
