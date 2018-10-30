package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.module.Module;

/**
 * Represents a selection change in the Person List Panel
 */
public class ModulePanelSelectionChangedEvent extends BaseEvent {


    private final Module newSelection;

    public ModulePanelSelectionChangedEvent(Module newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Module getNewSelection() {
        return newSelection;
    }
}
