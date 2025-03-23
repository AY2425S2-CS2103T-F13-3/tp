package hirehive.address.logic.parser;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.FilterCommand;
import hirehive.address.model.person.PersonContainsTagPredicate;
import hirehive.address.model.tag.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new PersonContainsTagPredicate(new Tag("applicant")));
        CommandParserTestUtil.assertParseSuccess(parser, " t/ applicant", expectedFilterCommand);
    }
}
