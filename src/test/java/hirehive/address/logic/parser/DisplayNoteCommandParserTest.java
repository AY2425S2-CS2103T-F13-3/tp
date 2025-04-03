package hirehive.address.logic.parser;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.DisplayNoteCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.model.person.NameContainsKeywordsPredicate;

public class DisplayNoteCommandParserTest {

    private DisplayNoteCommandParser parser = new DisplayNoteCommandParser();

    @Test
    public void parse_validArgs_returnsNoteCommand() {
        DisplayNoteCommand expectedCommand = new DisplayNoteCommand(
                new NameQuery(new NameContainsKeywordsPredicate("Alice"))
        );

        CommandParserTestUtil.assertParseSuccess(parser, "displaynote n/Alice", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "displaynote Alice",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DisplayNoteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "note n/Alice n/Bob",
                String.format(Messages.getErrorMessageForDuplicatePrefixes(new Prefix("n/"))));
    }
}
