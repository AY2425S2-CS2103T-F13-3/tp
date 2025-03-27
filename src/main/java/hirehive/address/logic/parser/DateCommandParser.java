package hirehive.address.logic.parser;

import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_DATE;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import hirehive.address.logic.commands.DateCommand;
import hirehive.address.logic.commands.EditCommand;
import hirehive.address.logic.commands.TagCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DateCommand object
 */
public class DateCommandParser implements Parser<DateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DateCommand
     * and returns a DateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DateCommand parse(String args) throws ParseException {
        /*
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_DATE);

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
         */
        return new DateCommand();
    }
}
