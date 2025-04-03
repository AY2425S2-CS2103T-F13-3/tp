package hirehive.address.logic.commands;

import static hirehive.address.logic.Messages.MESSAGE_MULTIPLE_PEOPLE_QUERIED;
import static hirehive.address.logic.commands.EditCommand.createEditedPerson;
import static java.util.Objects.requireNonNull;

import java.util.List;

import hirehive.address.commons.core.index.Index;
import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.commands.queries.exceptions.QueryException;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;
import hirehive.address.model.person.PersonPredicate;

/**
 * Helper functions for handling command execution of similar types
 */
public class CommandUtil {

    /**
     * Updates the displayed list based on the given {@code predicate} and returns a CommandResult indicating
     * the results of the filter.
     * @param model The model to update the list in
     * @param predicate The given predicate to filter the list with
     * @return A CommandResult object
     */
    public static CommandResult executeFilter(Model model, PersonPredicate predicate) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        String message;
        if (!model.getFilteredPersonList().isEmpty()) {
            message = predicate.getSuccessString();
        } else {
            message = Messages.MESSAGE_NO_SUCH_PERSON;
        }
        return new CommandResult(message);
    }

    /**
     * Uses the given query to find the matching person in the model.
     * @param model The model to update the list in
     * @param query The {@code NameQuery} object used to search for the person
     * @return A {@code Person} object
     * @throws CommandException
     */
    public static Person querySearch(Model model, NameQuery query) throws CommandException {
        requireNonNull(model);
        List<Person> personToEdit;
        try {
            personToEdit = query.query(model);
        } catch (QueryException qe) {
            throw new CommandException(qe.getMessage());
        }
        if (personToEdit.size() > 1) {
            throw new CommandException(MESSAGE_MULTIPLE_PEOPLE_QUERIED);
        }
        return personToEdit.get(0);
    }
    /**
     * Uses the given index to find the matching person in the model.
     * @param model The model to update the list in
     * @param index The {@code index} object used to search for the person
     * @return A {@code Person} object
     * @throws CommandException
     */
    public static Person indexSearch(Model model, Index index) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(index.getZeroBased());
    }
}
