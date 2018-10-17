# jeremiah-ang
###### /java/seedu/address/logic/commands/GoalCommand.java
``` java
/**
 * Sets CAP Goal
 */
public class GoalCommand extends Command {

    public static final String COMMAND_WORD = "goal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set your CAP goal. "
            + "Parameters: "
            + "CAP_GOAL "
            + "Example: " + COMMAND_WORD + " "
            + "4.5";

    public static final String MESSAGE_SUCCESS = "Your CAP Goal: %1$s";

    private final double goal;

    /**
     * Creates an GoalCommand to set the CAP Goal
     */
    public GoalCommand(double goal) {
        this.goal = goal;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        model.updateCapGoal(goal);
        CapGoal capGoal = model.getCapGoal();
        return new CommandResult(String.format(MESSAGE_SUCCESS, capGoal.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoalCommand // instanceof handles nulls
                && goal == ((GoalCommand) other).goal); // state check
    }
}
```
###### /java/seedu/address/logic/commands/CapCommand.java
``` java
/**
 * Shows CAP based on existing modules.
 */
public class CapCommand extends Command {
    public static final String COMMAND_WORD = "cap";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculate current CAP with given modules "
            + "Parameters: NONE "
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Your Current CAP is: %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        double cap = model.getCap();
        return new CommandResult(String.format(MESSAGE_SUCCESS, cap));
    }
}
```
###### /java/seedu/address/logic/parser/GoalCommandParser.java
``` java
/**
 * Parses User Input
 */
public class GoalCommandParser implements Parser<GoalCommand> {
    @Override
    public GoalCommand parse(String userInput) throws ParseException {
        final String trimmedArgs = userInput.trim();
        final String format = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoalCommand.MESSAGE_USAGE);
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(format);
        }

        try {
            double newGoal = Double.parseDouble(trimmedArgs);
            if (newGoal < 0 || newGoal > 5) {
                throw new ParseException(format);
            }
            return new GoalCommand(newGoal);
        } catch (NumberFormatException nfe) {
            throw new ParseException(format);
        }
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public CapGoal getCapGoal() {
        return versionedTranscript.getCapGoal();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateCapGoal(double capGoal) {
        versionedTranscript.setCapGoal(capGoal);
        indicateTranscriptChanged();
    }

    //TODO: REMOVE
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    //TODO: REMOVE
    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    //TODO: REMOVE
    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    //TODO: REMOVE
    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    //TODO: REMOVE
    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //TODO: REMOVE
    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     * TODO: REMOVE
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    //TODO: REMOVE
    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    //TODO: REMOVE
    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    //TODO: REMOVE
    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    //TODO: REMOVE
    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    //TODO: REMOVE
    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    //TODO: REMOVE
    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public double getCap() {
        return versionedTranscript.getCap();
    }

```
###### /java/seedu/address/model/Transcript.java
``` java
    /**
     * Return the current CAP
     *
     * @return current cap score
     */
    public double getCap() {
        return calculateCap();
    }

    /**
     * Calculate CAP Score based on modules with scores
     *
     * @return cap: cap score
     */
    private double calculateCap() {

        ObservableList<Module> gradedModulesList = getGradedModulesList();
        double totalModulePoint = calculateTotalModulePoint(gradedModulesList);
        double totalModuleCredit = calculateTotalModuleCredit(gradedModulesList);

        double cap = 0;
        if (totalModuleCredit > 0) {
            cap = totalModulePoint / totalModuleCredit;
        }

        return cap;
    }

    /**
     * Calculates the total module point from the list of modules
     * @param modules
     * @return
     */
    private double calculateTotalModulePoint(ObservableList<Module> modules) {
        double totalPoint = 0;
        for (Module module : modules) {
            totalPoint += module.getGrade().getPoint() * module.getCredits().value;
        }
        return totalPoint;
    }

    /**
     * Calculates the total module credit from the list of modules
     * @param modules
     * @return
     */
    private double calculateTotalModuleCredit(ObservableList<Module> modules) {
        int totalModuleCredit = 0;
        for (Module module : modules) {
            totalModuleCredit += module.getCredits().value;
        }
        return totalModuleCredit;
    }

    /**
     * Filters for modules that is to be used for CAP calculation
     *
     * @return list of modules used for CAP calculation
     */
    private ObservableList<Module> getGradedModulesList() {
        return modules.getFilteredModules(this::moduleIsUsedForCapCalculation);
    }

    /**
     * Filters for modules that have yet been graded
     * @return gradedModulesList: a list of modules used for CAP calculation
     */
    private ObservableList<Module> getNotCompletedModulesList() {
        return modules.getFilteredModules(module -> !module.hasCompleted());
    }

    /**
     * Check if the given module should be considered for CAP Calculation
     *
     * @param module
     * @return true if yes, false otherwise
     */
    private boolean moduleIsUsedForCapCalculation(Module module) {
        return module.hasCompleted() && moduleAffectsGrade(module);
    }

    /**
     * Check if a module affects grade
     *
     * @param module
     * @return true if module affects grade, false otheriwse
     */
    private boolean moduleAffectsGrade(Module module) {
        return module.getGrade().affectsCap();
    }

    /**
     * Calculates target module grade in order to achieve target goal
     * @return a list of modules with target grade if possible. null otherwise
     */
    public ObservableList<Module> getTargetModuleGrade() {
        ObservableList<Module> gradedModules = getGradedModulesList();
        ObservableList<Module> ungradedModules = getNotCompletedModulesList()
                .sorted(Comparator.comparingInt(o -> o.getCredits().value));
        List<Module> targetModules = new ArrayList<>();
        if (ungradedModules.isEmpty()) {
            return FXCollections.observableArrayList(targetModules);
        }

        double totalUngradedModuleCredit = calculateTotalModuleCredit(ungradedModules);
        double totalMc = calculateTotalModuleCredit(gradedModules) + totalUngradedModuleCredit;
        double currentTotalPoint = calculateTotalModulePoint(gradedModules);

        double totalScoreToAchieve = capGoal.getCapGoal() * totalMc - currentTotalPoint;
        double unitScoreToAchieve = Math.ceil(totalScoreToAchieve / totalUngradedModuleCredit * 2) / 2.0;
        if (unitScoreToAchieve > 5) {
            return null;
        }


        Module targetModule;
        for (Module ungradedModule : ungradedModules) {
            if (unitScoreToAchieve == 0.5) {
                unitScoreToAchieve = 1.0;
            }
            targetModule = new Module(ungradedModule, new Grade(unitScoreToAchieve));
            targetModules.add(targetModule);
            totalScoreToAchieve -= targetModule.getCredits().value * unitScoreToAchieve;
            totalUngradedModuleCredit -= targetModule.getCredits().value;
            unitScoreToAchieve = Math.ceil(totalScoreToAchieve / totalUngradedModuleCredit * 2) / 2.0;
        }

        return FXCollections.observableArrayList(targetModules);
    }

    public CapGoal getCapGoal() {
        return capGoal;
    }

    public void setCapGoal(double capGoal) {
        this.capGoal = new CapGoal(capGoal);
    }

```
###### /java/seedu/address/model/capgoal/CapGoal.java
``` java
/**
 * Represents Cap Goal
 *
 * Immutable. Value can be null.
 */
public class CapGoal {

    private static final String MESSAGE_IS_NULL = "NIL";

    private double capGoal;
    private boolean isSet = true;

    public CapGoal() {

    }

    public CapGoal(double capGoal) {
        isSet = false;
        this.capGoal = capGoal;
    }

    /**
     * Returns the cap goal
     * @return
     */
    public double getCapGoal() {
        return capGoal;
    }

    public boolean isSet() {
        return isSet;
    }

    @Override
    public String toString() {
        if (isSet) {
            return MESSAGE_IS_NULL;
        }
        return "" + getCapGoal();
    }
}
```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Creates a new Module from an existing module but with a different grade
     * @param module
     * @param grade
     */
    public Module(Module module, Grade grade) {
        this(module.code, module.year, module.semester, module.credits, grade, module.completed);
    }

```
###### /java/seedu/address/model/module/UniqueModuleList.java
``` java
    /**
     * Returns the list of filtered Module based on the given predicate
     *
     * @param predicate
     * @return filtered list
     */
    public ObservableList<Module> getFilteredModules(Predicate<Module> predicate) {
        return internalList.filtered(predicate);
    }

```
