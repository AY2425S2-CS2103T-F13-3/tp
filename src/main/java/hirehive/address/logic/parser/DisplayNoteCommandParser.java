package hirehive.address.logic.parser;

import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import hirehive.address.logic.commands.DisplayNoteCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input argument and creates a new NoteCommand object.
 */
public class DisplayNoteCommandParser implements Parser<DisplayNoteCommand> {

    /**
     * Parses the given {@code String} argument in the context of the DisplayNoteCommand
     * and returns a DisplayNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DisplayNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (argMultimap.getValue(PREFIX_NAME).orElse("").trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayNoteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        String nameKeyword = argMultimap.getValue(PREFIX_NAME).get();
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(nameKeyword));

        return new DisplayNoteCommand(nameQuery);
    }

}
