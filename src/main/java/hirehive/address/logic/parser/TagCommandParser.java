package hirehive.address.logic.parser;

import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import hirehive.address.commons.core.index.Index;
import hirehive.address.logic.commands.EditCommand.EditPersonDescriptor;
import hirehive.address.logic.commands.TagCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_TAG);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_TAG).isEmpty()) {
            if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
            }
            String nameKeywords = argMultimap.getValue(PREFIX_NAME).get();
            NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(nameKeywords));
            try {
                int offset = Integer.parseInt(argMultimap.getPreamble());
                return new TagCommand(nameQuery, offset);
            } catch (NumberFormatException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
            }
        }

        editPersonDescriptor.setTag(ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get()));

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
                return new TagCommand(index, editPersonDescriptor);
            } catch (ParseException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
            }
        }

        String nameKeywords = argMultimap.getValue(PREFIX_NAME).get();
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(nameKeywords));

        return new TagCommand(nameQuery, editPersonDescriptor);
    }
}
