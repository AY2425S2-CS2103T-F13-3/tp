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
import hirehive.address.testutil.EditPersonDescriptorBuilder;
import hirehive.address.testutil.PersonBuilder;
import hirehive.address.testutil.TypicalPersons;

public class NewNoteCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validUniqueName_success() {
        Person editedPerson = new PersonBuilder(TypicalPersons.ALICE).withNote("test").build();
        EditCommand.EditPersonDescriptor descriptor =
                new EditPersonDescriptorBuilder(editedPerson).build();
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(TypicalPersons.ALICE.getName().fullName));
        NewNoteCommand newNoteCommand = new NewNoteCommand(TypicalPersons.ALICE.getName().fullName, descriptor);

        String expectedMessage = String.format(NewNoteCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Person> personsToAddNote;
        try {
            personsToAddNote = nameQuery.query(expectedModel);
            expectedModel.setPerson(personsToAddNote.get(0), editedPerson);
        } catch (QueryException e) {
            fail();
        }

        assertCommandSuccess(newNoteCommand, model, new CommandResult(expectedMessage, false, false, true, true), expectedModel);
    }

    @Test
    public void execute_nonexistentName_throwsCommandException() {
        String name = "Nonexistent";
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().build();
        NewNoteCommand newNoteCommand = new NewNoteCommand(name, descriptor);

        assertThrows(CommandException.class, () -> newNoteCommand.execute(model), Messages.MESSAGE_NO_SUCH_PERSON);
    }

    @Test
    public void execute_multipleMatches_throwsCommandException() {
        String name = "Meier";
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().build();
        NewNoteCommand newNoteCommand = new NewNoteCommand(name, descriptor);

        assertThrows(CommandException.class, () -> newNoteCommand.execute(model), Messages.MESSAGE_MULTIPLE_PEOPLE_QUERIED_NAME);
    }

    @Test
    public void equals() {
        EditCommand.EditPersonDescriptor firstDescriptor = new EditPersonDescriptorBuilder().withNote("first").build();
        EditCommand.EditPersonDescriptor secondDescriptor = new EditPersonDescriptorBuilder().withNote("second").build();

        NewNoteCommand newNoteFirstCommand = new NewNoteCommand("Alice", firstDescriptor);
        NewNoteCommand newNoteSecondCommand = new NewNoteCommand("Bob", secondDescriptor);

        // same object -> returns true
        assertTrue(newNoteFirstCommand.equals(newNoteFirstCommand));
        assertTrue(newNoteSecondCommand.equals(newNoteSecondCommand));

        // same values -> returns true
        NewNoteCommand newNoteFirstCopy = new NewNoteCommand("Alice", firstDescriptor);
        NewNoteCommand newNoteSecondCopy = new NewNoteCommand("Bob", secondDescriptor);
        assertTrue(newNoteFirstCommand.equals(newNoteFirstCopy));
        assertTrue(newNoteSecondCommand.equals(newNoteSecondCopy));

        // different type -> returns false
        assertFalse(newNoteFirstCommand.equals(0));
        assertFalse(newNoteSecondCommand.equals(true));

        // null object -> returns false
        assertFalse(newNoteFirstCommand.equals(null));
        assertFalse(newNoteSecondCommand.equals(null));

        // different values -> returns false
        assertFalse(newNoteFirstCommand.equals(newNoteSecondCommand));
        NewNoteCommand newNoteFirstSecond = new NewNoteCommand("Alice", secondDescriptor);
        NewNoteCommand newNoteSecondFirst = new NewNoteCommand("Bob", firstDescriptor);
        assertFalse(newNoteFirstCommand.equals(newNoteFirstSecond));
        assertFalse(newNoteFirstCommand.equals(newNoteSecondFirst));
        assertFalse(newNoteSecondCommand.equals(newNoteFirstSecond));
        assertFalse(newNoteSecondCommand.equals(newNoteSecondFirst));
    }

    @Test
    public void toStringMethod() {
        String name = TypicalPersons.ALICE.getName().fullName;
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().build();
        NewNoteCommand newNoteCommand = new NewNoteCommand(TypicalPersons.ALICE.getName().fullName, descriptor);

        String expected = NewNoteCommand.class.getCanonicalName() + "{name=" + name + ", editPersonDescriptor=" + descriptor + "}";
        assertEquals(expected, newNoteCommand.toString());
    }
}
