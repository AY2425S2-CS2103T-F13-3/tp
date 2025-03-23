package hirehive.address.logic.parser;

import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import hirehive.address.commons.exceptions.IllegalValueException;
import hirehive.address.logic.commands.FilterCommand;
import hirehive.address.logic.commands.TagCommand;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.tag.Tag;


public class FilterCommandParser implements Parser<FilterCommand>{

    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        Tag tag;
        if (argMultimap.getValue(PREFIX_TAG).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
        return new FilterCommand(tag);
    }

}
