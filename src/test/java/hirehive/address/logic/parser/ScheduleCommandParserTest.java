package hirehive.address.logic.parser;

import org.junit.jupiter.api.Test;

import hirehive.address.commons.core.index.Index;
import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.EditCommand;
import hirehive.address.logic.commands.ReminderCommand;
import hirehive.address.logic.commands.ScheduleCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.model.person.InterviewDate;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.model.person.Note;
import hirehive.address.testutil.EditPersonDescriptorBuilder;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, " n/Alice id/01/01/1500", ScheduleCommandParser.MESSAGE_DATE_OUT_OF_BOUNDS);
    }

    @Test
    public void parse_validArgs_returnsScheduleCommand() {
        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        editPersonDescriptor.setDate(new InterviewDate("31/12/2999"));
        ScheduleCommand expectedScheduleCommand = new ScheduleCommand(new NameQuery(new NameContainsKeywordsPredicate("Alice")), editPersonDescriptor);
        CommandParserTestUtil.assertParseSuccess(parser, " n/Alice id/31/12/2999", expectedScheduleCommand);

        expectedScheduleCommand = new ScheduleCommand(Index.fromOneBased(1), editPersonDescriptor);
        CommandParserTestUtil.assertParseSuccess(parser, "1 id/31/12/2999", expectedScheduleCommand);

        expectedScheduleCommand = new ScheduleCommand(new NameQuery(new NameContainsKeywordsPredicate("Alice")));
        CommandParserTestUtil.assertParseSuccess(parser, " n/Alice", expectedScheduleCommand);

        expectedScheduleCommand = new ScheduleCommand(Index.fromOneBased(1));
        CommandParserTestUtil.assertParseSuccess(parser, "1", expectedScheduleCommand);
    }
}
