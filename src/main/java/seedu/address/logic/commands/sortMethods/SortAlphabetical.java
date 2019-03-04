package seedu.address.logic.commands.sortMethods;

import java.util.*;
import seedu.address.model.person.Person;


/**
 * Sorts all persons by age.
 */
public class SortAlphabetical {

    private final List<Person> lastShownList;
    private List<Person> newList = new ArrayList<>();
    private final List<String> correctOrder = new ArrayList<>();

    public List<Person> getList() {
        return this.newList;
    }

    public SortAlphabetical(List<Person> lastShownList) {
        this.lastShownList = lastShownList;
        for (int i=0; i<lastShownList.size(); i++) {
            correctOrder.add(lastShownList.get(i).getName().toString());
        }
        Collections.sort(correctOrder); //Case sensitive
        for (int i=0; i<correctOrder.size(); i++) {
            for (Person person : lastShownList) {
                if (person.getName().toString() == correctOrder.get(i)) {
                    newList.add(person);
                }
            }
        }
        this.newList = newList;
    }
}
