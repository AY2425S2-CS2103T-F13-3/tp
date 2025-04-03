package hirehive.address.logic.parser;

import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_DATE;
import static hirehive.address.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import hirehive.address.commons.core.index.Index;
import hirehive.address.logic.commands.EditCommand;
import hirehive.address.logic.commands.ScheduleCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.InterviewDate;
import hirehive.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new DateCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {
    public static final String MESSAGE_DATE_OUT_OF_BOUNDS = "The given date has already passed."
            + "Please provide a valid date.";

    /**
     * Parses the given {@code String} of arguments in the context of the DateCommand
     * and returns a DateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE);
        NameQuery nameQuery = null;
        Index index = null;
        InterviewDate date = null;

        if (argMultimap.getValue(PREFIX_NAME).orElse("").trim().isEmpty()
                && argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getPreamble().trim().isEmpty()) {
            String name = argMultimap.getValue(PREFIX_NAME).get();
            nameQuery = new NameQuery(new NameContainsKeywordsPredicate(name));
        } else {
            index = ParserUtil.parseIndex(argMultimap.getPreamble().trim());
        }
        if (!argMultimap.getValue(PREFIX_DATE).orElse("").trim().isEmpty()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            if (date.getValue().get().isBefore(LocalDate.now())) {
                throw new ParseException(MESSAGE_DATE_OUT_OF_BOUNDS);
            }
        }
        if (date == null) {
            if (index == null) {
                return new ScheduleCommand(nameQuery);
            } else {
                return new ScheduleCommand(index);
            }
        } else {
            EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
            editPersonDescriptor.setDate(date);
            if (index == null) {
                return new ScheduleCommand(nameQuery, editPersonDescriptor);
            } else {
                return new ScheduleCommand(index, editPersonDescriptor);
            }
        }
    }
}
