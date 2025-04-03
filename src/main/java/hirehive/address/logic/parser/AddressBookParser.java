package hirehive.address.logic.parser;

import static hirehive.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirehive.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hirehive.address.commons.core.LogsCenter;
import hirehive.address.logic.commands.AddCommand;
import hirehive.address.logic.commands.ClearCommand;
import hirehive.address.logic.commands.Command;
import hirehive.address.logic.commands.DateCommand;
import hirehive.address.logic.commands.DeleteCommand;
import hirehive.address.logic.commands.EditCommand;
import hirehive.address.logic.commands.ExitCommand;
import hirehive.address.logic.commands.FilterCommand;
import hirehive.address.logic.commands.FindCommand;
import hirehive.address.logic.commands.HelpCommand;
import hirehive.address.logic.commands.ListCommand;
import hirehive.address.logic.commands.NewNoteCommand;
import hirehive.address.logic.commands.NoteCommand;
import hirehive.address.logic.commands.ReminderCommand;
import hirehive.address.logic.commands.SortCommand;
import hirehive.address.logic.commands.TagCommand;
import hirehive.address.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case DateCommand.COMMAND_WORD:
            return new DateCommand();

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommand();

        case NoteCommand.COMMAND_WORD:
            return new NoteCommandParser().parse(arguments);

        case NewNoteCommand.COMMAND_WORD:
            return new NewNoteCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
