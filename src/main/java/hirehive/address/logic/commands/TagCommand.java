package hirehive.address.logic.commands;

import static hirehive.address.logic.Messages.MESSAGE_MULTIPLE_PEOPLE_QUERIED;
import static hirehive.address.logic.commands.EditCommand.createEditedPerson;
import static hirehive.address.logic.commands.EditCommand.createOffsetTagPerson;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.List;

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

    // PLEASE CHANGE IT WHEN YOU MERGE THE PR
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the person identified by the given name.\n"
            + "Parameters: " + PREFIX_NAME + "NAME "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "John " + PREFIX_TAG + "Applicant";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged Person: %1$s";

    private NameQuery query = null;
    private EditPersonDescriptor editPersonDescriptor = null;
    private int offset = 0;


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

    /**
     * @param query to query for the specific person by name
     * @param offset of the hiring stage tag from the person's tag
     */
    public TagCommand(NameQuery query, int offset) {
        requireNonNull(query);

        this.query = query;
        this.offset = offset;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (isNull(editPersonDescriptor)) {
            List<Person> personToTag;
            try {
                personToTag = query.query(model);
            } catch (QueryException qe) {
                throw new CommandException(qe.getMessage());
            }

            if (personToTag.size() > 1) {
                throw new CommandException(MESSAGE_MULTIPLE_PEOPLE_QUERIED);
            }

            Person taggedPerson = createOffsetTagPerson(personToTag.get(0), offset);

            model.setPerson(personToTag.get(0), taggedPerson);
            return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, Messages.format(taggedPerson)));
        }

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
