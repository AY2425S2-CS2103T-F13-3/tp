package hirehive.address.logic.commands;

import static java.util.Objects.requireNonNull;

import hirehive.address.commons.util.ToStringBuilder;
import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.model.Model;
import hirehive.address.model.person.UpcomingInterviewPredicate;

public class ReminderCommand extends Command {
    public static final String COMMAND_WORD = "remind";
    public static final String NOT_IMPLEMENTED_TEXT = "Command not implemented yet";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons with an upcoming interview"
            + "within the given amount of days and displays them as a list with index numbers.\n"
            + "Parameters: DAYS\n"
            + "Example: " + COMMAND_WORD + " 3";

    private final UpcomingInterviewPredicate predicate;
    public ReminderCommand(UpcomingInterviewPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return FilterUtil.executeFilter(model, predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCommand)) {
            return false;
        }

        ReminderCommand otherReminderCommand = (ReminderCommand) other;
        return predicate.equals(otherReminderCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
