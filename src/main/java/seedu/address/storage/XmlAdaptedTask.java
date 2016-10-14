package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {
    
    
    
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String period;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private boolean isUntimed;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        
        name = source.getName().taskName;
        time = source.getTime().value;
        startTime = source.getTime().startDate.format(DateTimeFormatter.ofPattern(Time.DATE_TIME_PRINT_FORMAT));
        if (source.getTime().getEndDate().isPresent()) {
            endTime = source.getTime().getEndDate().toString();
            System.out.println("Yo Fist me bro:" + endTime);
        }else
            endTime = null;
                
        isUntimed = source.getTime().isUntimed;
        period = source.getPeriod().value;
        description = source.getDescription().value;
        address = source.getLocation().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {

        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }  
        Time time;
        if(endTime!=null) {
            time = new Time(this.startTime,this.endTime,isUntimed);
        }else {
            time = new Time(this.startTime,isUntimed);                
        }
        final Name name = new Name(this.name);
        final Period period = new Period(this.period);
        final Description description = new Description(this.description);
        final Location address = new Location(this.address);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, time, period, description, address, tags);

    }
}
