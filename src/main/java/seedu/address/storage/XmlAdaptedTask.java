package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
//    @XmlElement(required = false)
//    private DateTime openTime;
//    @XmlElement(required = false)
//    private DateTime closeTime;
//    @XmlElement(required = false)
//    private boolean isImportant;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().taskName;
//        openTime = source.getOpenTime();
//        closeTime = source.getCloseTime();
//        isImportant = source.getImportance();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
//        final DateTime openTime = new DateTime(this.openTime);
//        final DateTime closeTime = new DateTime(this.closeTime);
//        final boolean isImportant = this.isImportant;
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, tags); //(name, openTime, closeTime, isImportant, tags)
    }
}
