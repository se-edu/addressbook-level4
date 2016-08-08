package address.model.datatypes.tag;

import address.model.datatypes.ExtractableObservables;
import address.model.datatypes.UniqueData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;

public class Tag extends UniqueData implements ExtractableObservables {

    @JsonIgnore private final SimpleStringProperty name;

    {
        name = new SimpleStringProperty("");
    }

    public Tag() {}

    public Tag(String name) {
        setName(name);
    }

    // Copy constructor
    public Tag(Tag tag) {
        update(tag);
    }

    public Tag update(Tag group) {
        setName(group.getName());
        return this;
    }

    @Override
    public Observable[] extractObservables() {
        return new Observable[] { name };
    }

    @JsonProperty("name")
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }


    public SimpleStringProperty nameProperty() {
        return name;
    }

    @Override
    public boolean equals(Object otherGroup) {
        if (otherGroup == this) return true;
        if (otherGroup == null) return false;
        if (!Tag.class.isAssignableFrom(otherGroup.getClass())) return false;

        final Tag other = (Tag) otherGroup;
        return this.getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return getName();
    }

}
