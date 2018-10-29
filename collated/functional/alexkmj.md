# alexkmj
###### /java/seedu/address/logic/LogicManager.java
``` java
    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        transcriptParser = new TranscriptParser();
        addressBookParser = new AddressBookParser();
    }

```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            if (commandText.trim().startsWith("c_")) {
                Command command = transcriptParser.parseCommand(commandText);
                return command.execute(model, history);
            }

            Command command = addressBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
```
###### /java/seedu/address/logic/Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of modules */
    ObservableList<Module> getFilteredModuleList();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
```
###### /java/seedu/address/logic/parser/AddModuleCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModuleCommand parse(String args) throws ParseException {
        String[] tokenizedArgs = ParserUtil.tokenize(args);
        validateNumOfArgs(tokenizedArgs, 4, 5);

        int index = 0;

        Code code = ParserUtil.parseCode(tokenizedArgs[index++]);
        Year year = ParserUtil.parseYear(tokenizedArgs[index++]);
        Semester semester = ParserUtil.parseSemester(tokenizedArgs[index++]);
        Credit credit = ParserUtil.parseCredit(tokenizedArgs[index++]);

        Module module = null;

        if (tokenizedArgs.length == 4) {
            module = new Module(code, year, semester, credit, null, false);
        } else {
            Grade grade = ParserUtil.parseGrade(tokenizedArgs[index++]);
            module = new Module(code, year, semester, credit, grade, true);
        }

        return new AddModuleCommand(module);
    }
}
```
###### /java/seedu/address/logic/parser/TranscriptParser.java
``` java
/**
 * Parses user input.
 */
