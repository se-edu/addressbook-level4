package address.parser;

import address.model.ModelManager;
import address.model.datatypes.person.Person;
import address.model.datatypes.person.ReadOnlyPerson;

import java.util.Optional;

/**
 *
 */
public class AddCommand implements Command{

    @Override
    public void execute(ModelManager modelManager) {
        modelManager.createPersonThroughUI(Optional.of(new Person("John", "Smith", -1)));
    }
}
