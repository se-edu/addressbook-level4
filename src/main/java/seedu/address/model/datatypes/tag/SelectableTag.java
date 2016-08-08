package seedu.address.model.datatypes.tag;

import com.google.common.base.Objects;

// TODO CHANGE THIS TO WRAPPER CLASS
public class SelectableTag extends Tag {
    private boolean isSelected = false;

    public SelectableTag(Tag tag) {
        super(tag.getName());
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SelectableTag that = (SelectableTag) o;
        return isSelected == that.isSelected;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), isSelected);
    }
}
