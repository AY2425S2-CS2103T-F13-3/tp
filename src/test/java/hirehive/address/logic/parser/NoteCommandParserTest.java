package hirehive.address.logic.parser;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.NoteCommand;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.model.person.NameContainsKeywordsPredicate;

public class NoteCommandParserTest {

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_validArgs_returnsNoteCommand() {
        NoteCommand expectedCommand = new NoteCommand(
                new NameQuery(new NameContainsKeywordsPredicate("Alice"))
        );

        CommandParserTestUtil.assertParseSuccess(parser, "note n/Alice", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "note Alice",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
    }
}
