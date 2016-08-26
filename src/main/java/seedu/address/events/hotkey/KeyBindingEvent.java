package seedu.address.events.hotkey;

import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import seedu.address.events.BaseEvent;

import java.util.Optional;

/**
 * Indicates a key event occurred that is potentially a key binding being used
 */
public class KeyBindingEvent extends BaseEvent {

    /** The key event that triggered this event*/
    public Optional<KeyEvent> keyEvent = Optional.empty();

    /** A key combination that matches the key event.
     * This is used when a key event is not accessible.*/
    public Optional<KeyCombination> keyCombination = Optional.empty();

    /** The time that the Key event occurred */
    public long time;

    public KeyBindingEvent(KeyEvent keyEvent){
        this.time = System.nanoTime();
        this.keyEvent = Optional.of(keyEvent);
    }

    public KeyBindingEvent(KeyCombination keyCombination) {
        this.time = System.nanoTime();
        this.keyCombination = Optional.of(keyCombination);
    }

    /**
     * @param potentialMatch
     * @return true if the key combination matches this key binding
     */
    public boolean isMatching(KeyCombination potentialMatch) {
        if (keyEvent.isPresent()){
            return potentialMatch.match(keyEvent.get());
        } else {
            return potentialMatch.equals(keyCombination.get());
        }
    }

    @Override
    public String toString() {
        final String className = this.getClass().getSimpleName();
        return className + " " + (keyEvent.isPresent() ? keyEvent.get().getText() :
                                                         keyCombination.get().getDisplayText());
    }
}
