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
import seedu.address.testutil.ModuleBuilder;

public class UniqueModuleListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueModuleList uniqueModuleList = new UniqueModuleList();

    @Test
    public void contains_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.contains(null);
    }

    @Test
    public void contains_moduleNotInList_returnsFalse() {
        assertFalse(uniqueModuleList.contains(DATA_STRUCTURES));
    }

    @Test
    public void contains_moduleInList_returnsTrue() {
        uniqueModuleList.add(DATA_STRUCTURES);
        assertTrue(uniqueModuleList.contains(DATA_STRUCTURES));
    }

    @Test
    public void add_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.add(null);
    }

    @Test
    public void add_duplicateModule_throwsDuplicateModuleException() {
        uniqueModuleList.add(DATA_STRUCTURES);
        thrown.expect(DuplicateModuleException.class);
        uniqueModuleList.add(DATA_STRUCTURES);
    }

    @Test
    public void setModule_nullTargetModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModule(null, DATA_STRUCTURES);
    }

    @Test
    public void setModule_nullEditedModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModule(DATA_STRUCTURES, null);
    }

    @Test
    public void setModule_targetModuleNotInList_throwsModuleNotFoundException() {
        thrown.expect(ModuleNotFoundException.class);
        uniqueModuleList.setModule(DATA_STRUCTURES, DATA_STRUCTURES);
    }

    @Test
    public void setModule_editedModuleIsSameModule_success() {
        uniqueModuleList.add(DATA_STRUCTURES);
        uniqueModuleList.setModule(DATA_STRUCTURES, DATA_STRUCTURES);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(DATA_STRUCTURES);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasSameIdentity_success() {
        uniqueModuleList.add(DATA_STRUCTURES);
        Module editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withCode(DISCRETE_MATH.getCode().value)
                .build();
        uniqueModuleList.setModule(DATA_STRUCTURES, editedDataStructures);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(editedDataStructures);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasDifferentIdentity_success() {
        uniqueModuleList.add(DATA_STRUCTURES);
        uniqueModuleList.setModule(DATA_STRUCTURES, DISCRETE_MATH);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(DISCRETE_MATH);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasNonUniqueIdentity_throwsDuplicateModuleException() {
        uniqueModuleList.add(DATA_STRUCTURES);
        uniqueModuleList.add(DISCRETE_MATH);
        thrown.expect(DuplicateModuleException.class);
        uniqueModuleList.setModule(DATA_STRUCTURES, DISCRETE_MATH);
    }

    @Test
    public void remove_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.remove(null);
    }

    @Test
    public void remove_moduleDoesNotExist_throwsModuleNotFoundException() {
        thrown.expect(ModuleNotFoundException.class);
        uniqueModuleList.remove(DATA_STRUCTURES);
    }

    @Test
    public void remove_existingModule_removesModule() {
        uniqueModuleList.add(DATA_STRUCTURES);
        uniqueModuleList.remove(DATA_STRUCTURES);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_nullUniqueModuleList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModules((UniqueModuleList) null);
    }

    @Test
    public void setModules_uniqueModuleList_replacesOwnListWithProvidedUniqueModuleList() {
        uniqueModuleList.add(DATA_STRUCTURES);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(DISCRETE_MATH);
        uniqueModuleList.setModules(expectedUniqueModuleList);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModules((List<Module>) null);
    }

    @Test
    public void setModules_list_replacesOwnListWithProvidedList() {
        uniqueModuleList.add(DATA_STRUCTURES);
        List<Module> moduleList = Collections.singletonList(DISCRETE_MATH);
        uniqueModuleList.setModules(moduleList);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(DISCRETE_MATH);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_listWithDuplicateModule_throwsDuplicateModuleException() {
        List<Module> listWithDuplicateModules = Arrays.asList(DATA_STRUCTURES, DATA_STRUCTURES);
        thrown.expect(DuplicateModuleException.class);
        uniqueModuleList.setModules(listWithDuplicateModules);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueModuleList.asUnmodifiableObservableList().remove(0);
    }
}
