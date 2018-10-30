package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import guitests.guihandles.ModuleCardHandle;
import guitests.guihandles.ModuleListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.module.Module;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(ModuleCardHandle expectedCard, ModuleCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getCode(), actualCard.getCode());
        assertEquals(expectedCard.getSemester(), actualCard.getSemester());
        assertEquals(expectedCard.getYear(), actualCard.getYear());
        assertEquals(expectedCard.getGrade(), actualCard.getGrade());
    }
    //public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
    //assertEquals(expectedCard.getId(), actualCard.getId());
    //assertEquals(expectedCard.getAddress(), actualCard.getAddress());
    //assertEquals(expectedCard.getEmail(), actualCard.getEmail());
    //assertEquals(expectedCard.getName(), actualCard.getName());
    //assertEquals(expectedCard.getPhone(), actualCard.getPhone());
    // assertEquals(expectedCard.getTags(), actualCard.getTags());
    //}

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     * @param expectedModule
     * @param actualCard
     */
    public static void assertCardDisplaysModule(Module expectedModule, ModuleCardHandle actualCard) {
        assertEquals(expectedModule.getCode().value, actualCard.getCode());
        assertEquals(expectedModule.getSemester().value, actualCard.getSemester());
        assertEquals(expectedModule.getYear().value, actualCard.getYear());
        assertEquals(expectedModule.getCredits().value, actualCard.getCredits());
        assertEquals(expectedModule.getGrade().value, actualCard.getGrade());
    }

    //public static void assertCardDisplaysPerson(Module expectedModule, ModuleCardHandle actualCard) {
    //assertEquals(expectedModule.getName().fullName, actualCard.getCode());
    //assertEquals(expectedModule.getPhone().value, actualCard.getSemester());
    //assertEquals(expectedModule.getEmail().value, actualCard.getEmail());
    //assertEquals(expectedModule.getAddress().value, actualCard.getCredits());
    //assertEquals(expectedModule.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
    //actualCard.getTags());
    //}

    /**
     * Asserts that the list in {@code moduleListPanelHandle} displays the details of {@code modules} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ModuleListPanelHandle moduleListPanelHandle, Module... modules) {
        for (int i = 0; i < modules.length; i++) {
            moduleListPanelHandle.navigateToCard(i);
            assertCardDisplaysModule(modules[i], moduleListPanelHandle.getModuleCardHandle(i));
        }
    }

    //public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
    //for (int i = 0; i < persons.length; i++) {
    //personListPanelHandle.navigateToCard(i);
    //assertCardDisplaysModule(persons[i], personListPanelHandle.getPersonCardHandle(i));
    //}
    //}

    /**
     * Asserts that the list in {@code moduleListPanelHandle} displays the details of {@code modules} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ModuleListPanelHandle moduleListPanelHandle, List<Module> modules) {
        assertListMatching(moduleListPanelHandle, modules.toArray(new Module[0]));
    }

    //public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
    //assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    //}

    /**
     * Asserts the size of the list in {@code moduleListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(ModuleListPanelHandle moduleListPanelHandle, int size) {
        int numberOfModules = moduleListPanelHandle.getListSize();
        assertEquals(size, numberOfModules);
    }

    //public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
    //int numberOfPeople = personListPanelHandle.getListSize();
    //assertEquals(size, numberOfPeople);
    //}

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
