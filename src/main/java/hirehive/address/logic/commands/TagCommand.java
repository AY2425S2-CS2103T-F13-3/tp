package hirehive.address.logic.commands;

import static hirehive.address.logic.Messages.MESSAGE_MULTIPLE_PEOPLE_QUERIED;
import static hirehive.address.logic.commands.EditCommand.createEditedPerson;
import static hirehive.address.logic.commands.EditCommand.createOffsetTagPerson;
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
            + "Parameters (either 1, 2, 3):\n"
            + " 1. " + PREFIX_NAME + "NAME " + PREFIX_TAG + "TAG\n"
            + " 2. OFFSET " + PREFIX_NAME + "NAME\n" 
            + " 3. INDEX (must be a positive integer) " + PREFIX_TAG + "TAG\n"
            + "Example:\n"
            + " - " + COMMAND_WORD + " " + PREFIX_NAME + "John " + PREFIX_TAG + "Applicant\n"
            + " - " + COMMAND_WORD + " +1 " + PREFIX_NAME + "John\n"
            + " - " + COMMAND_WORD + " 1 " + PREFIX_TAG + "Applicant\n";

    public static final String MESSAGE_TAG_INVALID_PARAMS = "The given Tag parameters are invalid.";
    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged Person: %1$s";

    private NameQuery query = null;
    private EditPersonDescriptor editPersonDescriptor = null;
    private Index index = null;
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
  
    /**
     * @param index of the person in the filtered person list to tag
     * @param editPersonDescriptor details to tag the person with
     */
    public TagCommand(Index index, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = editPersonDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
      
        if (isNull(index)) {
            List<Person> personToTag;
            if (isNull(editPersonDescriptor)) {
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
        if (isNull(query)) {
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
