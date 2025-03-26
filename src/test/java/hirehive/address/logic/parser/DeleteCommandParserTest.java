package hirehive.address.logic.parser;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.DeleteCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.testutil.TypicalIndexes;
import hirehive.address.testutil.TypicalPersons;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        DeleteCommand expectedCommand = new DeleteCommand(
                new NameQuery(new NameContainsKeywordsPredicate(Arrays.asList("Alice")))
        );

        CommandParserTestUtil.assertParseSuccess(parser, "delete n/Alice", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
