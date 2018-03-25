# ChoChihTun
###### \java\seedu\address\logic\commands\AddTuteeCommandTest.java
``` java
public class AddTuteeCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTuteeCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Tutee validTutee = new TuteeBuilder().build();

        CommandResult commandResult = getAddTuteeCommandForTutee(validTutee, modelStub).execute();

        assertEquals(String.format(AddTuteeCommand.MESSAGE_SUCCESS, validTutee), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTutee), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        Tutee validTutee = new TuteeBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTuteeCommand.MESSAGE_DUPLICATE_PERSON);

        getAddTuteeCommandForTutee(validTutee, modelStub).execute();
    }

    @Test
    public void equals() {
        Tutee alice = new TuteeBuilder().withName("Alice").build();
        Tutee bob = new TuteeBuilder().withName("Bob").build();
        AddTuteeCommand addAliceCommand = new AddTuteeCommand(alice);
        AddTuteeCommand addBobCommand = new AddTuteeCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddTuteeCommand addAliceCommandCopy = new AddTuteeCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddTuteeCommand with the details of the given tutee.
     */
    private AddTuteeCommand getAddTuteeCommandForTutee(Tutee tutee, Model model) {
        AddTuteeCommand command = new AddTuteeCommand(tutee);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag, Person person) {
            fail("deleteTag should not be called when adding Person.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends AddTuteeCommandTest.ModelStub {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends AddTuteeCommandTest.ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\parser\AddPersonalTaskCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid format
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11/01/2018 11:11 1h30m Outing",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "11:11 1h30m Outing with friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "11/01/2018 1h30m Outing with friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "11/01/2018 11:11 Outing with friends",
                 String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "11/01/2018 11:11 1h Outing with friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "11/01/2018 11:11 30m Outing with friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "11:11 32/01/2018 1h30m Outing with friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "aa/01/2018 11:11 1h30m Outing with friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "32/01/2018 11:aa 1h30m Outing with friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "32/01/2018 11:11 1haam Outing with friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));

        // Invalid date
        assertParseFailure(parser, "32/01/2018 11:11 1h30m Outing with friends",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddPersonalTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "29/02/2018 11:11 1h30m Outing with friends",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddPersonalTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "31/04/2018 11:11 1h30m Outing with friends",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddPersonalTaskCommand.MESSAGE_USAGE);

        // Invalid time
        assertParseFailure(parser, "11/01/2018 31:11 1h30m Outing with friends",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddPersonalTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "11/01/2018 11:60 1h30m Outing with friends",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddPersonalTaskCommand.MESSAGE_USAGE);

        // Invalid duration
        assertParseFailure(parser, "11/01/2018 11:11 1h60m Outing with friends",
                MESSAGE_INVALID_DURATION + "\n" + AddPersonalTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "11/01/2018 11:11 24h0m Outing with friends",
                MESSAGE_INVALID_DURATION + "\n" + AddPersonalTaskCommand.MESSAGE_USAGE);
    }
}
```
###### \java\seedu\address\logic\parser\AddTuitionTaskCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid format
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "11/01/2018 11:11 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11:11 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11/01/2018 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11/01/2018 11:11 tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11/01/2018 11:11 1h tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11/01/2018 11:11 30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 11:11 32/01/2018 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "aaa 32/01/2018 11:11 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 aa/01/2018 11:11 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 32/01/2018 11:aa 1h30m tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 32/01/2018 11:11 1haam tuition homework",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));

        // Invalid date
        assertParseFailure(parser, "1 32/01/2018 11:11 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 29/02/2018 11:11 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 31/04/2018 11:11 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);

        // Invalid time
        assertParseFailure(parser, "1 11/01/2018 31:11 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 11/01/2018 11:60 1h30m tuition homework",
                MESSAGE_INVALID_DATE_TIME + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);

        // Invalid duration
        assertParseFailure(parser, "1 11/01/2018 11:11 1h60m tuition homework",
                MESSAGE_INVALID_DURATION + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 11/01/2018 11:11 24h0m tuition homework",
                MESSAGE_INVALID_DURATION + "\n" + AddTuitionTaskCommand.MESSAGE_USAGE);
    }
}
```
###### \java\seedu\address\logic\parser\AddTuteeCommandParserTest.java
``` java
public class AddTuteeCommandParserTest {
    private AddTuteeCommandParser parser = new AddTuteeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Tutee expectedTutee = new TuteeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withGrade(VALID_GRADE_BOB).withEducationLevel(VALID_EDUCATION_LEVEL_BOB).withSchool(VALID_SCHOOL_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple subjects - last subject accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_AMY + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple grades - last grade accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_AMY + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple education levels - last education level accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_AMY + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple schools - last school accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_AMY
                + SCHOOL_DESC_BOB + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTutee));

        // multiple tags - all accepted
        Tutee expectedTuteeMultipleTags = new TuteeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withGrade(VALID_GRADE_BOB).withEducationLevel(VALID_EDUCATION_LEVEL_BOB).withSchool(VALID_SCHOOL_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddTuteeCommand(expectedTuteeMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Tutee expectedTutee = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSubject(VALID_SUBJECT_AMY)
                .withGrade(VALID_GRADE_AMY).withEducationLevel(VALID_EDUCATION_LEVEL_AMY)
                .withSchool(VALID_SCHOOL_AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY,
                new AddTuteeCommand(expectedTutee));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing subject prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing grade prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing education level prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + SCHOOL_DESC_BOB,
                expectedMessage);

        // missing school prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_SUBJECT_DESC + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Subject.MESSAGE_SUBJECT_CONSTRAINTS);

        // invalid grade
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + INVALID_GRADE_DESC + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Grade.MESSAGE_GRADE_CONSTRAINTS);

        // invalid education level
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + INVALID_EDUCATION_LEVEL + SCHOOL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, EducationLevel.MESSAGE_EDUCATION_LEVEL_CONSTRAINTS);

        // invalid school
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + INVALID_SCHOOL
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, School.MESSAGE_SCHOOL_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + GRADE_DESC_BOB + EDUCATION_LEVEL_DESC_BOB + SCHOOL_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // three invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + SUBJECT_DESC_BOB + GRADE_DESC_BOB + INVALID_EDUCATION_LEVEL + SCHOOL_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + GRADE_DESC_BOB + INVALID_EDUCATION_LEVEL
                        + SCHOOL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\ScheduleTest.java
