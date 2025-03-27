package hirehive.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import hirehive.address.logic.Messages;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;

public class FilterUtil {
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
