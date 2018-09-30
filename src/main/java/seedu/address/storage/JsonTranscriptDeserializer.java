package seedu.address.storage;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyTranscript;
import seedu.address.model.Transcript;
import seedu.address.model.module.Module;

/**
 * Deserializer for {@link seedu.address.model.ReadOnlyTranscript}.
 */
public class JsonTranscriptDeserializer extends StdDeserializer<ReadOnlyTranscript> {
    

    private static final Logger logger = LogsCenter.getLogger(JsonTranscriptStorage.class);
    public static final String MESSAGE_DUPLICATE_MODULE = "Transcript in JSON file contains duplicate modules.";
    public static final String MESSAGE_INVALID_TRANSCRIPT = "Transcript in JSON contains invalid data:";

    public JsonTranscriptDeserializer(Class<ReadOnlyTranscript> vc) {
        super(vc);
    }

    public JsonTranscriptDeserializer() {
        super(ReadOnlyTranscript.class);
    }
    
    @Override
    public ReadOnlyTranscript deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Transcript transcript = new Transcript();
        JsonNode node = jp.getCodec().readTree(jp);
        try {
            Iterator<JsonNode> elements = node.get("modules").get("internalList").elements();
            for (; elements.hasNext(); ) {
                JsonNode e = elements.next();
                String code = e.path("code").path("value").textValue();
                int year = e.path("year").path("value").intValue();
                String semester = e.path("semester").path("value").textValue();
                int credits = e.path("credits").path("value").intValue();
                String grade = e.path("grade").path("value").textValue();
                boolean completed = e.path("completed").booleanValue();
                Module module = new Module(code, year, semester, credits, grade, completed);
                transcript.addModule(module);
            }
            return transcript;
        } catch (NullPointerException e) {
            throw new IOException(e);
        }
    }
}
