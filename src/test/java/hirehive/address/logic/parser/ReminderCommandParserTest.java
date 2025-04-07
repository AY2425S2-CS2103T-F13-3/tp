package hirehive.address.logic.parser;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.FilterCommand;
import hirehive.address.logic.commands.ReminderCommand;
import hirehive.address.model.person.PersonContainsTagPredicate;
import hirehive.address.model.person.UpcomingInterviewPredicate;
import hirehive.address.model.tag.Tag;

public class ReminderCommandParserTest {
    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        CommandParserTestUtil.assertParseFailure(parser, "100000000000000000000000000000000", ParserUtil.MESSAGE_OUT_OF_RANGE);
    }

    @Test
    public void parse_validArgs_returnsReminderCommand() {
        ReminderCommand expectedReminderCommand =
                new ReminderCommand(new UpcomingInterviewPredicate(10));
        CommandParserTestUtil.assertParseSuccess(parser, " 10", expectedReminderCommand);
    }
}
