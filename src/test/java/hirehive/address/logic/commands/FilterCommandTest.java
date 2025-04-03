package hirehive.address.logic.commands;

import static hirehive.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hirehive.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.parser.ParserUtil;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.Model;
import hirehive.address.model.ModelManager;
import hirehive.address.model.UserPrefs;
import hirehive.address.model.person.PersonContainsTagPredicate;
import hirehive.address.model.tag.Tag;
import hirehive.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsTagPredicate firstPredicate =
                new PersonContainsTagPredicate(Tag.APPLICANT);
        PersonContainsTagPredicate secondPredicate =
                new PersonContainsTagPredicate(Tag.CANDIDATE);

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand findFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_throwsParseException() {
        assertThrows(ParseException.class, () -> preparePredicate("test"));
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws ParseException {
        String expectedMessage = String.format(Messages.MESSAGE_FILTER_OVERVIEW_TAG, "REJECTED");
        PersonContainsTagPredicate predicate = preparePredicate("Rejected");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalPersons.BENSON, TypicalPersons.ELLE,
                TypicalPersons.FIONA), model.getFilteredPersonList());
    }

    private PersonContainsTagPredicate preparePredicate(String userInput) throws ParseException {
        return new PersonContainsTagPredicate(ParserUtil.parseTag(userInput));
    }
}
