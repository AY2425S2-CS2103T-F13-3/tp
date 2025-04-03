package hirehive.address.logic.commands;

import static hirehive.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
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
import hirehive.address.model.tag.Tag;
import hirehive.address.testutil.EditPersonDescriptorBuilder;
import hirehive.address.testutil.PersonBuilder;
import hirehive.address.testutil.TypicalPersons;

public class ScheduleCommandTest {
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validApplicantAndDate_success() {
        Person editedPerson = new PersonBuilder(TypicalPersons.ALICE).withDate("31/12/2025").withTag("INTERVIEWEE").build();
        EditCommand.EditPersonDescriptor descriptor =
                new EditPersonDescriptorBuilder(editedPerson).build();
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(TypicalPersons.ALICE.getName().fullName));
        ScheduleCommand scheduleCommand = new ScheduleCommand(nameQuery, descriptor);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_DATE_PERSON_SUCCESS, Messages.format(editedPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Person> personsToAddNote;
        try {
            personsToAddNote = nameQuery.query(expectedModel);
            expectedModel.setPerson(personsToAddNote.get(0), editedPerson);
        } catch (QueryException e) {
            fail();
        }

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIntervieweeAndDate_success() {
        Person editedPerson = new PersonBuilder(TypicalPersons.CARL).withDate("31/12/2025").build();
        EditCommand.EditPersonDescriptor descriptor =
                new EditPersonDescriptorBuilder(editedPerson).build();
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(TypicalPersons.CARL.getName().fullName));
        ScheduleCommand scheduleCommand = new ScheduleCommand(nameQuery, descriptor);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_DATE_PERSON_SUCCESS, Messages.format(editedPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Person> personsToEdit;
        try {
            personsToEdit = nameQuery.query(expectedModel);
            expectedModel.setPerson(personsToEdit.get(0), editedPerson);
        } catch (QueryException e) {
            fail();
        }

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validApplicantWithoutDate_success() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);
        Person editedPerson = new PersonBuilder(TypicalPersons.ALICE)
                .withDate(LocalDate.now().plusDays(1).format(formatter))
                .withTag("INTERVIEWEE").build();
        EditCommand.EditPersonDescriptor descriptor =
                new EditPersonDescriptorBuilder(editedPerson).build();
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(TypicalPersons.ALICE.getName().fullName));
        ScheduleCommand scheduleCommand = new ScheduleCommand(nameQuery);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_DATE_PERSON_SUCCESS, Messages.format(editedPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Person> personsToAddNote;
        try {
            personsToAddNote = nameQuery.query(expectedModel);
            expectedModel.setPerson(personsToAddNote.get(0), editedPerson);
        } catch (QueryException e) {
            fail();
        }

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTag_throwsCommandException() {
        EditCommand.EditPersonDescriptor descriptor =
                new EditPersonDescriptorBuilder().build();
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(TypicalPersons.BENSON.getName().fullName));
        ScheduleCommand scheduleCommand = new ScheduleCommand(nameQuery, descriptor);
        assertThrows(CommandException.class, () -> scheduleCommand.execute(model), String.format(ScheduleCommand.MESSAGE_INVALID_PERSON,
                TypicalPersons.BENSON.getName().fullName, TypicalPersons.BENSON.getTag().getTagName()));
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        String name = "Nonexistent";
        EditCommand.EditPersonDescriptor descriptor =
                new EditPersonDescriptorBuilder().build();
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(name));
        ScheduleCommand scheduleCommand = new ScheduleCommand(nameQuery, descriptor);
        assertThrows(CommandException.class, () -> scheduleCommand.execute(model), Messages.MESSAGE_NO_SUCH_PERSON);
    }

    @Test
    public void equals() {
        NameQuery firstQuery = new NameQuery(new NameContainsKeywordsPredicate("Alice"));
        NameQuery secondQuery = new NameQuery(new NameContainsKeywordsPredicate("Bob"));
        EditCommand.EditPersonDescriptor firstDescriptor = new EditPersonDescriptorBuilder().withDate("01/01/2025").build();
        EditCommand.EditPersonDescriptor secondDescriptor = new EditPersonDescriptorBuilder().withDate("02/01/2025").build();

        ScheduleCommand firstCommand = new ScheduleCommand(firstQuery, firstDescriptor);
        ScheduleCommand secondCommand = new ScheduleCommand(secondQuery, secondDescriptor);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(secondCommand.equals(secondCommand));

        // same values -> returns true
        ScheduleCommand firstCopy = new ScheduleCommand(firstQuery, firstDescriptor);
        ScheduleCommand secondCopy = new ScheduleCommand(secondQuery, secondDescriptor);
        assertTrue(firstCommand.equals(firstCopy));
        assertTrue(secondCommand.equals(secondCopy));

        // different type -> returns false
        assertFalse(firstCommand.equals(0));
        assertFalse(secondCommand.equals(true));

        // null object -> returns false
        assertFalse(firstCommand.equals(null));
        assertFalse(secondCommand.equals(null));

        // different values -> returns false
        assertFalse(firstCommand.equals(secondCommand));
        ScheduleCommand firstSecond = new ScheduleCommand(firstQuery, secondDescriptor);
        ScheduleCommand secondFirst = new ScheduleCommand(secondQuery, firstDescriptor);
        assertFalse(firstCommand.equals(firstSecond));
        assertFalse(firstCommand.equals(secondFirst));
        assertFalse(secondCommand.equals(firstSecond));
        assertFalse(secondCommand.equals(secondFirst));
    }
}
