package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;

/**
 * Wraps all data at the transcript level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class Transcript implements ReadOnlyTranscript {

    private final UniqueModuleList modules;
    private double capGoal;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueModuleList();
    }

    public Transcript() {}

    /**
     * Creates an Transcript using the Modules in the {@code toBeCopied}
     */
    public Transcript(ReadOnlyTranscript toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Resets the existing data of this {@code Transcript} with {@code newData}.
     */
    public void resetData(ReadOnlyTranscript newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
    }

    //// module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the transcript.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the transcript.
     * The module must not already exist in the transcript.
     */
    public void addModule(Module p) {
        modules.add(p);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the transcript.
     * The module identity of {@code editedModule} must not be the same as another existing module in the transcript.
     */
    public void updateModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from this {@code Transcript}.
     * {@code key} must exist in the transcript.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    /**
     * Return the current CAP
     * @return current cap score
     */
    public double getCurrentCap() {
        return calculateCapScore();
    }

    /**
     * Calculate CAP Score based on modules with scores
     * @return cap: cap score
     */
    private double calculateCapScore() {
        UniqueModuleList gradedModulesList = getGradedModulesList();
        Iterator<Module> gradedModulesIterator = gradedModulesList.iterator();

        double totalCap = 0;
        double point;
        int totalModuleCredit = 0;
        int moduleCredit;
        while (gradedModulesIterator.hasNext()) {
            Module module = gradedModulesIterator.next();
            moduleCredit = module.getCredits().value;
            point = module.getGrade().getPoint();
            totalCap += moduleCredit * point;
            totalModuleCredit += moduleCredit;
        }

        double cap = 0;
        if (totalModuleCredit > 0) {
            cap = totalCap / totalModuleCredit;
        }

        return cap;
    }

    /**
     * Filters for modules that is to be used for CAP calculation
     * @return gradedModulesList: a list of modules used for CAP calculation
     */
    private UniqueModuleList getGradedModulesList() {
        UniqueModuleList gradedModulesList = new UniqueModuleList();
        Iterator<Module> moduleIterator = modules.iterator();
        while (moduleIterator.hasNext()) {
            Module module = moduleIterator.next();
            if (moduleIsUsedForCapCalculation(module)) {
                gradedModulesList.add(module);
            }
        }
        return gradedModulesList;
    }

    /**
     * Check if the given module should be considered for CAP Calculation
     * @param module
     * @return true if yes, false otherwise
     */
    private boolean moduleIsUsedForCapCalculation(Module module) {
        return module.hasCompleted() && moduleAffectsGrade(module);
    }

    /**
     * Check if a module affects grade
     * @param module
     * @return true if module affects grade, false otheriwse
     */
    private boolean moduleAffectsGrade(Module module) {
        return module.getGrade().affectsCap();
    }

    //// util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Transcript // instanceof handles nulls
                && modules.equals(((Transcript) other).modules));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }

    public double getCapGoal() {
        return capGoal;
    }

    public void setCapGoal(double capGoal) {
        this.capGoal = capGoal;
    }
}