``` java
public class ScheduleTest {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Generates a list of existing tasks
     */
    private static void createTaskList() {
        Schedule.taskList.add(new TuitionTask(
                "Anne", LocalDateTime.parse("11/01/2011 22:00", formatter), "1h30m", "tuition 1"));
        Schedule.taskList.add(new PersonalTask(
                LocalDateTime.parse("15/01/2011 22:00", formatter), "2h30m", "personal task 1"));
        Schedule.taskList.add(new PersonalTask(
                LocalDateTime.parse("13/01/2011 11:00", formatter), "1h0m", "personal task 2"));
    }

    @Test
    public void isTaskClash_invalidTaskDateAndTime_false() {
        createTaskList();

        // New task is on another day
        assertFalse(Schedule.isTaskClash(LocalDateTime.parse("17/01/2011 11:00", formatter), "1h0m"));

        // New task ends right before start of an existing task
        assertFalse(Schedule.isTaskClash(LocalDateTime.parse("11/01/2011 11:00", formatter), "1h0m"));

        // New task starts right after the end of an existing task
        assertFalse(Schedule.isTaskClash(LocalDateTime.parse("16/01/2011 00:30", formatter), "2h0m"));
    }

    @Test
    public void isTaskClash_validTaskDateAndTime_true() {
        createTaskList();

        // New task starts at the same time as an existing task
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("11/01/2011 22:00", formatter), "2h0m"));

        // New task starts during an existing task
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("15/01/2011 22:30", formatter), "2h0m"));

        // New task ends at the same time as an existing task
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("13/01/2011 11:30", formatter), "0h30m"));

        // New task ends during an existing task
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("13/01/2011 10:00", formatter), "1h30m"));

        // New task is within an existing task completely
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("15/01/2011 22:30", formatter), "1h30m"));

        // Existing task is within the new task completely
        assertTrue(Schedule.isTaskClash(LocalDateTime.parse("11/01/2011 21:00", formatter), "4h0m"));

    }

}
```
###### \java\seedu\address\model\tutee\EducationLevelTest.java
``` java
public class EducationLevelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EducationLevel(null));
    }

    @Test
    public void constructor_invalidEducationLevel_throwsIllegalArgumentException() {
        String invalidEducationLevel = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EducationLevel(invalidEducationLevel));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> EducationLevel.isValidEducationLevel(null));

        // invalid phone numbers
        assertFalse(EducationLevel.isValidEducationLevel("")); // empty string
        assertFalse(EducationLevel.isValidEducationLevel(" ")); // spaces only
        assertFalse(EducationLevel.isValidEducationLevel("91")); // numbers
        assertFalse(EducationLevel.isValidEducationLevel("university")); // not the specified education level
        assertFalse(EducationLevel.isValidEducationLevel("primary5")); // contains number
        assertFalse(EducationLevel.isValidEducationLevel("primary@")); // contains special characters

        // valid phone numbers
        assertTrue(EducationLevel.isValidEducationLevel("primary")); // primary school
        assertTrue(EducationLevel.isValidEducationLevel("secondary")); // secondary school
        assertTrue(EducationLevel.isValidEducationLevel("junior college")); // junior college
        assertTrue(EducationLevel.isValidEducationLevel("SeCoNdaRy")); // Capital
    }

}
```
###### \java\seedu\address\model\tutee\GradeTest.java
``` java
public class GradeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructor_invalidGrade_throwsIllegalArgumentException() {
        String invalidGrade = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Grade(invalidGrade));
    }

    @Test
    public void isValidGrade() {
        // null subject grade
        Assert.assertThrows(NullPointerException.class, () -> Grade.isValidGrade(null));

        // invalid subject grade
        assertFalse(Grade.isValidGrade("")); // empty string
        assertFalse(Grade.isValidGrade(" ")); // spaces only
        assertFalse(Grade.isValidGrade("9112")); // only contains numbers
        assertFalse(Grade.isValidGrade("pass")); // more than 2 alphabet
        assertFalse(Grade.isValidGrade("+B")); // special character before alphabet
        assertFalse(Grade.isValidGrade("B -")); // spaces within digits

        // valid subject grade
        assertTrue(Grade.isValidGrade("A+")); // 1 alphabet followed by a special character
        assertTrue(Grade.isValidGrade("B")); // only 1 alphabet
        assertTrue(Grade.isValidGrade("b")); // small letter
        assertTrue(Grade.isValidGrade("C5")); // number after alphabet
    }

}
```
###### \java\seedu\address\model\tutee\SchoolTest.java
``` java
public class SchoolTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new School(null));
    }

    @Test
    public void constructor_invalidSchool_throwsIllegalArgumentException() {
        String invalidSchool = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new School(invalidSchool));
    }

    @Test
    public void isValidSchool() {
        // null school name
        Assert.assertThrows(NullPointerException.class, () -> School.isValidSchool(null));

        // invalid school name
        assertFalse(School.isValidSchool("")); // empty string
        assertFalse(School.isValidSchool(" ")); // spaces only
        assertFalse(School.isValidSchool("^")); // only non-alphabetic characters
        assertFalse(School.isValidSchool("bedok primary school*")); // contains non-alphabetic characters
        assertFalse(School.isValidSchool("911")); // numbers only
        assertFalse(School.isValidSchool("bedok12 secondary school")); // contains numbers

        // valid school name
        assertTrue(School.isValidSchool("victoria junior college")); // alphabets only
        assertTrue(School.isValidSchool("Victoria Junior College")); // with capital letters
        assertTrue(School.isValidSchool("The longest name school primary school")); // long subject name
    }

}
```
###### \java\seedu\address\model\tutee\SubjectTest.java
``` java
public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidSubject() {
        // null subject
        Assert.assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid subject name
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only
        assertFalse(Subject.isValidSubject("^")); // only non-alphabetic characters
        assertFalse(Subject.isValidSubject("economics*")); // contains non-alphabetic characters
        assertFalse(Subject.isValidSubject("911")); // numbers only
        assertFalse(Subject.isValidSubject("math12")); // contains numbers

        // valid subject name
        assertTrue(Subject.isValidSubject("social studies")); // alphabets only
        assertTrue(Subject.isValidSubject("Social Studies")); // with capital letters
        assertTrue(Subject.isValidSubject("introduction to fluid dynamics")); // long subject name
    }

}
```
###### \java\seedu\address\testutil\TuteeBuilder.java
``` java
/**
 * A utility class to help with building Tutee objects.
 */
public class TuteeBuilder extends PersonBuilder {
    public static final String DEFAULT_SUBJECT = "social studies";
    public static final String DEFAULT_GRADE = "B-";
    public static final String DEFAULT_EDUCATION_LEVEL = "primary";
    public static final String DEFAULT_SCHOOL = "fengshan primary school";

    private Subject subject;
    private Grade grade;
    private EducationLevel educationLevel;
    private School school;

    public TuteeBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        subject = new Subject(DEFAULT_SUBJECT);
        grade = new Grade(DEFAULT_GRADE);
        educationLevel = new EducationLevel(DEFAULT_EDUCATION_LEVEL);
        school = new School(DEFAULT_SCHOOL);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        tags.add(new Tag("Tutee"));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TuteeBuilder(Tutee tuteeToCopy) {
        name = tuteeToCopy.getName();
        phone = tuteeToCopy.getPhone();
        email = tuteeToCopy.getEmail();
        address = tuteeToCopy.getAddress();
        subject = tuteeToCopy.getSubject();
        grade = tuteeToCopy.getGrade();
        educationLevel = tuteeToCopy.getEducationLevel();
        school = tuteeToCopy.getSchool();
        tags = new HashSet<>(tuteeToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Tutee} that we are building.
     */
    public TuteeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        this.tags.add(new Tag("Tutee"));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }
    /**
     * Sets the {@code Grade} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
        return this;
    }
    /**
     * Sets the {@code EducationLevel} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withEducationLevel(String educationLevel) {
        this.educationLevel = new EducationLevel(educationLevel);
        return this;
    }
    /**
     * Sets the {@code School} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withSchool(String school) {
        this.school = new School(school);
        return this;
    }


    public Tutee build() {
        return new Tutee(name, phone, email, address, subject, grade, educationLevel, school, tags);
    }

}
```
###### \java\seedu\address\testutil\TuteeUtil.java
``` java
/**
 * A utility class for Tutee.
 */
public class TuteeUtil {

    /**
     * Returns an addtutee command string for adding the {@code tutee}.
     */
    public static String getAddTuteeCommand(Tutee tutee) {
        return AddTuteeCommand.COMMAND_WORD + " " + getTuteeDetails(tutee);
    }

    /**
     * Returns the part of command string for the given {@code tutee}'s details.
     */
    public static String getTuteeDetails(Tutee tutee) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + tutee.getName().fullName + " ");
        sb.append(PREFIX_PHONE + tutee.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + tutee.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + tutee.getAddress().value + " ");
        sb.append(PREFIX_SUBJECT + tutee.getSubject().subject + " ");
        sb.append(PREFIX_GRADE + tutee.getGrade().grade + " ");
        sb.append(PREFIX_EDUCATION_LEVEL + tutee.getEducationLevel().educationLevel + " ");
        sb.append(PREFIX_SCHOOL + tutee.getSchool().school + " ");
        tutee.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TypicalTutees.java
``` java
/**
 * A utility class containing a list of {@code Tutee} objects to be used in tests.
 */
