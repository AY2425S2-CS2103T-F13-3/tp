package hirehive.address.logic.commands;

import static hirehive.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.commands.queries.exceptions.QueryException;
import hirehive.address.model.Model;
import hirehive.address.model.ModelManager;
import hirehive.address.model.UserPrefs;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.model.person.Person;
import hirehive.address.testutil.TypicalIndexes;
import hirehive.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DisplayNoteCommand}.
 */
public class DisplayNoteCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validUniqueName_success() {
        Person personToDisplay = model.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        String nameToDisplay = personToDisplay.getName().fullName;

        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(nameToDisplay));
        DisplayNoteCommand noteCommand = new DisplayNoteCommand(nameToDisplay);

        String expectedMessage = String.format(DisplayNoteCommand.MESSAGE_SUCCESS, Messages.format(personToDisplay));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        try {
            List<Person> personsToDisplay = nameQuery.query(expectedModel);
        } catch (QueryException e) {
            fail();
        }

        assertCommandSuccess(noteCommand, model, new CommandResult(expectedMessage, false, false, true, false), expectedModel);
    }

    @Test
    public void execute_nonexistentName_throwsCommandException() {
        String nonexistentKeyword = "Nonexistent";
        DisplayNoteCommand noteCommand = new DisplayNoteCommand(nonexistentKeyword);

        assertThrows(CommandException.class, () -> noteCommand.execute(model), Messages.MESSAGE_NO_SUCH_PERSON);
    }

    @Test
    public void execute_multipleMatches_throwsCommandException() {
        String multipleMatches = "Meier";
        DisplayNoteCommand noteCommand = new DisplayNoteCommand(multipleMatches);

        assertThrows(CommandException.class, () -> noteCommand.execute(model), Messages.MESSAGE_MULTIPLE_PEOPLE_QUERIED_NAME);
    }

    @Test
    public void equals() {
        DisplayNoteCommand noteFirstCommand = new DisplayNoteCommand("Alice");
        DisplayNoteCommand noteSecondCommand = new DisplayNoteCommand("Bob");

        // same object -> returns true
        assertTrue(noteFirstCommand.equals(noteFirstCommand));

        // same values -> returns true
        DisplayNoteCommand noteFirstCommandCopy = new DisplayNoteCommand("Alice");
        assertTrue(noteFirstCommand.equals(noteFirstCommandCopy));

        // different type -> returns false
        assertFalse(noteFirstCommand.equals(1));

        // null object -> returns false
        assertFalse(noteFirstCommand.equals(null));

        // different values -> returns false
        assertFalse(noteFirstCommand.equals(noteSecondCommand));

    }

    @Test
    public void toStringMethod() {
        String name = "Alice";
        DisplayNoteCommand noteCommand = new DisplayNoteCommand(name);
        String expected = DisplayNoteCommand.class.getCanonicalName() + "{name="
                + name + "}";
        assertEquals(expected, noteCommand.toString());
    }
}
