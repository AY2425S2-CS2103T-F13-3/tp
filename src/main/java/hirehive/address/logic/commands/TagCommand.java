package hirehive.address.logic.commands;

import hirehive.address.commons.core.index.Index;
import hirehive.address.logic.parser.CliSyntax;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class TagCommand extends EditCommand {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + CliSyntax.PREFIX_TAG + "TAG"
            + "Example: " + COMMAND_WORD + " 1 " + CliSyntax.PREFIX_TAG + "example";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged Person: %1$s";

    public TagCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        super(index, editPersonDescriptor);
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
        return getIndex().equals(otherTagCommand.getIndex())
                && getEditPersonDescriptor().equals(otherTagCommand.getEditPersonDescriptor());
    }
}
