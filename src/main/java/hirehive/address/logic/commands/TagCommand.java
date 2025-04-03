package hirehive.address.logic.commands;

import static hirehive.address.logic.Messages.MESSAGE_MULTIPLE_PEOPLE_QUERIED;
import static hirehive.address.logic.commands.EditCommand.createEditedPerson;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.List;

import hirehive.address.commons.core.index.Index;
import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.EditCommand.EditPersonDescriptor;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.commands.queries.exceptions.QueryException;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;

/**
 * Tags a person identified using their name or by index.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the person identified by the given name or index.\n"
            + "Parameters:\n"
            + " - " + PREFIX_NAME + "NAME "
            + PREFIX_TAG + "TAG\n"
            + " - " + "INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG\n"
            + "Example:\n"
            + " - " + COMMAND_WORD + " " + PREFIX_NAME + "John " + PREFIX_TAG + "Applicant\n"
            + " - " + COMMAND_WORD + " 1 " + PREFIX_TAG + "Applicant\n";

    public static final String MESSAGE_TAG_INVALID_PARAMS = "The given Tag parameters are invalid.";
    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged Person: %1$s";

    private NameQuery query = null;
    private Index index = null;
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

    public TagCommand(Index index, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = editPersonDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!isNull(query)) {
            List<Person> personToTag;
            try {
                personToTag = query.query(model);
            } catch (QueryException qe) {
                throw new CommandException(qe.getMessage());
            }

            if (personToTag.size() > 1) {
                throw new CommandException(MESSAGE_MULTIPLE_PEOPLE_QUERIED);
            }

            Person taggedPerson = createEditedPerson(personToTag.get(0), editPersonDescriptor);

            model.setPerson(personToTag.get(0), taggedPerson);
            return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, Messages.format(taggedPerson)));
        }
        if (!isNull(index)) {
            List<Person> lastShownList = model.getFilteredPersonList();

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToTag = lastShownList.get(index.getZeroBased());
            Person taggedPerson = createEditedPerson(personToTag, editPersonDescriptor);

            model.setPerson(personToTag, taggedPerson);
            return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, Messages.format(taggedPerson)));
        }
        throw new CommandException(MESSAGE_TAG_INVALID_PARAMS);
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
