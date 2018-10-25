package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.person.Person;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Person> PREDICATE_MATCHING_NO_PERSONS = unused -> false;
    private static final Predicate<Module> PREDICATE_MATCHING_NO_MODULE = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Person> toDisplay) {
        Optional<Predicate<Person>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Person... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Person} equals to {@code other}.
     */
    private static Predicate<Person> getPredicateMatching(Person other) {
        return person -> person.equals(other);
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredModuleList(Model model, List<Module> toDisplay) {
        Optional<Predicate<Module>> predicate =
                toDisplay.stream().map(ModelHelper::getModulePredicateMatching).reduce(Predicate::or);
        model.updateFilteredModuleList(predicate.orElse(PREDICATE_MATCHING_NO_MODULE));
    }

    /**
     * @see ModelHelper#setFilteredModuleList(Model, List)
     */
    public static void setFilteredModuleList(Model model, Module... toDisplay) {
        setFilteredModuleList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Module} equals to {@code other}.
     */
    private static Predicate<Module> getModulePredicateMatching(Module other) {
        return module -> module.equals(other);
    }
}
