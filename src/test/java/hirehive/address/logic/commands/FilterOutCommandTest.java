package hirehive.address.logic.commands;

import static hirehive.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.text.html.HTML;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.parser.ParserUtil;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.Model;
import hirehive.address.model.ModelManager;
import hirehive.address.model.UserPrefs;
import hirehive.address.model.person.PersonDoesNotContainTagPredicate;
import hirehive.address.model.tag.Tag;
import hirehive.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code FilterOutCommand}.
 */
public class FilterOutCommandTest {
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_wrongInput_throwsParseException() {
        assertThrows(ParseException.class, () -> preparePredicate("user"));
    }

    @Test
    public void execute_validTagInput_success() throws ParseException {
        PersonDoesNotContainTagPredicate filterOutPredicate = preparePredicate("applicant");
        FilterOutCommand filterOutCommand = new FilterOutCommand(filterOutPredicate);
        String expectedMessage = String.format(Messages.MESSAGE_FILTEROUT_OVERVIEW_TAG, "APPLICANT");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(filterOutPredicate);

        assertCommandSuccess(filterOutCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        PersonDoesNotContainTagPredicate firstPredicate =
                new PersonDoesNotContainTagPredicate(Tag.APPLICANT);
        PersonDoesNotContainTagPredicate secondPredicate =
                new PersonDoesNotContainTagPredicate(Tag.CANDIDATE);

        FilterOutCommand filteroutFirstCommand = new FilterOutCommand(firstPredicate);
        FilterOutCommand filteroutSecondCommand = new FilterOutCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filteroutFirstCommand.equals(filteroutFirstCommand));

        // same values -> returns true
        FilterOutCommand findFirstCommandCopy = new FilterOutCommand(firstPredicate);
        assertTrue(filteroutFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(filteroutFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filteroutFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filteroutFirstCommand.equals(filteroutSecondCommand));
    }

    private PersonDoesNotContainTagPredicate preparePredicate(String userInput) throws ParseException {
        return new PersonDoesNotContainTagPredicate(ParserUtil.parseTag(userInput));
    }
}
