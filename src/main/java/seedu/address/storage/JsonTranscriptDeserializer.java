package seedu.address.storage;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyTranscript;
import seedu.address.model.Transcript;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credit;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

/**
 * Deserializer for {@link seedu.address.model.ReadOnlyTranscript}.
 */
public class JsonTranscriptDeserializer extends StdDeserializer<ReadOnlyTranscript> {


    private static final Logger logger = LogsCenter.getLogger(JsonTranscriptStorage.class);

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
            while (elements.hasNext()) {
                JsonNode e = elements.next();
                Code code = new Code(e.path("code").path("value").textValue());
                Year year = new Year(e.path("year").path("value").intValue());
                Semester semester = new Semester(e.path("semester").path("value").textValue());
                Credit credits = new Credit(e.path("credits").path("value").intValue());
                Grade grade = new Grade(e.path("grade").path("value").textValue());
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
