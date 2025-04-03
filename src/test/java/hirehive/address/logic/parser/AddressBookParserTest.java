package hirehive.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.AddCommand;
import hirehive.address.logic.commands.ClearCommand;
import hirehive.address.logic.commands.ScheduleCommand;
import hirehive.address.logic.commands.DeleteCommand;
import hirehive.address.logic.commands.DisplayNoteCommand;
import hirehive.address.logic.commands.EditCommand;
import hirehive.address.logic.commands.ExitCommand;
import hirehive.address.logic.commands.FilterCommand;
import hirehive.address.logic.commands.FindCommand;
import hirehive.address.logic.commands.HelpCommand;
import hirehive.address.logic.commands.ListCommand;
import hirehive.address.logic.commands.NewNoteCommand;
import hirehive.address.logic.commands.ReminderCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.InterviewDate;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.model.person.Note;
import hirehive.address.model.person.Person;
import hirehive.address.model.person.PersonContainsTagPredicate;
import hirehive.address.model.person.UpcomingInterviewPredicate;
import hirehive.address.testutil.Assert;
import hirehive.address.testutil.DefaultPersonBuilder;
import hirehive.address.testutil.DefaultPersonUtil;
import hirehive.address.testutil.EditPersonDescriptorBuilder;
import hirehive.address.testutil.PersonBuilder;
import hirehive.address.testutil.PersonUtil;
import hirehive.address.testutil.TypicalIndexes;
import hirehive.address.testutil.TypicalPersons;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new DefaultPersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(DefaultPersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        String nameToDelete = TypicalPersons.ALICE.getName().fullName;

        DeleteCommand expectedCommand = new DeleteCommand(
                new NameQuery(new NameContainsKeywordsPredicate(nameToDelete))
        );
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + "n/" + nameToDelete
        );

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        String keywords = "foo bar baz";
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        String tag = "Applicant";
        FilterCommand command = (FilterCommand) parser.parseCommand(FilterCommand.COMMAND_WORD + " t/ " + tag);
        assertEquals(new FilterCommand(new PersonContainsTagPredicate(ParserUtil.parseTag(tag))), command);
    }

    @Test
    public void parseCommand_remind() throws Exception {
        String days = "5";
        ReminderCommand command = (ReminderCommand) parser.parseCommand(ReminderCommand.COMMAND_WORD + " " + days);
        assertEquals(new ReminderCommand(new UpcomingInterviewPredicate(5)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_note() throws Exception {
        String nameToDisplay = TypicalPersons.ALICE.getName().fullName;

        DisplayNoteCommand expectedCommand = new DisplayNoteCommand(
                new NameQuery(new NameContainsKeywordsPredicate(nameToDisplay))
        );
        DisplayNoteCommand command = (DisplayNoteCommand) parser.parseCommand(
                DisplayNoteCommand.COMMAND_WORD + " n/" + nameToDisplay
        );

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_newnote() throws Exception {
        String nameToAddNote = TypicalPersons.ALICE.getName().fullName;
        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        editPersonDescriptor.setNote(new Note("test"));

        NewNoteCommand expectedCommand = new NewNoteCommand(
                new NameQuery(new NameContainsKeywordsPredicate(nameToAddNote)),
                editPersonDescriptor
        );
        NewNoteCommand command = (NewNoteCommand) parser.parseCommand(
                NewNoteCommand.COMMAND_WORD + " n/" + nameToAddNote + " i/test"
        );

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        String name = TypicalPersons.ALICE.getName().fullName;
        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        editPersonDescriptor.setDate(new InterviewDate("01/01/2025"));
        ScheduleCommand expectedCommand = new ScheduleCommand(new NameQuery(new NameContainsKeywordsPredicate(name)), editPersonDescriptor);
        ScheduleCommand parsedCommand = (ScheduleCommand) parser.parseCommand(ScheduleCommand.COMMAND_WORD + " "
                + CliSyntax.PREFIX_NAME + name + " " + CliSyntax.PREFIX_DATE + "01/01/2025");
        assertEquals(expectedCommand, parsedCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
