# alexkmj
###### /java/seedu/address/logic/parser/AddModuleCommandParserTest.java
``` java
public class AddModuleCommandParserTest {
    private AddModuleCommandParser parser = new AddModuleCommandParser();

    @Test
    public void parseAllFieldsPresentSuccess() throws Exception {
        // leading and trailing whitespace
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                + " " + DISCRETE_MATH.getCode().value
                + " " + DISCRETE_MATH.getYear().value
                + " " + DISCRETE_MATH.getSemester().value
                + " " + DISCRETE_MATH.getCredits().value
                + " " + DISCRETE_MATH.getGrade().value, new AddModuleCommand(DISCRETE_MATH));
    }

    @Test
    public void parseOptionalFieldsMissingSuccess() {
        Module expectedModule = new ModuleBuilder(DISCRETE_MATH)
                .noGrade()
                .withCompleted(false)
                .build();

        // no grade
        assertParseSuccess(parser, DISCRETE_MATH.getCode().value
                + " " + DISCRETE_MATH.getYear().value
                + " " + DISCRETE_MATH.getSemester().value
                + " " + DISCRETE_MATH.getCredits().value, new AddModuleCommand(expectedModule));
    }
}
```
###### /java/seedu/address/logic/parser/TranscriptParserTest.java
``` java
public class TranscriptParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TranscriptParser parser = new TranscriptParser();

    @Test
    public void parseCommandAddModule() throws Exception {
        Module module = new ModuleBuilder().build();
        AddModuleCommand command = (AddModuleCommand) parser.parseCommand(ModuleUtil.getAddModuleCommand(module));
        assertEquals(new AddModuleCommand(module), command);
    }
}
```
###### /java/seedu/address/model/module/GradeTest.java
``` java
public class GradeTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructorInvalidGradeThrowsIllegalArgumentException() {
        String invalidGrade = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Grade(invalidGrade));
    }

    @Test
    public void isValidGrade() {
        // invalid grade format
        assertFalse(Grade.isValidGrade(" A+")); // no leading whitespace
        assertFalse(Grade.isValidGrade("A+ ")); // no leading whitespace
        assertFalse(Grade.isValidGrade("A +")); // no whitespace in between
        assertFalse(Grade.isValidGrade("G")); // First character has to be A, B, C, D, F

        // valid grade
        assertTrue(Grade.isValidGrade("A+"));
        assertTrue(Grade.isValidGrade("A"));
        assertTrue(Grade.isValidGrade("A-"));
        assertTrue(Grade.isValidGrade("B+"));
        assertTrue(Grade.isValidGrade("B"));
        assertTrue(Grade.isValidGrade("B-"));
        assertTrue(Grade.isValidGrade("C+"));
        assertTrue(Grade.isValidGrade("C"));
        assertTrue(Grade.isValidGrade("D+"));
        assertTrue(Grade.isValidGrade("D"));
        assertTrue(Grade.isValidGrade("F"));
        assertTrue(Grade.isValidGrade("CU"));
        assertTrue(Grade.isValidGrade("CS"));
    }

    @Test
    public void affectCapValid() {
        assertTrue(new Grade("A+").affectsCap());
        assertTrue(new Grade("A").affectsCap());
        assertTrue(new Grade("A-").affectsCap());
        assertTrue(new Grade("B+").affectsCap());
        assertTrue(new Grade("B").affectsCap());
        assertTrue(new Grade("B-").affectsCap());
        assertTrue(new Grade("C+").affectsCap());
        assertTrue(new Grade("C").affectsCap());
        assertTrue(new Grade("D+").affectsCap());
        assertTrue(new Grade("D").affectsCap());
        assertTrue(new Grade("F").affectsCap());
        assertFalse(new Grade("CS").affectsCap());
        assertFalse(new Grade("CU").affectsCap());
    }

    @Test
    public void getPointValid() {
        assertTrue(new Grade("A+").getPoint() == 5);
        assertTrue(new Grade("A").getPoint() == 5);
        assertTrue(new Grade("A-").getPoint() == 4.5);
        assertTrue(new Grade("B+").getPoint() == 4.0);
        assertTrue(new Grade("B").getPoint() == 3.5);
        assertTrue(new Grade("B-").getPoint() == 3.0);
        assertTrue(new Grade("C+").getPoint() == 2.5);
        assertTrue(new Grade("C").getPoint() == 2);
        assertTrue(new Grade("D+").getPoint() == 1.5);
        assertTrue(new Grade("D").getPoint() == 1);
        assertTrue(new Grade("F").getPoint() == 0);
    }

    @Test
    public void toStringValid() {
        assertTrue(new Grade("A+").toString().contentEquals("A+"));
    }

    @Test
    public void equalsValid() {
        assertTrue(new Grade("A+").equals(new Grade("A+")));
    }


```
###### /java/seedu/address/model/module/SemesterTest.java
``` java
public class SemesterTest {

    @Test
    public void constructorInvalidYearThrowsIllegalArgumentException() {
        Assert.assertThrows(NullPointerException.class, () -> new Semester(null));
    }

    @Test
    public void isValidSemester() {
        // invalid semester format
        assertFalse(Semester.isValidSemester("s3"));
        assertFalse(Semester.isValidSemester("3"));

        // valid semester
        assertTrue(Semester.isValidSemester(Semester.SEMESTER_ONE));
        assertTrue(Semester.isValidSemester(Semester.SEMESTER_TWO));
        assertTrue(Semester.isValidSemester(Semester.SEMESTER_SPECIAL_ONE));
        assertTrue(Semester.isValidSemester(Semester.SEMESTER_SPECIAL_TWO));
    }

    @Test
    public void toStringValid() {
        assertTrue(new Semester(Semester.SEMESTER_ONE).toString().contentEquals(Semester.SEMESTER_ONE));
    }

    @Test
    public void equalsValid() {
        assertTrue(new Semester(Semester.SEMESTER_ONE).equals(new Semester(Semester.SEMESTER_ONE)));
    }
}
```
###### /java/seedu/address/model/module/YearTest.java
``` java
public class YearTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Year(null));
    }

    @Test
    public void constructorInvalidYearThrowsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Year(0));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Year("0"));
    }

    @Test
    public void isValidYear() {
        // invalid year format
        assertFalse(Year.isValidYear(0)); // year must be at least 1
        assertFalse(Year.isValidYear(6)); // year must be 5 or below
        assertFalse(Year.isValidYear(10)); // only 1 digit allowed

        // valid year format
        assertTrue(Year.isValidYear(1)); // year 1
        assertTrue(Year.isValidYear(2)); // year 2
        assertTrue(Year.isValidYear(3)); // year 3
        assertTrue(Year.isValidYear(4)); // year 4
        assertTrue(Year.isValidYear(5)); // year 5
    }

    @Test
    public void toStringValid() {
        assertTrue(new Year(1).toString().contentEquals("1"));
    }

    @Test
    public void equalsValid() {
        assertTrue(new Year(1).equals(new Year(1)));
    }

    @Test
    public void hashCodeValid() {
        assertTrue(new Year(1).hashCode() == "1".hashCode());
    }
}
```
###### /java/seedu/address/model/module/UniqueModuleListTest.java
``` java
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
                .withCode(DISCRETE_MATH.getCode().value)
                .build();
        uniqueModuleList.setModule(DATA_STRUCTURES, editedDataStructures);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(editedDataStructures);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModuleEditedModuleHasDifferentIdentitySuccess() {
        uniqueModuleList.add(DATA_STRUCTURES);
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
        uniqueModuleList.remove(null);
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
```
###### /java/seedu/address/model/module/CreditTest.java
``` java
public class CreditTest {
    @Test
    public void constructorInvalidCreditThrowsIllegalArgumentException() {
        int invalidCredit = 0;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Credit(invalidCredit));
    }

    @Test
    public void isValidCredit() {
        // invalid code format
        assertFalse(Credit.isValidCredit(0)); // must be greater than or equal to 1
        assertFalse(Credit.isValidCredit(21)); // must be lower than or equal to 20

        // valid code
        assertTrue(Credit.isValidCredit(4)); // credit between 1 and 20
    }

    @Test
    public void toStringValid() {
        assertTrue(new Credit(4).toString().contentEquals("4"));
    }

    @Test
    public void equalsValid() {
        assertTrue(new Credit(4).equals(new Credit(4)));
    }
}
```
###### /java/seedu/address/model/module/CodeTest.java
``` java
public class CodeTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Code(null));
    }

    @Test
    public void constructorInvalidCodeThrowsIllegalArgumentException() {
        String invalidCode = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Code(invalidCode));
    }

    @Test
    public void isValidCode() {
        // invalid code format
        assertFalse(Code.isValidCode("")); // cannot be blank
        assertFalse(Code.isValidCode(" CS2103")); // no leading whitespace
        assertFalse(Code.isValidCode("CS2103 ")); // no leading whitespace
        assertFalse(Code.isValidCode("CS 2103")); // no whitespace in between

        // valid code
        assertTrue(Code.isValidCode("CS2103")); // no whitespace
    }

    @Test
    public void toStringValid() {
        assertTrue(new Code("CS2103").toString().contentEquals("CS2103"));
    }

    @Test
    public void equalsValid() {
        assertTrue(new Code("CS2103").equals(new Code("CS2103")));
    }
}
```
###### /java/seedu/address/model/module/ModuleTest.java
``` java
public class ModuleTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Module(null, null, null, null, null, false));
    }

    @Test
    public void isSameModule() {
        // same object -> returns true
        assertTrue(DATA_STRUCTURES.isSameModule(DATA_STRUCTURES));

        // different object -> returns false
        assertFalse(DATA_STRUCTURES.isSameModule(DISCRETE_MATH));

        // different code -> returns false
        Module editedDataStructures = new ModuleBuilder(DATA_STRUCTURES)
                .withCode(DISCRETE_MATH.getCode().value)
                .build();
        assertFalse(DATA_STRUCTURES.isSameModule(editedDataStructures));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Module dataStructuresCopy = new ModuleBuilder(DATA_STRUCTURES).build();
        assertTrue(DATA_STRUCTURES.equals(dataStructuresCopy));

        // same object -> returns true
        assertTrue(DATA_STRUCTURES.equals(DATA_STRUCTURES));

        // different type -> returns false
        assertFalse(DATA_STRUCTURES.equals(5));

        // different module -> returns false
        assertFalse(DATA_STRUCTURES.equals(DISCRETE_MATH));

        // different code -> returns false
        Module editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withCode(DISCRETE_MATH.getCode().value)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));

        // different year -> returns false
        editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withYear(DISCRETE_MATH.getYear().value)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));

        // different semester -> returns false
        editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withSemester(DISCRETE_MATH.getSemester().value)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));

        // different credit -> returns false
        editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withCredit(DATABASE_SYSTEMS_2MC.getCredits().value)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));

        // different grade -> returns false
        editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withGrade(DISCRETE_MATH.getGrade().value)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));

        // different completed -> returns false
        editedDataStrucure = new ModuleBuilder(DATA_STRUCTURES)
                .withCompleted(false)
                .build();
        assertFalse(DATA_STRUCTURES.equals(editedDataStrucure));
    }

    @Test
    public void toStringValid() {
        assertTrue(DATA_STRUCTURES.toString().contentEquals("Code: CS2040 Year: 3 Semester: "
                + "s1 Credits: 4 Grade: F Completed: true"));
    }
}
```
###### /java/seedu/address/testutil/ModuleUtil.java
``` java
/**
 * A utility class for Module.
 */
public class ModuleUtil {

    /**
     * Returns an add command string for adding the {@code module}.
     */
    public static String getAddModuleCommand(Module module) {
        return AddModuleCommand.COMMAND_WORD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(module.getCode().value + " ");
        sb.append(module.getYear().value + " ");
        sb.append(module.getSemester().value + " ");
        sb.append(module.getCredits().value + " ");
        sb.append(module.getGrade().value + " ");
        return sb.toString();
    }
}
```
###### /java/seedu/address/testutil/TypicalModules.java
``` java
/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {
    // Manually added

    public static final Double MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP = 3.0;

    public static final Module DISCRETE_MATH = new ModuleBuilder().withCode("CS1231")
            .withYear(1)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();

    public static final Module PROGRAMMING_METHODOLOGY_TWO = new ModuleBuilder().withCode("CS2030")
            .withYear(2)
            .withSemester(Semester.SEMESTER_TWO)
            .withCredit(4)
            .withGrade("B+")
            .build();

    public static final Module DATA_STRUCTURES = new ModuleBuilder().withCode("CS2040")
            .withYear(3)
            .withSemester(Semester.SEMESTER_SPECIAL_ONE)
            .withCredit(4)
            .withGrade("F")
            .build();

    public static final Module ASKING_QUESTIONS = new ModuleBuilder().withCode("GEQ1000")
            .withYear(1)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("CS")
            .build();

    public static final Module SOFTWARE_ENGINEERING = new ModuleBuilder().withCode("CS2103")
            .withYear(3)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();

    public static final Module DATABASE_SYSTEMS = new ModuleBuilder().withCode("CS2102")
            .withYear(2)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(4)
            .withGrade("A+")
            .build();

    public static final Module DATABASE_SYSTEMS_2MC = new ModuleBuilder().withCode("CS2102B")
            .withYear(2)
            .withSemester(Semester.SEMESTER_ONE)
            .withCredit(2)
            .withGrade("A+")
            .build();

    /**
     * Prevents instantiation
     */
    private TypicalModules() {

    }

    /**
     * Returns an {@code Transcript} given modules as arguments.
     */
    public static Transcript getTranscriptWithModules(Module... modules) {
        Transcript tr = new Transcript();
        for (Module module : modules) {
            tr.addModule(module);
        }
        return tr;
    }

    /**
     * Returns an {@code Transcript} with all the typical persons.
     */
    public static Transcript getTypicalTranscript() {
        Transcript tr = new Transcript();
        for (Module module : getTypicalModules()) {
            tr.addModule(module);
        }
        return tr;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(DISCRETE_MATH,
                PROGRAMMING_METHODOLOGY_TWO,
                DATA_STRUCTURES));
    }


    /**
     * A list of modules that affects the cap
     *
     * @return
     */
    public static List<Module> getModulesWithoutNonGradeAffectingModules() {
        return new ArrayList<>(Arrays.asList(DISCRETE_MATH,
                PROGRAMMING_METHODOLOGY_TWO,
                DATA_STRUCTURES));
    }

    /**
     * A list of modules that might not affect the cap
     *
     * @return
     */
    public static List<Module> getModulesWithNonGradeAffectingModules() {
        List<Module> affectingModules = getModulesWithoutNonGradeAffectingModules();
        List<Module> nonAffectingModules = new ArrayList<>(Arrays.asList(ASKING_QUESTIONS));
        affectingModules.addAll(nonAffectingModules);
        return affectingModules;
    }
    // TODO: getTypicalAddressBook()
}
```
