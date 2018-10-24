package seedu.address.model.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalModules.DATA_STRUCTURES;
import static seedu.address.testutil.TypicalModules.DISCRETE_MATH;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.util.ModuleBuilder;

public class UniqueModuleListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueModuleList uniqueModuleList = new UniqueModuleList();

    @Test
    public void containsNullModuleThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.contains(null);
    }

    @Test
    public void containsModuleNotInListReturnsFalse() {
        assertFalse(uniqueModuleList.contains(DATA_STRUCTURES));
    }

    @Test
    public void containsModuleInListReturnsTrue() {
        uniqueModuleList.add(DATA_STRUCTURES);
        assertTrue(uniqueModuleList.contains(DATA_STRUCTURES));
    }

    @Test
    public void addNullModuleThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.add(null);
    }

    @Test
    public void addDuplicateModuleThrowsDuplicateModuleException() {
        uniqueModuleList.add(DATA_STRUCTURES);
        thrown.expect(DuplicateModuleException.class);
        uniqueModuleList.add(DATA_STRUCTURES);
    }

    @Test
    public void setModuleNullTargetModuleThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModule(null, DATA_STRUCTURES);
    }

    @Test
    public void setModuleNullEditedModuleThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModule(DATA_STRUCTURES, null);
    }

    @Test
    public void setModuleTargetModuleNotInListThrowsModuleNotFoundException() {
        thrown.expect(ModuleNotFoundException.class);
        uniqueModuleList.setModule(DATA_STRUCTURES, DATA_STRUCTURES);
    }

    @Test
    public void setModuleEditedModuleIsSameModuleSuccess() {
        uniqueModuleList.add(DATA_STRUCTURES);
        uniqueModuleList.setModule(DATA_STRUCTURES, DATA_STRUCTURES);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(DATA_STRUCTURES);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModuleEditedModuleHasSameIdentitySuccess() {
        uniqueModuleList.add(DATA_STRUCTURES);
        Module editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withYear(DISCRETE_MATH.getYear().value)
                .build();
        uniqueModuleList.setModule(DATA_STRUCTURES, editedDataStructures);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(editedDataStructures);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModuleEditedModuleHasDifferentIdentitySuccess() {
        uniqueModuleList.add(DATA_STRUCTURES);
        Module editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withCode(DISCRETE_MATH.getCode().value)
                .build();
        uniqueModuleList.setModule(DATA_STRUCTURES, DISCRETE_MATH);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(DISCRETE_MATH);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModuleEditedModuleHasNonUniqueIdentityThrowsDuplicateModuleException() {
        uniqueModuleList.add(DATA_STRUCTURES);
        uniqueModuleList.add(DISCRETE_MATH);
        thrown.expect(DuplicateModuleException.class);
        uniqueModuleList.setModule(DATA_STRUCTURES, DISCRETE_MATH);
    }

    @Test
    public void removeNullModuleThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.remove((Module) null);
    }

    @Test
    public void removeModuleDoesNotExistThrowsModuleNotFoundException() {
        thrown.expect(ModuleNotFoundException.class);
        uniqueModuleList.remove(DATA_STRUCTURES);
    }

    @Test
    public void removeExistingModuleRemovesModule() {
        uniqueModuleList.add(DATA_STRUCTURES);
        uniqueModuleList.remove(DATA_STRUCTURES);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModulesNullUniqueModuleListThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModules((UniqueModuleList) null);
    }

    @Test
    public void setModulesUniqueModuleListReplacesOwnListWithProvidedUniqueModuleList() {
        uniqueModuleList.add(DATA_STRUCTURES);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(DISCRETE_MATH);
        uniqueModuleList.setModules(expectedUniqueModuleList);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModulesNullListThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModules((List<Module>) null);
    }

    @Test
    public void setModulesListReplacesOwnListWithProvidedList() {
        uniqueModuleList.add(DATA_STRUCTURES);
        List<Module> moduleList = Collections.singletonList(DISCRETE_MATH);
        uniqueModuleList.setModules(moduleList);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(DISCRETE_MATH);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModulesListWithDuplicateModuleThrowsDuplicateModuleException() {
        List<Module> listWithDuplicateModules = Arrays.asList(DATA_STRUCTURES, DATA_STRUCTURES);
        thrown.expect(DuplicateModuleException.class);
        uniqueModuleList.setModules(listWithDuplicateModules);
    }

    @Test
    public void asUnmodifiableObservableListModifyListThrowsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueModuleList.asUnmodifiableObservableList().remove(0);
    }
}
