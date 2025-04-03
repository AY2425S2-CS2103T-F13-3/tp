package hirehive.address.logic.parser;

import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static java.util.Objects.requireNonNull;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.EditCommand.EditPersonDescriptor;
import hirehive.address.logic.commands.NewNoteCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new NewNoteCommand object
 */
public class NewNoteCommandParser implements Parser<NewNoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NewNoteCommand
     * and returns a NewNoteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NewNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NOTE);
        if (argMultimap.getValue(PREFIX_NAME).orElse("").trim().isEmpty()
                || argMultimap.getValue(PREFIX_NOTE).isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    NewNoteCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_NOTE);

        String name = argMultimap.getValue(PREFIX_NAME).get();
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(name));

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        editPersonDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get().trim()));

        return new NewNoteCommand(nameQuery, editPersonDescriptor);
    }

}
