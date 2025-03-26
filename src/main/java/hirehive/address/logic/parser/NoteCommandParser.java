package hirehive.address.logic.parser;

import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import hirehive.address.logic.commands.DeleteCommand;
import hirehive.address.logic.commands.NoteCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input argument and creates a new NoteCommand object.
 */
public class NoteCommandParser implements Parser<NoteCommand> {

    /**
     * Parses the given {@code String} argument in the context of the NoteCommand
     * and returns a NoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        // String nameKeyword = argMultimap.getValue(PREFIX_NAME).get();
        // NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(nameKeyword));

        return new NoteCommand(nameQuery);
    }

}
