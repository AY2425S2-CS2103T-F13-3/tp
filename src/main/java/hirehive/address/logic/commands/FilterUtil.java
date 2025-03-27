package hirehive.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import hirehive.address.logic.Messages;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;

/**
 * Helper functions for handling filtering commands
 */
public class FilterUtil {

    /**
     * Updates the displayed list based on the given {@code predicate} and returns a CommandResult indicating
     * the results of the filter.
     * @param model The model to update the list in
     * @param predicate The given predicate to filter the list with
     * @return A CommandResult object
     */
    public static CommandResult executeFilter(Model model, Predicate<Person> predicate) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        String message;
        if (!model.getFilteredPersonList().isEmpty()) {
            message = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size());
        } else {
            message = Messages.MESSAGE_NO_SUCH_PERSON;
        }
        return new CommandResult(message);
    }
}
