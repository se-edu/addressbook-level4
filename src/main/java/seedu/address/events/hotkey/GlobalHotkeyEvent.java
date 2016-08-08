package seedu.address.events.hotkey;

import seedu.address.events.BaseEvent;
import javafx.scene.input.KeyCombination;

/**
 * Represents a global hotkey event.
 * Used as an intermediary event to be raised by jKeyMaster infrastructure.
 * Wraps around a {@link KeyBindingEvent} that will be raised by the code handling this event.
 */
public class GlobalHotkeyEvent extends BaseEvent {

    /** The key binding event that represents the global hotkey*/
    public final KeyBindingEvent keyBindingEvent;

    public GlobalHotkeyEvent(KeyCombination keyCombination) {
        this.keyBindingEvent = new KeyBindingEvent(keyCombination);
    }

    @Override
    public String toString() {
        final String className = this.getClass().getSimpleName();
        return className + " : keyCombination is " + keyBindingEvent.keyCombination.get().getDisplayText();
    }
}
