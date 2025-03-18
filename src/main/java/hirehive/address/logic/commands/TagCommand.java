package hirehive.address.logic.commands;

import static hirehive.address.logic.commands.EditCommand.createEditedPerson;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static hirehive.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static java.util.Objects.requireNonNull;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.EditCommand.EditPersonDescriptor;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.commands.queries.exceptions.QueryException;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;

/**
 * Tags a person identified using their name.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the person identified by the index number used in the displayed person list.\n"
            + "Parameters: " + PREFIX_NAME + "NAME "
            + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "John " + PREFIX_TAG + "example";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged Person: %1$s";

    private final NameQuery query;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param query to query for the specific person by name
     * @param editPersonDescriptor details to tag the person with
     */
    public TagCommand(NameQuery query, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(query);
        requireNonNull(editPersonDescriptor);

        this.query = query;
        this.editPersonDescriptor = editPersonDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToTag;
        try {
            personToTag = query.query(model);
        } catch (QueryException qe) {
            throw new CommandException(qe.getMessage());
        }

        Person taggedPerson = createEditedPerson(personToTag, editPersonDescriptor);

        model.setPerson(personToTag, taggedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, Messages.format(taggedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherTagCommand = (TagCommand) other;
        return query.equals(otherTagCommand.query)
                && editPersonDescriptor.equals(otherTagCommand.editPersonDescriptor);
    }
}
