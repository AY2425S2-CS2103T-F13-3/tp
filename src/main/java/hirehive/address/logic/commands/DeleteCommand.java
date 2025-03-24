package hirehive.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.commands.queries.exceptions.QueryException;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;


/**
 * Deletes a person identified using its displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the name used in the displayed person list.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " n/john doe";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final NameQuery query;

    /**
     * Creates DeleteCommand to remove a person at the specified index
     * @param query the name of the person to be deleted
     */
    public DeleteCommand(NameQuery query) {
        requireNonNull(query);
        this.query = query;
    }

    /**
     * Executes the delete command to remove a contact from the address book
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} containing success message
     * @throws CommandException if provided index is out of bounds
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToDelete;
        try {
            personToDelete = query.query(model);
        } catch (QueryException qe) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_PERSON);
        }

        model.deletePerson(personToDelete);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return query.equals(otherDeleteCommand.query);
    }

}
