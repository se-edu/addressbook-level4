package seedu.address.logic.commands;

public class ReversibleCommandTest {
    /*
    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());

    @Test
    public void rollback_validCommand_succeeds() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        deleteCommand.setData(model, new CommandHistory());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        ReadOnlyPerson toRemove = model.getFilteredPersonList().get(0);
        expectedModel.deletePerson(toRemove);

        String expectedMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, toRemove);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }*/
}
