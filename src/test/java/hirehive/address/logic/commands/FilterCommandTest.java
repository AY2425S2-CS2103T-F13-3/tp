package hirehive.address.logic.commands;

import hirehive.address.logic.Messages;
import hirehive.address.model.Model;
import hirehive.address.model.ModelManager;
import hirehive.address.model.UserPrefs;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.model.person.PersonContainsTagPredicate;
import hirehive.address.model.tag.Tag;
import hirehive.address.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static hirehive.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsTagPredicate firstPredicate =
                new PersonContainsTagPredicate(new Tag("first"));
        PersonContainsTagPredicate secondPredicate =
                new PersonContainsTagPredicate(new Tag("second"));

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
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsTagPredicate predicate = preparePredicate("test");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonContainsTagPredicate predicate = preparePredicate("applicant");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalPersons.ALICE, TypicalPersons.CARL,
                TypicalPersons.GEORGE), model.getFilteredPersonList());
    }

    private PersonContainsTagPredicate preparePredicate(String userInput) {
        return new PersonContainsTagPredicate(new Tag(userInput));
    }
}
