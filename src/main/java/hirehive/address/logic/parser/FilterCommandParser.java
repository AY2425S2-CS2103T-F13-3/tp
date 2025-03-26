package hirehive.address.logic.parser;

import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import hirehive.address.logic.commands.FilterCommand;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.PersonContainsTagPredicate;
import hirehive.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        Tag tag;
        if (argMultimap.getValue(PREFIX_TAG).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
        return new FilterCommand(new PersonContainsTagPredicate(tag));
    }

}
