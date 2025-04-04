package hirehive.address.logic.commands;

import hirehive.address.logic.parser.CliSyntax;
import hirehive.address.model.Model;
import hirehive.address.model.person.PersonDoesNotContainTagPredicate;

/**
 * Finds and lists all persons in address book whose tags do not
 * contain any of the argument tags.
 */
public class FilterOutCommand extends Command {
    public static final String COMMAND_WORD = "filterout";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters out all persons with the given tag (case-insensitive) "
            + "and displays the remaining persons as a list.\n"
            + "Parameters: t/TAG\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_TAG + "offered";

    private final PersonDoesNotContainTagPredicate predicate;

    /**
     * Creates FilterOutCommand to filter out applicants with the specified tag
     * @param predicate the predicate to filter the list
     */
    public FilterOutCommand(PersonDoesNotContainTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        return CommandUtil.executeFilter(model, predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterOutCommand)) {
            return false;
        }

        FilterOutCommand otherFilterOutCommand = (FilterOutCommand) other;
        return predicate.equals(otherFilterOutCommand.predicate);
    }
}