public class TranscriptParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern
            .compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord")
                .replaceFirst("c_", "");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case AddModuleCommand.COMMAND_WORD:
            return new AddModuleCommandParser().parse(arguments);
        case CapCommand.COMMAND_WORD:
            return new CapCommand();
        case GoalCommand.COMMAND_WORD:
            return new GoalCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Tokenizes args into an array of args. Checks if args is null and trims leading and trailing
     * whitespaces.
     *
     * @param args the target args that would be tokenize
     * @return array of args
     */
    public static String[] tokenize(String args) {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        return trimmedArgs.split(" ");
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Validates the number of arguments. If number of arguments is not within the bounds,
     * ParseException will be thrown.
     *
     * @throws ParseException if the number of arguments is invalid
     */
    public static void validateNumOfArgs(String[] args, int min, int max) throws ParseException {
        if (args.length < min || args.length > max) {
            throw new ParseException("Invalid number of arguments!"
                    + "Number of arguments should be more than or equal to " + min
                    + " and less than or equal to " + max);
        }
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String code} into a {@code Code}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code code} is invalid.
     */
    public static Code parseCode(String args) throws ParseException {
        requireNonNull(args);
        String trimmedCode = args.trim();
        if (!Code.isValidCode(trimmedCode)) {
            throw new ParseException(Code.MESSAGE_CODE_CONSTRAINTS);
        }
        return new Code(trimmedCode);
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String year} into a {@code Year}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String args) throws ParseException {
        requireNonNull(args);
        String trimmedYear = args.trim();
        if (!Year.isValidYear(trimmedYear)) {
            throw new ParseException(Year.MESSAGE_YEAR_CONSTRAINTS);
        }
        return new Year(trimmedYear);
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String semester} into a {@code Semester}. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if the given {@code semester} is invalid.
     */
    public static Semester parseSemester(String args) throws ParseException {
        requireNonNull(args);
        String trimmedSemester = args.trim();
        if (!Semester.isValidSemester(trimmedSemester)) {
            throw new ParseException(Semester.MESSAGE_SEMESTER_CONSTRAINTS);
        }
        return new Semester(trimmedSemester);
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String credit} into a {@code Credit}. Leading and trailing whitespaces will
     * be trimmed.
     *
     * @throws ParseException if the given {@code credit} is invalid.
     */
    public static Credit parseCredit(String args) throws ParseException {
        requireNonNull(args);
        String trimmedCredit = args.trim();
        int intCredit = Integer.parseInt(trimmedCredit);
        if (!Credit.isValidCredit(intCredit)) {
            throw new ParseException(Credit.MESSAGE_CREDIT_CONSTRAINTS);
        }
        return new Credit(intCredit);
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String grade} into a {@code Grade}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static Grade parseGrade(String args) throws ParseException {
        requireNonNull(args);
        String trimmedGrade = args.trim();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new ParseException(Grade.MESSAGE_GRADE_CONSTRAINTS);
        }
        return new Grade(trimmedGrade);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Clears existing backing model and replaces with the newly provided data.
     * @param replacement the replacement.
     */
    void resetData(ReadOnlyTranscript replacement);

    /** Clears existing backing model and replaces with the provided new data. TODO: REMOVE*/
    void resetData(ReadOnlyAddressBook newData);

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Returns the Transcript.
     *
     * @return read only version of the transcript
     */
    ReadOnlyTranscript getTranscript();

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Returns true if a module with the same identity as {@code module} exists in the transcript.
     *
     * @param module module to find in the transcript
     * @return true if module exists in transcript
     */
    boolean hasModule(Module module);

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Deletes the given module.
     * <p>
     * The module must exist in the transcript.
     *
     * @param target module to be deleted from the transcript
     */
    void deleteModule(Module target);

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Adds the given module.
     * <p>
     * {@code module} must not already exist in the transcript.
     *
     * @param module module to be added into the transcript
     */
    void addModule(Module module);

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the transcript. The module identity of {@code editedModule}
     * must not be the same as another existing module in the transcript.
     *
     * @param target module to be updated
     * @param editedModule the updated version of the module
     */
    void updateModule(Module target, Module editedModule);

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Returns an unmodifiable view of the filtered module list.
     */
    ObservableList<Module> getFilteredModuleList();

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Returns true if the model has previous transcript states to restore.
     */
    boolean canUndoTranscript();

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Returns true if the model has undone transcript states to restore.
     */
    boolean canRedoTranscript();

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Restores the model's transcript to its previous state.
     */
    void undoTranscript();

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Restores the model's transcript to its previously undone state.
     */
    void redoTranscript();

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Saves the current transcript state for undo/redo.
     */
    void commitTranscript();

    /**
     * Get the cap goal of the current transcript
     */
    CapGoal getCapGoal();

    /**
     * Set the cap goal of the current transcript
     */
    void updateCapGoal(double capGoal);

    /**
     * Returns the CAP based on the current Transcript records
     */
    double getCap();

    /** Returns the AddressBook TODO: REMOVE*/
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     * TODO: REMOVE
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     * TODO: REMOVE
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     * TODO: REMOVE
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person
     * in the address book.
     * TODO: REMOVE
     */
    void updatePerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list TODO: REMOVE */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     * TODO: REMOVE
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     * TODO: REMOVE
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     * TODO: REMOVE
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     * TODO: REMOVE
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     * TODO: REMOVE
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     * TODO: REMOVE
     */
    void commitAddressBook();
}
```
###### /java/seedu/address/model/ReadOnlyTranscript.java
``` java
/**
 * Unmodifiable view of a Transcript.
 */
@JsonDeserialize(using = JsonTranscriptDeserializer.class)
public interface ReadOnlyTranscript {
    /**
     * Returns an unmodifiable view of the module list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    private final VersionedTranscript versionedTranscript;
```
###### /java/seedu/address/model/ModelManager.java
``` java
    private final FilteredList<Module> filteredModules;

```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Initializes a ModelManager with the given transcript and userPrefs.
     */
    public ModelManager(ReadOnlyTranscript transcript, UserPrefs userPrefs) {
        super();
        requireAllNonNull(transcript, userPrefs);

        logger.fine("Initializing with transcript: " + transcript + " and user prefs " + userPrefs);

        versionedTranscript = new VersionedTranscript(transcript);
        filteredModules = new FilteredList<>(versionedTranscript.getModuleList());

        //TODO: REMOVE
        versionedAddressBook = new VersionedAddressBook(new AddressBook());
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
    }

    public ModelManager() {
        this(new Transcript(), new UserPrefs());
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     * TODO: REMOVE
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        versionedTranscript = new VersionedTranscript(new Transcript());
        filteredModules = new FilteredList<>(versionedTranscript.getModuleList());
    }

    @Override
    public void resetData(ReadOnlyTranscript newData) {
        versionedTranscript.resetData(newData);
        indicateTranscriptChanged();
    }

