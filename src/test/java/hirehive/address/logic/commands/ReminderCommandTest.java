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
        UpcomingInterviewPredicate firstPredicate =
                new UpcomingInterviewPredicate(5, "01/01/2025");
        UpcomingInterviewPredicate secondPredicate =
                new UpcomingInterviewPredicate(10, "01/01/2025");

        ReminderCommand reminderFirstCommand = new ReminderCommand(firstPredicate);
        ReminderCommand reminderSecondCommand = new ReminderCommand(secondPredicate);

        // same object -> returns true
        assertTrue(reminderFirstCommand.equals(reminderFirstCommand));

        // same values -> returns true
        ReminderCommand findFirstCommandCopy = new ReminderCommand(firstPredicate);
        assertTrue(reminderFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(reminderFirstCommand.equals(1));

        // null -> returns false
        assertFalse(reminderFirstCommand.equals(null));

        // different days -> returns false
        assertFalse(reminderFirstCommand.equals(reminderSecondCommand));
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
        String expectedMessage = String.format(Messages.MESSAGE_FIlTER_OVERVIEW_DATE, 0);
        UpcomingInterviewPredicate predicate = preparePredicate(0, "01/02/2025");
        ReminderCommand command = new ReminderCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalPersons.BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroDays_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_FIlTER_OVERVIEW_DATE, 0);
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
        String expectedMessage = String.format(Messages.MESSAGE_FIlTER_OVERVIEW_DATE, 10);
        UpcomingInterviewPredicate predicate = preparePredicate(10, "22/01/2025");
        ReminderCommand command = new ReminderCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalPersons.BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_nonZeroDays_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_FIlTER_OVERVIEW_DATE, 10);
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
