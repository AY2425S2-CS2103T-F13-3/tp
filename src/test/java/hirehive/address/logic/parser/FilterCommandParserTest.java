package hirehive.address.logic.parser;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.FilterCommand;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.PersonContainsTagPredicate;
import hirehive.address.model.tag.Tag;

public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() throws ParseException {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new PersonContainsTagPredicate(ParserUtil.parseTag("Applicant")));
        CommandParserTestUtil.assertParseSuccess(parser, " t/ applicant", expectedFilterCommand);
    }
}
