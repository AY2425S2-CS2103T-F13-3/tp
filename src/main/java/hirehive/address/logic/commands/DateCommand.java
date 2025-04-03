package hirehive.address.logic.commands;

import static hirehive.address.logic.commands.EditCommand.createEditedPerson;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;

/**
 * Adds or edits the interview date of a Person
 */
public class DateCommand extends Command {
    public static final String COMMAND_WORD = "date";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds or edits the interview date for the person identified by the name used in "
            + "the displayed person list.\n"
            + "Parameters: " + PREFIX_NAME + "NAME "
            + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "John " + PREFIX_TAG + "example";

    public static final String MESSAGE_DATE_PERSON_SUCCESS = "Added date: %1$s";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET =
            "Date command not implemented yet";

    private final NameQuery query;
    private final EditCommand.EditPersonDescriptor editPersonDescriptor;

    public DateCommand(NameQuery query, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(query);
        requireNonNull(editPersonDescriptor);
        this.query = query;
        this.editPersonDescriptor = editPersonDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToAddDate = CommandUtil.querySearch(model, query);
        Person editedPerson = createEditedPerson(personToAddDate, editPersonDescriptor);

        model.setPerson(personToAddDate, editedPerson);
        return new CommandResult(String.format(MESSAGE_DATE_PERSON_SUCCESS, Messages.format(editedPerson)));
    }
}
