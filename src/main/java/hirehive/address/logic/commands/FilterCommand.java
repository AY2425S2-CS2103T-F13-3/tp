package hirehive.address.logic.commands;

import static java.util.Objects.requireNonNull;

import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.model.Model;
import hirehive.address.model.tag.Tag;

public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String NOT_IMPLEMENTED_TEXT = "Command not implemented yet";
    public static final String MESSAGE_ARGUMENTS = "Tag: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons with the given tag "
            + "(case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: t/ TAG\n"
            + "Example: " + COMMAND_WORD + " applicant";

    private final Tag tag;

    public FilterCommand(Tag tag) {
        requireNonNull(tag);
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, tag));
    }

}
