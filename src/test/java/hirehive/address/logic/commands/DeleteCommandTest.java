package hirehive.address.logic.commands;

import static hirehive.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static hirehive.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.model.Model;
import hirehive.address.model.ModelManager;
import hirehive.address.model.UserPrefs;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.model.person.Person;
import hirehive.address.testutil.TypicalIndexes;
import hirehive.address.testutil.TypicalPersons;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        String nameToDelete = personToDelete.getName().fullName;

        List<String> nameKeywords = Arrays.asList(nameToDelete.split("\\s+"));
        System.out.println(nameKeywords);
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(nameKeywords));
        DeleteCommand deleteCommand = new DeleteCommand(nameQuery);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentName_throwsCommandException() {
        List<String> nonexistentKeywords = List.of("Nonexistent");
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(nonexistentKeywords));
        DeleteCommand deleteCommand = new DeleteCommand(nameQuery);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void equals() {
        NameQuery firstQuery = new NameQuery(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        NameQuery secondQuery = new NameQuery(new NameContainsKeywordsPredicate(Collections.singletonList("Bob")));

        DeleteCommand deleteFirstCommand = new DeleteCommand(firstQuery);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondQuery);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstQuery);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
