package hirehive.address.logic.commands;

import static hirehive.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadPoolExecutor;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.model.Model;
import hirehive.address.model.ModelManager;
import hirehive.address.model.UserPrefs;
import hirehive.address.model.person.PersonContainsTagPredicate;
import hirehive.address.model.person.UpcomingInterviewPredicate;
import hirehive.address.model.tag.Tag;
import hirehive.address.testutil.TypicalPersons;

public class ReminderCommandTest {
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
    public void execute_zeroDays_noPersonFound() {
        String expectedMessage = Messages.MESSAGE_NO_SUCH_PERSON;
        UpcomingInterviewPredicate predicate = preparePredicate(0, "01/01/2025");
        ReminderCommand command = new ReminderCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroDays_onePersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        UpcomingInterviewPredicate predicate = preparePredicate(0, "01/02/2025");
        ReminderCommand command = new ReminderCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalPersons.BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroDays_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        UpcomingInterviewPredicate predicate = preparePredicate(0, "01/03/2025");
        ReminderCommand command = new ReminderCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalPersons.ELLE, TypicalPersons.FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_nonZeroDays_noPersonsFound() {
        String expectedMessage = Messages.MESSAGE_NO_SUCH_PERSON;
        UpcomingInterviewPredicate predicate = preparePredicate(10, "01/01/2024");
        ReminderCommand command = new ReminderCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_nonZeroDays_onePersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        UpcomingInterviewPredicate predicate = preparePredicate(10, "22/01/2025");
        ReminderCommand command = new ReminderCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalPersons.BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_nonZeroDays_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        UpcomingInterviewPredicate predicate = preparePredicate(10, "21/02/2025");
        ReminderCommand command = new ReminderCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalPersons.ELLE, TypicalPersons.FIONA), model.getFilteredPersonList());
    }

    private UpcomingInterviewPredicate preparePredicate(int userInput, String currDay) {
        return new UpcomingInterviewPredicate(userInput, currDay);
    }
}
