package hirehive.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import hirehive.address.commons.core.index.Index;
import hirehive.address.commons.util.ToStringBuilder;
import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;


/**
 * Deletes a person identified using its displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    /**
     * Creates DeleteCommand to remove a person at the specified index
     * @param targetIndex the index of the person to be deleted
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
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
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(lastShownList.size() == 1
                    ? Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                    : String.format(Messages.MESSAGE_INDEX_OUT_OF_BOUNDS, lastShownList.size()));
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
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
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
