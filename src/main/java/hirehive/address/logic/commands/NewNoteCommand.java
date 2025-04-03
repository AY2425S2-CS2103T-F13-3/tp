package hirehive.address.logic.commands;

import static hirehive.address.logic.Messages.MESSAGE_DATA_SAVED;
import static hirehive.address.logic.commands.EditCommand.createEditedPerson;
import static java.util.Objects.requireNonNull;

import java.util.List;

import hirehive.address.commons.util.ToStringBuilder;
import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.EditCommand.EditPersonDescriptor;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.commands.queries.exceptions.QueryException;
import hirehive.address.model.Model;
import hirehive.address.model.person.Name;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.model.person.Person;

/**
 * Finds a Person by name, then creates and displays the note in a pop up window.
 */
public class NewNoteCommand extends Command {

    public static final String COMMAND_WORD = "newnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a note for the person identified by the name used in the displayed person list. \n"
            + "Parameters: n/NAME i/NOTE\nExample: " + COMMAND_WORD + " n/john doe i/Good at expressing himself";

    public static final String MESSAGE_SUCCESS = "Added and displaying note of: %1$s";

    private final String name;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * Creates NewnoteCommand to create and show the note of a person.
     * @param name the name of the person.
     */
    public NewNoteCommand(String name, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(name);
        requireNonNull(editPersonDescriptor);

        this.name = name;
        this.editPersonDescriptor = editPersonDescriptor;
    }

    /**
     * Executes the newnote command to add and display the note of the queried person
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} containing success message.
     * @throws CommandException if person is not found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        NameQuery query = new NameQuery(new NameContainsKeywordsPredicate(name));
        List<Person> personToAddNote;
        try {
            personToAddNote = query.query(model);
        } catch (QueryException e) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_PERSON);
        }

        Person personAddedNote = null;
        if (personToAddNote.size() > 1) {
            Name givenName = new Name(name);
            for (Person person : personToAddNote) {
                if (person.getName().equals(givenName)) {
                    personAddedNote = person;
                    break;
                }
            }
            if (personAddedNote == null) {
                throw new CommandException(Messages.MESSAGE_MULTIPLE_PEOPLE_QUERIED_NAME);
            }
        } else {
            personAddedNote = personToAddNote.get(0);
        }

        Person editedPerson = createEditedPerson(personAddedNote, editPersonDescriptor);

        model.setPerson(personAddedNote, editedPerson);
        model.updatePersonNote(editedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedPerson)),
                false, false, true, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NewNoteCommand)) {
            return false;
        }

        NewNoteCommand otherNewNoteCommand = (NewNoteCommand) other;
        return name.equals(otherNewNoteCommand.name)
                && editPersonDescriptor.equals(otherNewNoteCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }
}
