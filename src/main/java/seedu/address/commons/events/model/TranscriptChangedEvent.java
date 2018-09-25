package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Transcript;

/** Indicates the Transcript in the model has changed*/
public class TranscriptChangedEvent extends BaseEvent {

    public final Transcript data;

    public TranscriptChangedEvent(Transcript data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of modules " + data.getModuleList().size();
    }
}
