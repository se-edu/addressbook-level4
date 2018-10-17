# jeremiah-ang
###### /java/seedu/address/logic/parser/GoalCommandParserTest.java
``` java
public class GoalCommandParserTest {
    private GoalCommandParser parser = new GoalCommandParser();

    @Test
    public void parseValidCommandSuccess() {
        String userInput = "4.5";
        GoalCommand expectedCommand = new GoalCommand(4.5);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseInvalidNumberFormatFailure() {
        String userInput = "4.5 3.5";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoalCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
```
###### /java/seedu/address/model/TranscriptTest.java
``` java
/**
 * Test {@code TranscriptTest} Class
 */
public class TranscriptTest {

    private static final Module GRADE_BMINUS_4MC_A = new ModuleBuilder()
            .withCode("BMINUSA")
            .withCredit(4)
            .withGrade("B-")
            .build();
    private static final Module GRADE_A_4MC_A = new ModuleBuilder()
            .withCode("AA")
            .withCredit(4)
            .withGrade("A")
            .build();
    private static final Module GRADE_A_4MC_B = new ModuleBuilder()
            .withCode("AB")
            .withCredit(4)
            .withGrade("A")
            .build();
    private static final Module INCOMPLETE_4MC_A = new ModuleBuilder()
            .withCode("INCOMPLETEA")
            .withCredit(4)
            .withCompleted(false)
            .build();
    private static final Module INCOMPLETE_4MC_B = new ModuleBuilder()
            .withCode("INCOMPLETEB")
            .withCredit(4)
            .withCompleted(false)
            .build();
    private static final Module INCOMPLETE_4MC_C = new ModuleBuilder()
            .withCode("INCOMPLETEC")
            .withCredit(4)
            .withCompleted(false)
            .build();
    private static final Module INCOMPLETE_5MC_A = new ModuleBuilder()
            .withCode("INCOMPLETE5A")
            .withCredit(5)
            .withCompleted(false)
            .build();

    @Test
    public void typicalModulesCapScore() {
        List<Module> modules = getModulesWithoutNonGradeAffectingModules();
        assertCapScoreEquals(modules, MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP);
    }

    @Test
    public void calculateCapScoreWithSuModule() {
        List<Module> modules = getModulesWithNonGradeAffectingModules();
        assertCapScoreEquals(modules, MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP);

    }

    @Test
    public void calculateTargetGrades() {
        List<Module> modules = new ArrayList<>(Arrays.asList(
            INCOMPLETE_4MC_A,
            INCOMPLETE_4MC_B,
            INCOMPLETE_4MC_C
        ));
        double capGoal = 4.0;
        List<String> expectedTargetGrades = new ArrayList<>(Arrays.asList(
            "B+",
            "B+",
            "B+"
        ));
        assertTargetGradesEquals(modules, capGoal, expectedTargetGrades);

        modules = new ArrayList<>(Arrays.asList(
                INCOMPLETE_4MC_A,
                INCOMPLETE_4MC_B,
                INCOMPLETE_5MC_A,
                INCOMPLETE_4MC_C,
                GRADE_BMINUS_4MC_A
        ));
        capGoal = 4.5;
        expectedTargetGrades = new ArrayList<>(Arrays.asList(
                "A",
                "A",
                "A",
                "A-"
        ));
        assertTargetGradesEquals(modules, capGoal, expectedTargetGrades);

        capGoal = 5.0;
        assertTargetGradesEquals(modules, capGoal, null);

        modules = new ArrayList<>(Arrays.asList(
                GRADE_BMINUS_4MC_A
        ));
        assertTargetGradesEquals(modules, capGoal, new ArrayList<>());

        modules = new ArrayList<>(Arrays.asList(
                INCOMPLETE_4MC_A,
                INCOMPLETE_4MC_B,
                INCOMPLETE_4MC_C,
                GRADE_A_4MC_A,
                GRADE_A_4MC_B
        ));
        capGoal = 4.0;
        expectedTargetGrades = new ArrayList<>(Arrays.asList(
                "B",
                "B",
                "B-"
        ));
        assertTargetGradesEquals(modules, capGoal, expectedTargetGrades);
    }

    /**
     * Assert that the modules will have the CAP score of expectedCapScore
     * @param modules
     * @param expectedCapScore
     */
    private void assertCapScoreEquals(List<Module> modules, Double expectedCapScore) {
        Transcript transcript = new Transcript();
        transcript.setModules(modules);
        double cap = transcript.getCap();
        assertEquals(Double.valueOf(cap), expectedCapScore);
    }

    /**
     * Assert that the given modules and cap goal will result in expected target grades
     * @param modules
     * @param capGoal
     * @param expectedTargetGrades
     */
    private void assertTargetGradesEquals(List<Module> modules, Double capGoal, List<String> expectedTargetGrades) {
        Transcript transcript = new Transcript();
        transcript.setModules(modules);
        transcript.setCapGoal(capGoal);
        ObservableList<Module> targetModules = transcript.getTargetModuleGrade();

        if (expectedTargetGrades == null) {
            assertEquals(targetModules, null);
            return;
        }

        List<String> targetGrades = new ArrayList<>();
        targetModules.forEach(module -> targetGrades.add(module.getGrade().value));
        String targetGradesString = String.join(" ", targetGrades);
        String expectedTargetGradesString = String.join(" ", expectedTargetGrades);
        assertEquals(targetGradesString, expectedTargetGradesString);
    }

}
```
###### /java/seedu/address/model/module/GradeTest.java
``` java
    @Test
    public void isValidPoint() {
        assertTrue(Grade.isValidPoint(5.0));
        assertTrue(Grade.isValidPoint(4.0));
        assertTrue(Grade.isValidPoint(3.0));
        assertTrue(Grade.isValidPoint(2.0));
        assertTrue(Grade.isValidPoint(1.0));
        assertTrue(Grade.isValidPoint(0));
        assertTrue(Grade.isValidPoint(0.0));
        assertTrue(Grade.isValidPoint(4.5));
        assertTrue(Grade.isValidPoint(3.5));
        assertTrue(Grade.isValidPoint(2.5));
        assertTrue(Grade.isValidPoint(1.5));
        assertFalse(Grade.isValidPoint(6.0));
        assertFalse(Grade.isValidPoint(4.3));
        assertFalse(Grade.isValidPoint(0.5));
    }
    @Test
    public void getGradeValid() {
        assertTrue("A".equals(new Grade(5.0).value));
        assertTrue("A-".equals(new Grade(4.5).value));
        assertTrue("B+".equals(new Grade(4.0).value));
        assertTrue("B".equals(new Grade(3.5).value));
        assertTrue("B-".equals(new Grade(3.0).value));
        assertTrue("C+".equals(new Grade(2.5).value));
        assertTrue("C".equals(new Grade(2.0).value));
        assertTrue("D+".equals(new Grade(1.5).value));
        assertTrue("D".equals(new Grade(1.0).value));
        assertTrue("F".equals(new Grade(0).value));
    }

}
```
###### /java/systemtests/GoalCommandSystemTest.java
``` java
/**
 * System test for Goal Command
 */
public class GoalCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void setGoalSuccess() {
        /* Case: Set goal with valid value
         * -> goal command handled correctly
         */
        double newGoal = 4.5;
        assertGoalSuccess(newGoal);

        newGoal = 5.0;
        assertGoalSuccess(newGoal);
    }

    @Test
    public void setGoalFailure() {
        /* Case: Set goal with valid value
         * -> goal command handled correctly
         */
        double newGoal = -1;
        assertGoalFailure(newGoal);
    }

    /**
     * Assert that the given goal would result in a failure action.
     * @param goal
     */
    private void assertGoalFailure(double goal) {
        String expectedResultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoalCommand.MESSAGE_USAGE);
        assertCommandFailure(getCommandString(goal), getModel(), expectedResultMessage);
    }

    /**
     * Assert that the given goal would result in a successful action.
     * @param goal
     */
    public void assertGoalSuccess(double goal) {
        String expectedResultMessage = String.format(GoalCommand.MESSAGE_SUCCESS, goal);
        assertCommandSuccess(getCommandString(goal), getModel(), expectedResultMessage);
    }

    private String getCommandString(double goal) {
        return GoalCommand.COMMAND_WORD + " " + goal;
    }

    /**
     * Assert given command would be successful
     * @param command
     * @param expectedModel
     * @param expectedResultMessage
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
    }

    private void assertCommandFailure(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
    }
}
```
###### /java/systemtests/CapCommandSystemTest.java
``` java
public class CapCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void cap() {

        /**
         * Empty system should show cap = 0
         */
        executeCommand(CapCommand.COMMAND_WORD);
        double cap = 0.0;
        assertApplicationDisplaysExpected("", String.format(CapCommand.MESSAGE_SUCCESS, cap), getModel());
    }
}
```
