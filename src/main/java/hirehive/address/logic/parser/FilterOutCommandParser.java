package hirehive.address.logic.parser;

import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import hirehive.address.logic.commands.FilterOutCommand;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.PersonDoesNotContainTagPredicate;
import hirehive.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterOutCommand object
 */
public class FilterOutCommandParser implements Parser<FilterOutCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FilterOutCommand
     * and returns a FilterOutCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterOutCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Tag tag;
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TAG);
        if (argMultimap.getValue(PREFIX_NAME).map(String::trim).filter(String::isEmpty).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterOutCommand.MESSAGE_USAGE));
        }

        tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).orElseThrow(() ->
                new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterOutCommand.MESSAGE_USAGE))));
        return new FilterOutCommand(new PersonDoesNotContainTagPredicate(tag));
    }
}
