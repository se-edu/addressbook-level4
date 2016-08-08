package address.parser.qualifier;

import address.model.datatypes.person.ReadOnlyPerson;
import commons.StringUtil;

public class TagQualifier implements Qualifier {
    private final String tagName;

    public TagQualifier(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean run(ReadOnlyPerson person) {
        return person.getTagList().stream()
                .filter(tag -> StringUtil.containsIgnoreCase(tag.getName(), this.tagName))
                .findAny()
                .isPresent();
    }

    @Override
    public String toString() {
        return "tag=" + tagName;
    }
}
