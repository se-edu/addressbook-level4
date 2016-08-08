package guitests;

import address.model.datatypes.AddressBook;
import address.model.datatypes.person.Person;
import address.model.datatypes.person.ReadOnlyPerson;
import address.model.datatypes.tag.Tag;
import address.testutil.PersonBuilder;
import commons.StringUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class FilterPersonsGuiTest extends GuiTestBase {
    private Person sameLastNameAsAlice, sameCityAsAlice;

    @Override
    public AddressBook getInitialData() {
        AddressBook addressBook = new AddressBook(td.book);

        sameLastNameAsAlice = new Person("Zack", td.alice.getLastName(), addressBook.getPersonList().size() + 1);
        sameCityAsAlice = new PersonBuilder(new Person("Yoshi", "Nakamoto", addressBook.getPersonList().size() + 2))
                .withCity(td.alice.getCity())
                .build();

        addressBook.addPerson(sameLastNameAsAlice);
        addressBook.addPerson(sameCityAsAlice);

        return addressBook;
    }

    @Test
    public void filterPersons_singleSimpleQualifier() {
        personListPanel.enterFilterAndApply("tag:colleagues");
        assertResultList(filterByTag(getInitialData().getPersonList(), "colleagues", false));
    }

    @Test
    public void filterPersons_multipleSimpleQualifiers() {
        personListPanel.enterFilterAndApply("tag:friends city:new");
        assertResultList(filterByCity(filterByTag(getInitialData().getPersonList(), "friends", false), "new", false));

        personListPanel.enterFilterAndApply("tag:friends name:brown");
        assertResultList(
                filterByTag(filterByName(getInitialData().getPersonList(), "brown", false), "friends", false));

        personListPanel.enterFilterAndApply("name:edwards name:brown id:6");
        assertResultList(
                filterById(filterByName(filterByName(getInitialData().getPersonList(), "edwards", false),
                                        "brown", false), 6, false));

        personListPanel.enterFilterAndApply("name:edwa name:dan id:6");
        assertResultList(
                filterById(filterByName(filterByName(getInitialData().getPersonList(), "edwa", false),
                                        "dan", false), 6, false));
    }

    @Test
    public void filterPersons_unknownTag() {
        personListPanel.enterFilterAndApply("tag:enemies");
        assertResultList(filterByTag(getInitialData().getPersonList(), "enemies", false));
    }

    @Test
    public void filterPersons_negatedQualifiers() {
        personListPanel.enterFilterAndApply("!tag:enemies");
        assertResultList(filterByTag(getInitialData().getPersonList(), "enemies", true));

        personListPanel.enterFilterAndApply("!!tag:friends");
        assertResultList(filterByTag(getInitialData().getPersonList(), "friends", false));

        personListPanel.enterFilterAndApply("!!!city:Texas");
        assertResultList(filterByCity(getInitialData().getPersonList(), "Texas", true));
    }

    @Test
    public void filterPersons_multipleQualifiers() {
        personListPanel.enterFilterAndApply("tag:friends !city:california");
        assertResultList(
                filterByCity(filterByTag(getInitialData().getPersonList(), "friends", false), "california", true));

        personListPanel.enterFilterAndApply("!tag:colleagues city:chic");
        assertResultList(
                filterByCity(filterByTag(getInitialData().getPersonList(), "colleagues", true), "chic", false));

        personListPanel.enterFilterAndApply("id:2 !!!id:3 !id:5 !!!!!id:8");
        assertResultList(
                filterById(filterById(filterById(filterById(getInitialData().getPersonList(), 2, false), 3, true),
                                      5, true), 8, true));

        personListPanel.enterFilterAndApply("id:2 id:3 id:5 !id:4");
        assertResultList(
                filterById(filterById(filterById(filterById(getInitialData().getPersonList(), 2, false), 3, false),
                                      5, false), 4, true));
    }

    private List<ReadOnlyPerson> filterByTag(List<ReadOnlyPerson> originalList, String partialTagName,
                                             boolean isNegative) {
        return originalList.stream()
                .filter(person -> isNegative != hasTag(person, partialTagName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<ReadOnlyPerson> filterByName(List<ReadOnlyPerson> originalList, String partialName,
                                              boolean isNegative) {
        return originalList.stream()
                .filter(person -> isNegative != hasName(person, partialName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<ReadOnlyPerson> filterById(List<ReadOnlyPerson> originalList, int id, boolean isNegative) {
        return originalList.stream()
                .filter(person -> isNegative != (person.getId() == id))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<ReadOnlyPerson> filterByCity(List<ReadOnlyPerson> originalList, String partialCityName,
                                              boolean isNegative) {
        return originalList.stream()
                .filter(person -> isNegative != StringUtil.containsIgnoreCase(person.getCity(), partialCityName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean hasName(ReadOnlyPerson person, String partialName) {
        return StringUtil.containsIgnoreCase(person.getFirstName(), partialName)
                || StringUtil.containsIgnoreCase(person.getLastName(), partialName);
    }

    private boolean hasTag(ReadOnlyPerson person, String partialTagName) {
        for (Tag tag : person.getTagList()) {
            if (StringUtil.containsIgnoreCase(tag.getName(), partialTagName)) return true;
        }
        return false;
    }

    private void assertResultList(List<ReadOnlyPerson> resultList) {
        assertTrue(personListPanel.containsListOnly(resultList));
    }
}