    //TODO: REMOVE
    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public ReadOnlyTranscript getTranscript() {
        return versionedTranscript;
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    /** Raises an event to indicate the model has changed */
    private void indicateTranscriptChanged() {
        raise(new TranscriptChangedEvent(versionedTranscript));
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return versionedTranscript.hasModule(module);
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void deleteModule(Module target) {
        versionedTranscript.removeModule(target);
        indicateTranscriptChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void addModule(Module module) {
        versionedTranscript.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        indicateTranscriptChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        versionedTranscript.updateModule(target, editedModule);
        indicateTranscriptChanged();
    }

    //=========== Filtered Module List Accessors =============================================================

```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedTranscript}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return FXCollections.unmodifiableObservableList(filteredModules);
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public boolean canUndoTranscript() {
        return versionedTranscript.canUndo();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public boolean canRedoTranscript() {
        return versionedTranscript.canRedo();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void undoTranscript() {
        versionedTranscript.undo();
        indicateTranscriptChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void redoTranscript() {
        versionedTranscript.redo();
        indicateTranscriptChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void commitTranscript() {
        versionedTranscript.commit();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
            && filteredPersons.equals(other.filteredPersons) // TODO: REMOVE
            && filteredModules.equals(other.filteredModules);
    }
}
```
###### /java/seedu/address/model/VersionedTranscript.java
``` java
/**
 * {@code Transcript} that keeps track of its own history.
 */
public class VersionedTranscript extends Transcript {

    private final List<ReadOnlyTranscript> transcriptStateList;
    private int currentStatePointer;

    public VersionedTranscript(ReadOnlyTranscript initialState) {
        super(initialState);

        transcriptStateList = new ArrayList<>();
        transcriptStateList.add(new Transcript(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code Transcript} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        transcriptStateList.add(new Transcript(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        transcriptStateList.subList(currentStatePointer + 1, transcriptStateList.size()).clear();
    }

    /**
     * Restores the transcript to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(transcriptStateList.get(currentStatePointer));
    }

    /**
     * Restores the transcript to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(transcriptStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has transcript states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has transcript states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < transcriptStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedTranscript)) {
            return false;
        }

        VersionedTranscript otherVersionedTranscript = (VersionedTranscript) other;

        // state check
        return super.equals(otherVersionedTranscript)
                && transcriptStateList.equals(otherVersionedTranscript.transcriptStateList)
                && currentStatePointer == otherVersionedTranscript.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of transcriptState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of transcriptState list, unable to redo.");
        }
    }
}
```
###### /java/seedu/address/model/util/ModuleBuilder.java
``` java
/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_CODE = "CS2103";
    public static final int DEFAULT_YEAR = 1;
    public static final String DEFAULT_SEMESTER = Semester.SEMESTER_ONE;
    public static final int DEFAULT_CREDIT = 4;
    public static final String DEFAULT_GRADE = "A+";
    public static final boolean DEFAULT_COMPLETED = true;

    private Code code;
    private Year year;
    private Semester semester;
    private Credit credit;
    private Grade grade;
    private boolean completed;

    public ModuleBuilder() {
        code = new Code(DEFAULT_CODE);
        year = new Year(DEFAULT_YEAR);
        semester = new Semester(DEFAULT_SEMESTER);
        credit = new Credit(DEFAULT_CREDIT);
        grade = new Grade(DEFAULT_GRADE);
        completed = DEFAULT_COMPLETED;
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code personToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        code = moduleToCopy.getCode();
        year = moduleToCopy.getYear();
        semester = moduleToCopy.getSemester();
        credit = moduleToCopy.getCredits();
        grade = moduleToCopy.getGrade();
        completed = moduleToCopy.hasCompleted();
    }

    /**
     * Sets the {@code Code} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCode(String code) {
        this.code = new Code(code);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code Module} that we are building.
     */
    public ModuleBuilder withYear(int year) {
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code Module} that we are building.
     */
    public ModuleBuilder withSemester(String semester) {
        this.semester = new Semester(semester);
        return this;
    }

    /**
     * Sets the {@code Credit} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCredit(int credit) {
        this.credit = new Credit(credit);
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code Module} that we are building.
     */
    public ModuleBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code Module} that we are building to null.
     */
    public ModuleBuilder noGrade() {
        this.grade = null;
        return this;
    }

    /**
     * Sets the {@code completed} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public Module build() {
        return new Module(code, year, semester, credit, grade, completed);
    }
}
```
###### /java/seedu/address/model/Transcript.java
``` java
/**
 * Wraps all data at the transcript level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class Transcript implements ReadOnlyTranscript {

    private final UniqueModuleList modules;
    private CapGoal capGoal;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        modules = new UniqueModuleList();
    }

    public Transcript() {
        capGoal = new CapGoal();
    }

    /**
     * Creates an Transcript using the Modules in the {@code toBeCopied}
     */
    public Transcript(ReadOnlyTranscript toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Resets the existing data of this {@code Transcript} with {@code newData}.
     */
    public void resetData(ReadOnlyTranscript newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
    }

    //// module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the transcript.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the transcript.
     * The module must not already exist in the transcript.
     */
    public void addModule(Module p) {
        modules.add(p);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the transcript.
     * The module identity of {@code editedModule} must not be the same as another existing module in the transcript.
     */
    public void updateModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from this {@code Transcript}.
     * {@code key} must exist in the transcript.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

```
###### /java/seedu/address/model/module/Year.java
``` java
/**
 * Represents a Module's year in the transcript.
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidYear(int)}
 */
public class Year {

    public static final String MESSAGE_YEAR_CONSTRAINTS =
            "Year must be [1-5]. Example: 1 represents Year 1";

    /**
     * No whitespace allowed.
     */
    public static final String YEAR_VALIDATION_REGEX = "[1-5]";

    /**
     * Immutable year value.
     */
    public final int value;

    /**
     * Constructs an {@code Year}.
     *
     * @param year A valid year.
     */
    public Year(int year) {
        checkArgument(isValidYear(year), MESSAGE_YEAR_CONSTRAINTS);
        value = year;
    }

    /**
     * Constructs an {@code Year}.
     *
     * @param year A valid year.
     */
    public Year(String year) {
        requireNonNull(year);
        checkArgument(isValidYear(year), MESSAGE_YEAR_CONSTRAINTS);
        value = Integer.valueOf(year);
    }

    /**
     * Returns true if a given string is a valid year.
     *
     * @param year string to be tested for validity
     * @return true if given string is a valid year
     */
    public static boolean isValidYear(int year) {
        return isValidYear(Integer.toString(year));
    }

    /**
     * Returns true if a given string is a valid year.
     *
     * @param year string to be tested for validity
     * @return true if given string is a valid year
     */
    public static boolean isValidYear(String year) {
        return year.matches(YEAR_VALIDATION_REGEX);
    }

    /**
     * Returns the year the module was taken.
     *
     * @return year
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    /**
     * Compares the year value of both Year object.
     * <p>
     * This defines a notion of equality between two Year objects.
     *
     * @param other Year object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Year
                && value == ((Year) other).value);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
```
###### /java/seedu/address/model/module/Code.java
``` java
/**
 * Represents a Module's code in the transcript.
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class Code {

    /**
     * Describes the requirements for code value.
     */
    public static final String MESSAGE_CODE_CONSTRAINTS =
            "Code can take any values except whitespaces";

    /**
     * No whitespace allowed.
     */
    public static final String CODE_VALIDATION_REGEX = "^[^\\s]+$";

    /**
     * Immutable code value.
     */
    public final String value;

    /**
     * Constructs an {@code Code}.
     *
     * @param code A valid code.
     */
    public Code(String code) {
        requireNonNull(code);
        checkArgument(isValidCode(code), MESSAGE_CODE_CONSTRAINTS);
        value = code;
    }

    /**
     * Returns true if a given string is a valid code.
     *
     * @param code string to be tested for validity
     * @return true if given string is a valid code
     */
    public static boolean isValidCode(String code) {
        return code.matches(CODE_VALIDATION_REGEX);
    }

    /**
     * Returns the module code.
     *
     * @return module code
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares the module code value of both Code object.
     * <p>
     * This defines a notion of equality between two code objects.
     *
     * @param other Code object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Code
                && value.equals(((Code) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Constant for completed.
     */
    public static final boolean MODULE_COMPLETED = true;

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Constant for not completed.
     */
    public static final boolean MODULE_NOT_COMPLETED = false;

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Code for the module.
     */
    private final Code code;

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Year the module was taken.
     */
    private final Year year;

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Semester the module was taken.
     */
    private final Semester semester;

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Module credits awarded for completion this module.
     */
    private final Credit credits;

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Module grade awarded for completion this module.
     */
    private final Grade grade;

    /**
     * True if module has been completed. False if module has not been taken yet.
     */
    private final boolean completed;

```
###### /java/seedu/address/model/module/Module.java
``` java
    public Module(Code code, Year year, Semester semester, Credit credit, Grade grade,
            boolean completed) {
        requireNonNull(code);
        requireNonNull(year);
        requireNonNull(semester);
        requireNonNull(credit);

        this.code = code;
        this.year = year;
        this.semester = semester;
        this.credits = credit;
        this.grade = grade;
        this.completed = completed;
    }

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Returns the module code.
     *
     * @return module code
     */
    public Code getCode() {
        return code;
    }

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Returns the module credits awarded.
     *
     * @return module credits
     */
    public Credit getCredits() {
        return credits;
    }

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Returns the year in which the module was taken.
     *
     * @return year in which module was taken
     */
    public Year getYear() {
        return year;
    }

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Returns the semester in which the module was taken.
     *
     * @return semester in which module was taken
     */
    public Semester getSemester() {
        return semester;
    }

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Returns the module grade awarded.
     *
     * @return module grade
     */
    public Grade getGrade() {
        return grade;
    }

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Returns true if module has been completed and false if module has not been taken.
     *
     * @return true if module has been completed and false if module has not been taken
     */
    public boolean hasCompleted() {
        return completed;
    }

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Returns true if module code is the same.
     *
     * @return true if modue code is the same
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null && otherModule.getCode().equals(getCode());
    }

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Returns true if both modules are of the same object or contains the same set of data fields.
     * <p>
     * This defines a notion of equality between two modules.
     *
     * @param other other module to be compared with this Module object
     * @return true if both objects contains the same data fields
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;

        if (grade == null && otherModule.grade != null) {
            return false;
        }

        if (grade != null && otherModule.grade == null) {
            return false;
        }

        if (grade == null) {
            return otherModule.getCode().equals(getCode())
                    && otherModule.getYear().equals(getYear())
                    && otherModule.getSemester().equals(getSemester())
                    && otherModule.getCredits().equals(getCredits())
                    && otherModule.hasCompleted() == hasCompleted();
        }


        return otherModule.getCode().equals(getCode())
                && otherModule.getYear().equals(getYear())
                && otherModule.getSemester().equals(getSemester())
                && otherModule.getCredits().equals(getCredits())
                && otherModule.getGrade().equals(getGrade())
                && otherModule.hasCompleted() == hasCompleted();
    }

```
###### /java/seedu/address/model/module/Module.java
``` java
    /**
     * Returns the code, year, semester, credits, grade, is module completed.
     * <p>
     * Format: Code: CODE Year: YEAR Semester: SEMESTER Credits: CREDITS Grade: GRADE Completed:
     * COMPLETED
     *
     * @return information of this module
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        return builder.append("Code: ")
                .append(getCode())
                .append(" Year: ")
                .append(getYear())
                .append(" Semester: ")
                .append(getSemester())
                .append(" Credits: ")
                .append(getCredits())
                .append(" Grade: ")
                .append(getGrade())
                .append(" Completed: ")
                .append(hasCompleted())
                .toString();
    }

```
###### /java/seedu/address/model/module/Module.java
``` java
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(code, year, semester, credits, grade, completed);
    }
}
```
###### /java/seedu/address/model/module/UniqueModuleList.java
``` java
/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * A module is considered unique by comparing {@code moduleA.equals(moduleB)}.
 * <p>
 * As such, adding and updating of modules uses {@code moduleA.equals(moduleB)} for equality so as
 * to ensure that the module being added or updated is unique in terms of identity in the
 * UniqueModuleList.
 */
public class UniqueModuleList implements Iterable<Module> {

    /**
     * Creates an observable list of module.
     * See {@link Module}.
     */
    private final ObservableList<Module> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     * See {@link Module}.
     *
     * @param toCheck the module that is being checked against
     * @return true if list contains equivalent module
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameModule);
    }

    /**
     * Adds a module to the list.
     * <p>
     * The {@link Module} must not have already exist in the list.
     *
     * @param toAdd the module that would be added into the list
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the module {@code target} in the list with {@code editedModule}.
     * <p>
     * {@code target} must exist in the list. The {@link Module} identity of {@code editedModule}
     * must not be the same as another existing module in the list.
     *
     * @param target the module to be replaced
     * @param editedModule the modue that replaces the old module
     */
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!target.equals(editedModule) && contains(editedModule)) {
            throw new DuplicateModuleException();
        }

        internalList.set(index, editedModule);
    }

    /**
     * Replaces the {@link #internalList} of this UniqueModuleList with the internalList of the
     * replacement.
     *
     * @param replacement the UniqueModuleList object that contains the internalList that is
     * replacing the old internalList
     */
    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code modules}. {@code modules} must not contain
     * duplicate modules.
     *
     * @param modules the list of module that would replace the old list
     */
    public void setModules(List<Module> modules) {
        requireAllNonNull(modules);
        if (!modulesAreUnique(modules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(modules);
    }

    /**
     * Removes the equivalent module from the list.
     * <p>
     * The {@link Module} must exist in the list.
     *
     * @param toRemove the module to be removed from the list
     */
    public void remove(Module toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return backing list as an unmodifiable {@code ObservableList}
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Returns true if {@code modules} contains only unique modules.
     *
     * @param modules the module list that is being checked
     * @return true if modules are unique and false if modules are not unique
     */
    private boolean modulesAreUnique(List<Module> modules) {
        return modules.size() == modules.parallelStream()
                .distinct()
                .count();
    }

```
###### /java/seedu/address/model/module/Grade.java
``` java
    /**
     * Describes the requirements for grade value.
     */
    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grade can be A+, A, A-, B+, B, B-, C+, C, D+, D, F, CS, CU";

    public static final String MESSAGE_POINT_CONSTRAINTS =
            "Score must be between [0, 5] with increments of 0.5 and not 0.5";

```
###### /java/seedu/address/model/module/Grade.java
``` java
    /**
     * No whitespace allowed.
     */
    public static final String GRADE_VALIDATION_REGEX =
            "A\\+|A\\-|A|B\\+|B\\-|B|C\\+|C|D\\+|D|F|CS|CU";

    /**
     * Static Unchangeable Mapping between Grade and Point
     */
    private static final Map<String, Double> MAP_GRADE_POINT;
    private static final Map<Double, String> MAP_POINT_GRADE;
    static {
        Map<String, Double> tempGradePointMap = new HashMap<>();
        Map<Double, String> tempPointGradeMap = new HashMap<>();
        tempGradePointMap.put("A+", 5.0);
        tempGradePointMap.put("A", 5.0);
        tempGradePointMap.put("A-", 4.5);
        tempGradePointMap.put("B+", 4.0);
        tempGradePointMap.put("B", 3.5);
        tempGradePointMap.put("B-", 3.0);
        tempGradePointMap.put("C+", 2.5);
        tempGradePointMap.put("C", 2.0);
        tempGradePointMap.put("D+", 1.5);
        tempGradePointMap.put("D", 1.0);
        tempGradePointMap.put("F", 0.0);

        for (Map.Entry<String, Double> entry : tempGradePointMap.entrySet()) {
            tempPointGradeMap.put(entry.getValue(), entry.getKey());
        }
        tempPointGradeMap.put(5.0, "A");

        MAP_GRADE_POINT = Collections.unmodifiableMap(tempGradePointMap);
        MAP_POINT_GRADE = Collections.unmodifiableMap(tempPointGradeMap);
    }

```
###### /java/seedu/address/model/module/Grade.java
``` java
    /**
     * Immutable grade value.
     */
    public final String value;

```
###### /java/seedu/address/model/module/Grade.java
``` java
    /**
     * Constructs an {@code Grade}.
     *
     * @param grade A valid grade.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_GRADE_CONSTRAINTS);
        value = grade;
    }

    /**
     * Constructs an {@code Grade} from point
     * @param point
     */
    public Grade(double point) {
        requireNonNull(point);
        checkArgument(isValidPoint(point), MESSAGE_POINT_CONSTRAINTS);
        value = mapPointToValue(point);
    }

    /**
     * Returns true if point is within [0, 5] and step by 0.5 and not 0.5
     * @param point
     * @return
     */
    public static boolean isValidPoint(double point) {
        double fraction = point - Math.floor(point);
        return point >= 0 && point <= 5 && (fraction == 0 || fraction == 0.5) && point != 0.5;
    }

    /**
     * Returns the letter grade the point should be mapped to.
     * @param point
     * @return
     */
    private String mapPointToValue(double point) {
        return MAP_POINT_GRADE.get(point);
    }

```
###### /java/seedu/address/model/module/Grade.java
``` java
    /**
     * Returns true if a given string is a valid grade.
     *
     * @param grade string to be tested for validity
     * @return true if given string is a valid grade
     */
    public static boolean isValidGrade(String grade) {
        return grade.matches(GRADE_VALIDATION_REGEX);
    }

    /**
     * Returns true if grade affects cap and false if grade does not affect cap.
     *
     * @return true if grade affects cap and false if grade does not affect cap.
     */
    public boolean affectsCap() {
        return !value.contentEquals("CS") && !value.contentEquals("CU");
    }

    /**
     * Returns the point equivalent of the grade or 0 if grade is invalid.
     *
     * @return point equivalent of the grade
     */
    public float getPoint() {
        if (MAP_GRADE_POINT.containsKey(value)) {
            return MAP_GRADE_POINT.get(value).floatValue();
        }
        return 0;
    }

```
###### /java/seedu/address/model/module/Grade.java
``` java
    /**
     * Returns the grade value.
     *
     * @return grade
     */
    @Override
    public String toString() {
        return value;
    }

```
###### /java/seedu/address/model/module/Grade.java
``` java
    /**
     * Compares the grade value of both Grade object.
     * <p>
     * This defines a notion of equality between two grade objects.
     *
     * @param other Grade object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Grade
                && value.equals(((Grade) other).value));
    }

```
###### /java/seedu/address/model/module/Grade.java
``` java
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/module/Credit.java
``` java
/**
 * Represents a Module's credits in the transcript.
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidCredit(int)}
 */
public class Credit {

    /**
     * Describes the requirements for credit value.
     */
    public static final String MESSAGE_CREDIT_CONSTRAINTS = "Credits must be a integer";

    /**
     * Immutable credit value.
     */
    public final int value;

    /**
     * Constructs an {@code Credit}.
     *
     * @param credits A valid credit.
     */
    public Credit(int credits) {
        requireNonNull(credits);
        checkArgument(isValidCredit(credits), MESSAGE_CREDIT_CONSTRAINTS);
        value = credits;
    }

    /**
     * Returns true if a given string is a valid credit.
     * <p>
     * Credit must be between 1 and 20
     *
     * @param credits string to be tested for validity
     * @return true if given string is a valid credit
     */
    public static boolean isValidCredit(int credits) {
        if (credits < 1) {
            return false;
        } else if (credits > 20) {
            return false;
        }

        return true;
    }

    /**
     * Returns the module credits value.
     *
     * @return module credits
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    /**
     * Compares the module credit value of both Credit object.
     * <p>
     * This defines a notion of equality between two credit objects.
     *
     * @param other Credit object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Credit
                && value == ((Credit) other).value);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
```
###### /java/seedu/address/model/module/Semester.java
``` java
/**
 * Represents a Module's semester in the transcript.
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidSemester(String)}
 * <p>
 * Legal values: 1, 2, s1, s2. 1
 * <p>
 * (Semester 1), 2 (Semester 2), s1 (Special Semester 1), s2 (Special Semester 2).
 */
public class Semester {

    /**
     * Describes the requirements for semester value.
     */
    public static final String MESSAGE_SEMESTER_CONSTRAINTS = "Semester can be 1, 2, s1 or s2";

    /**
     * No whitespace allowed.
     */
    public static final String SEMESTER_VALIDATION_REGEX = "1|2|s1|s2";

    /**
     * Constant for semester one.
     */
    public static final String SEMESTER_ONE = "1";

    /**
     * Constant for semester two.
     */
    public static final String SEMESTER_TWO = "2";

    /**
     * Constant for special semester one.
     */
    public static final String SEMESTER_SPECIAL_ONE = "s1";

    /**
     * Constant for special semester two.
     */
    public static final String SEMESTER_SPECIAL_TWO = "s2";

    /**
     * Immutable semester value.
     */
    public final String value;

    /**
     * Constructs an {@code Code}.
     *
     * @param semester A valid semester.
     */
    public Semester(String semester) {
        requireNonNull(semester);
        checkArgument(isValidSemester(semester), MESSAGE_SEMESTER_CONSTRAINTS);
        value = semester;
    }

    /**
     * Returns true if a given string is a valid semester.
     *
     * @param semester string to be tested for validity
     * @return true if given string is a valid semester
     */
    public static boolean isValidSemester(String semester) {
        return semester.matches(SEMESTER_VALIDATION_REGEX);
    }

    /**
     * Returns the semester value.
     *
     * @return grade
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares the semester value of both Semester object.
     * <p>
     * This defines a notion of equality between two semester objects.
     *
     * @param other Semester object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Semester
                && value.equals(((Semester) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
