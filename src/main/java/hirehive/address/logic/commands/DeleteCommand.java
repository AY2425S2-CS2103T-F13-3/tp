package hirehive.address.logic.commands;

import static hirehive.address.logic.Messages.MESSAGE_DATA_SAVED;
import static hirehive.address.logic.Messages.MESSAGE_MULTIPLE_PEOPLE_QUERIED;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import hirehive.address.commons.core.index.Index;
import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.commands.queries.exceptions.QueryException;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;


/**
 * Deletes a person identified using its displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the given name or index.\n"
            + "Parameters (either 1 or 2): \n"
            + " 1. " + PREFIX_NAME + "NAME\n"
            + " 2. INDEX (must be a positive integer)\n"
            + "Example:\n"
            + " - " + COMMAND_WORD + " " + PREFIX_NAME + "John Doe\n"
            + " - " + COMMAND_WORD + " " + "1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private NameQuery query = null;

    private Index index = null;

    /**
     * Creates DeleteCommand to remove a person with specified name
     * @param query the name of the person to be deleted
     */
    public DeleteCommand(NameQuery query) {
        requireNonNull(query);
        this.query = query;
    }

    /**
     * Creates DeleteCommand to remove a person at the specified index
     * @param index the specified index of the Person
     */
    public DeleteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
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
        if (!isNull(query)) {
            personToDelete = CommandUtil.querySearch(model, query);
        } else if (!isNull(index)) {
            personToDelete = CommandUtil.indexSearch(model, index);
        } else {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        model.deletePerson(personToDelete);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)), true);
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
        return Objects.equals(this.query, otherDeleteCommand.query);
    }

}
