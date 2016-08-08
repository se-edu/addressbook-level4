package address.events.hotkey;

import address.events.BaseEvent;

/**
 * Indicates an accelerator key binding was detected and ignored.
 */
public class AcceleratorIgnoredEvent extends BaseEvent {
    private String acceleratorName;

    public AcceleratorIgnoredEvent(String acceleratorName) {
        this.acceleratorName = acceleratorName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + acceleratorName;
    }

    @Override
    public boolean hasSameIntentionAs(BaseEvent other){
        return (other instanceof AcceleratorIgnoredEvent)
            && this.acceleratorName.equals(((AcceleratorIgnoredEvent) other).acceleratorName);
    }
}
