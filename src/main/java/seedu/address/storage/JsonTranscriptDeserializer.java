package seedu.address.storage;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

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
                JsonNode element = elements.next();
                Code code = new Code(element.path("code").path("value").textValue());
                Year year = new Year(element.path("year").path("value").intValue());
                Semester semester = new Semester(element.path("semester").path("value").textValue());
                Credit credits = new Credit(element.path("credits").path("value").intValue());
                Grade grade = new Grade(element.path("grade").path("value").textValue(),
                        element.path("grade").path("state").textValue());
                boolean completed = element.path("completed").booleanValue();
                Module module = new Module(code, year, semester, credits, grade, completed);
                transcript.addModule(module);
            }

            JsonNode capGoal = node.get("capGoal");
            if (!capGoal.isMissingNode()) {
                transcript.setCapGoal(capGoal.path("value").doubleValue());
            }
            return transcript;
        } catch (NullPointerException e) {
            throw new IOException(e);
        }
    }
}
