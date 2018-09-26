package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTranscript;

/** Indicates the Transcript in the model has changed*/
public class TranscriptChangedEvent extends BaseEvent {

    public final ReadOnlyTranscript data;

    public TranscriptChangedEvent(ReadOnlyTranscript data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of transcripts " + data.getModuleList().size();
    }
}
