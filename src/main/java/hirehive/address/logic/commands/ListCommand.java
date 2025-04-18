package hirehive.address.logic.commands;

import static hirehive.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static java.util.Objects.requireNonNull;

import hirehive.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.unfilterPersonList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
