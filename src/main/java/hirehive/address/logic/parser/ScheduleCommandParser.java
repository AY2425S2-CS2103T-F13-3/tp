package hirehive.address.logic.parser;

import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_DATE;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import hirehive.address.logic.commands.EditCommand;
import hirehive.address.logic.commands.ScheduleCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new DateCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DateCommand
     * and returns a DateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE);

        if (argMultimap.getValue(PREFIX_NAME).orElse("").trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
        String name = argMultimap.getValue(PREFIX_NAME).get();
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(name));

        if (argMultimap.getValue(PREFIX_DATE).orElse("").trim().isEmpty()) {
            return new ScheduleCommand(nameQuery);
        } else {
            EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
            editPersonDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
            return new ScheduleCommand(nameQuery, editPersonDescriptor);
        }
    }
}
