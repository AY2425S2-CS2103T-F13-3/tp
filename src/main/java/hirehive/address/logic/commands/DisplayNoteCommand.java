package hirehive.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import hirehive.address.commons.util.ToStringBuilder;
import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.commands.queries.exceptions.QueryException;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;

/**
 * Finds a Person by name and displays the note in a popup window.
 */
public class DisplayNoteCommand extends Command {

    public static final String COMMAND_WORD = "displaynote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the note of the person identified by the name used in the displayed person list.\n"
            + "Parameters: n/NAME\nExample: " + COMMAND_WORD + " n/john doe";

    public static final String MESSAGE_SUCCESS = "Displaying note of: %1$s";

    private final NameQuery query;

    /**
     * Creates NoteCommand to show the note of a person.
     * @param query the name of the person.
     */
    public DisplayNoteCommand(NameQuery query) {
        requireNonNull(query);
        this.query = query;
    }

    /**
     * Executes the note command to display the note of the queried person
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} containing success message.
     * @throws CommandException if person is not found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> personToDisplay;
        try {
            personToDisplay = query.query(model);
        } catch (QueryException e) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_PERSON);
        }
        if (personToDisplay.size() > 1) {
            throw new CommandException(Messages.MESSAGE_MULTIPLE_PEOPLE_QUERIED);
        }

        Person personDisplayed = personToDisplay.get(0);
        model.updatePersonNote(personDisplayed);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personDisplayed)), false,
                false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayNoteCommand)) {
            return false;
        }

        DisplayNoteCommand otherNoteCommand = (DisplayNoteCommand) other;
        return query.equals(otherNoteCommand.query);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("query", query)
                .toString();
    }
}
