package hirehive.address.logic.parser;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.FilterOutCommand;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.person.PersonDoesNotContainTagPredicate;

public class FilterOutCommandParserTest {
    private FilterOutCommandParser parser = new FilterOutCommandParser();

    @Test
    public void parse_validArgs_returnsFilterOutCommand() throws ParseException {
        PersonDoesNotContainTagPredicate predicate =
                new PersonDoesNotContainTagPredicate(ParserUtil.parseTag("Candidate"));
        FilterOutCommand expectedCommand = new FilterOutCommand(predicate);
        CommandParserTestUtil.assertParseSuccess(parser, "filterout t/candidate", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FilterOutCommand.MESSAGE_USAGE));
    }
}
