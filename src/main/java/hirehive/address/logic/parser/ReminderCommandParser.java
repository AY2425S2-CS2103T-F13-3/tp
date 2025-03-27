package hirehive.address.logic.parser;

import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import hirehive.address.logic.commands.EditCommand;
import hirehive.address.logic.commands.FilterCommand;
import hirehive.address.logic.commands.ReminderCommand;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.PersonContainsTagPredicate;
import hirehive.address.model.person.UpcomingInterviewPredicate;
import hirehive.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new ReminderCommand object
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReminderCommand
     * and returns a ReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        int days;
        try {
            days = ParserUtil.parseDays(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE), pe);
        }
        return new ReminderCommand(new UpcomingInterviewPredicate(days));
    }
}