public class TypicalTutees {

    public static final Tutee ALICETUTEE = new TuteeBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withSubject("mathematics").withGrade("C+").withEducationLevel("secondary")
            .withSchool("nanhua high school").withTags("friends").build();
    public static final Tutee CARLTUTEE = new TuteeBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withSubject("history").withGrade("B")
            .withEducationLevel("secondary").withSchool("wall street high school").build();

    // Manually added
    public static final Tutee HOONTUTEE = new TuteeBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withSubject("economics").withGrade("A1")
            .withEducationLevel("secondary").withSchool("changi secondary school").build();
    public static final Tutee IDATUTEE = new TuteeBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withSubject("english").withGrade("B3")
            .withEducationLevel("secondary").withSchool("tanjong katong secondary school").build();

    // Manually added - Tutee's details found in {@code CommandTestUtil}
    public static final Tutee AMYTUTEE = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSubject(VALID_SUBJECT_AMY)
            .withGrade(VALID_GRADE_AMY).withEducationLevel(VALID_EDUCATION_LEVEL_AMY).withSchool(VALID_SCHOOL_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Tutee BOBTUTEE = new TuteeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
            .withGrade(VALID_GRADE_BOB).withEducationLevel(VALID_EDUCATION_LEVEL_BOB).withSchool(VALID_SCHOOL_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTutees() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICETUTEE, CARLTUTEE));
    }
}
```
###### \java\systemtests\AddTuteeCommandSystemTest.java
``` java
public class AddTuteeCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void addtutee() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Tutee toAdd = AMYTUTEE;
        String command = "   " + AddTuteeCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + SUBJECT_DESC_AMY + GRADE_DESC_AMY
                + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a tutee with all fields same as another tutee in the address book except name -> added */
        toAdd = new TuteeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withSubject(VALID_SUBJECT_AMY).withGrade(VALID_GRADE_AMY)
                .withEducationLevel(VALID_EDUCATION_LEVEL_AMY).withSchool(VALID_SCHOOL_AMY).withTags(VALID_TAG_FRIEND)
                .build();
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a tutee with all fields same as another tutee in the address book except phone -> added */
        toAdd = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).withSubject(VALID_SUBJECT_AMY)
                .withGrade(VALID_GRADE_AMY).withEducationLevel(VALID_EDUCATION_LEVEL_AMY).withSchool(VALID_SCHOOL_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a tutee with all fields same as another tutee in the address book except email -> added */
        toAdd = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_AMY).withSubject(VALID_SUBJECT_AMY).withGrade(VALID_GRADE_AMY)
                .withEducationLevel(VALID_EDUCATION_LEVEL_AMY).withSchool(VALID_SCHOOL_AMY).withTags(VALID_TAG_FRIEND)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except address -> added */
        toAdd = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_AMY).withGrade(VALID_GRADE_AMY)
                .withEducationLevel(VALID_EDUCATION_LEVEL_AMY).withSchool(VALID_SCHOOL_AMY).withTags(VALID_TAG_FRIEND)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICETUTEE);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOBTUTEE;
        command = AddTuteeCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOONTUTEE);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDATUTEE);

        /* ------------------------ Perform add operation while a person card is selected --------------------------- */

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARLTUTEE);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate tutee -> rejected */
        command = TuteeUtil.getAddTuteeCommand(HOONTUTEE);
        assertCommandFailure(command, AddTuteeCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate tutee except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(Person)
        command = TuteeUtil.getAddTuteeCommand(HOONTUTEE) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddTuteeCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing subject -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing grade -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing education level -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + SUBJECT_DESC_AMY + GRADE_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing school -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addtutees " + TuteeUtil.getTuteeDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: invalid subject -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_SUBJECT_DESC + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Subject.MESSAGE_SUBJECT_CONSTRAINTS);

        /* Case: invalid grade -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + INVALID_GRADE_DESC + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Grade.MESSAGE_GRADE_CONSTRAINTS);

        /* Case: invalid education level -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + INVALID_EDUCATION_LEVEL + SCHOOL_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, EducationLevel.MESSAGE_EDUCATION_LEVEL_CONSTRAINTS);

        /* Case: invalid school -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + INVALID_SCHOOL + INVALID_TAG_DESC;
        assertCommandFailure(command, School.MESSAGE_SCHOOL_CONSTRAINTS);
    }
    /**
     * Executes the {@code AddTuteeCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddTuteeCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Tutee toAdd) {
        assertCommandSuccess(TuteeUtil.getAddTuteeCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Tutee)}. Executes {@code command}
     * instead.
     * @see AddTuteeCommandSystemTest#assertCommandSuccess(Tutee)
     */
    private void assertCommandSuccess(String command, Tutee toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddTuteeCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddTuteeCommandSystemTest#assertCommandSuccess(String, Tutee)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
```
