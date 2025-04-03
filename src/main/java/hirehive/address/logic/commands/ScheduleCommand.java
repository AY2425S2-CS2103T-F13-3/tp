package hirehive.address.logic.commands;

import static hirehive.address.logic.commands.EditCommand.createEditedPerson;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_DATE;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import hirehive.address.commons.core.index.Index;
import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.model.Model;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.model.person.Person;
import hirehive.address.model.tag.Tag;

/**
 * Adds or edits the interview date of a Person
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds or edits the interview date for the person identified by the name used in\n"
            + "the displayed person list.\n"
            + "If no date is specified, the next available date starting from the next day will be used instead.\n"
            + "Parameters (either 1 or 2):\n"
            + " 1. " + PREFIX_NAME + "NAME [" + PREFIX_DATE + "DATE]\n"
            + " 2. INDEX [" + PREFIX_DATE + "NAME]\n"
            + "Example:\n - " + COMMAND_WORD + " " + PREFIX_NAME + "John " + PREFIX_DATE + "01/05/2025\n"
            + " - " + COMMAND_WORD + " 1 " + PREFIX_DATE + "01/05/2025";

    public static final String MESSAGE_DATE_PERSON_SUCCESS = "Added interview date: %1$s";
    public static final String MESSAGE_INVALID_PERSON = "%1$s has invalid tag: %2$s.\n"
            + "You can only schedule interviews with people who are Applicants, Candidates or Interviewees.";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET =
            "Date command not implemented yet";

    private final NameQuery query;
    private final EditCommand.EditPersonDescriptor editPersonDescriptor;
    private final Index index;

    private final boolean bDateProvided;

    /**
     * Creates a {@code ScheduleCommand} object with a provided date.
     */
    public ScheduleCommand(NameQuery query, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(query);
        requireNonNull(editPersonDescriptor);
        this.query = query;
        this.editPersonDescriptor = editPersonDescriptor;
        this.index = null;
        bDateProvided = true;
    }

    /**
     * Creates a {@code ScheduleCommand} object without a provided date.
     */
    public ScheduleCommand(NameQuery query) {
        requireNonNull(query);
        this.query = query;
        this.editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        this.index = null;
        bDateProvided = false;
    }

    /**
     * Creates a {@code ScheduleCommand} object with a provided date.
     */
    public ScheduleCommand(Index index, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);
        this.index = index;
        this.query = null;
        this.editPersonDescriptor = editPersonDescriptor;
        bDateProvided = true;
    }

    /**
     * Creates a {@code ScheduleCommand} object without a provided date.
     */
    public ScheduleCommand(Index index) {
        requireNonNull(index);
        this.index = index;
        this.query = null;
        this.editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        bDateProvided = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!bDateProvided) {
            editPersonDescriptor.setDate(model.getAvailableDate());
        }

        Person personToAddDate;
        if (index == null) {
            personToAddDate = CommandUtil.querySearch(model, query);
        } else {
            personToAddDate = CommandUtil.indexSearch(model, index);
        }

        if (personToAddDate.getTag().equals(Tag.APPLICANT) || personToAddDate.getTag().equals(Tag.CANDIDATE)) {
            editPersonDescriptor.setTag(Tag.INTERVIEWEE);
        } else if (personToAddDate.getTag().equals(Tag.OFFERED) || personToAddDate.getTag().equals(Tag.REJECTED)) {
            throw new CommandException(String.format(MESSAGE_INVALID_PERSON, personToAddDate.getName().toString(),
                    personToAddDate.getTag().getTagName()));
        }
        Person editedPerson = createEditedPerson(personToAddDate, editPersonDescriptor);
        model.setPerson(personToAddDate, editedPerson);
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(editedPerson.getName().fullName));
        return new CommandResult(String.format(MESSAGE_DATE_PERSON_SUCCESS, Messages.format(editedPerson)), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCommand)) {
            return false;
        }

        ScheduleCommand otherCommand = (ScheduleCommand) other;
        if (!this.editPersonDescriptor.equals(otherCommand.editPersonDescriptor)) {
            return false;
        }
        if (this.query == null) {
            return this.index.equals(otherCommand.index) && otherCommand.query == null;
        }
        if (this.index == null) {
            return this.query.equals(otherCommand.query) && otherCommand.index == null;
        }
        return false;
    }
}
