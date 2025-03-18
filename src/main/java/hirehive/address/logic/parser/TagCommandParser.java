package hirehive.address.logic.parser;

import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

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

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        return new TagCommand(nameQuery, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
