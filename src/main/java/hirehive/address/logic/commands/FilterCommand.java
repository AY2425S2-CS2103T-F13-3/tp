package hirehive.address.logic.commands;

import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.model.Model;

public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String NOT_IMPLEMENTED_TEXT = "Command not implemented yet";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(NOT_IMPLEMENTED_TEXT);
    }
}
