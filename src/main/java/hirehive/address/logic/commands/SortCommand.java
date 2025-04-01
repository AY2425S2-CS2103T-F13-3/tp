package hirehive.address.logic.commands;

import static java.util.Objects.requireNonNull;
import hirehive.address.model.Model;

/**
 * Sorts the applicants by their interview date
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Applicants have been sorted by their interview date!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortPersons();
        return new CommandResult(MESSAGE_SUCCESS);
    }


}
