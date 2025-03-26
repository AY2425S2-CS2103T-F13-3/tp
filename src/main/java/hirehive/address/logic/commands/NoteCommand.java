package hirehive.address.logic.commands;

import static java.util.Objects.requireNonNull;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.commands.queries.exceptions.QueryException;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;

/**
 * Finds a Person by name and displays the note in a pop up window.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the note of the person identified by the name used in the displayed person list.\n"
            + "Parameters: NAME\nExample: " + COMMAND_WORD + "n/john doe";

    public static final String MESSAGE_SUCCESS = "Displaying note of: %1$s";

    private final NameQuery query;

    /**
     * Creates NoteCommand to show the note of a person.
     * @param query the name of the person.
     */
    public NoteCommand(NameQuery query) {
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

        Person personToDisplay;
        try {
            personToDisplay = query.query(model);
        } catch (QueryException e) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_PERSON);
        }

        //model.updateNotePersonList();
        model.updatePersonNote(personToDisplay);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToDisplay)), false,
                false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        NoteCommand otherNoteCommand = (NoteCommand) other;
        return query.equals(otherNoteCommand.query);
    }

}
