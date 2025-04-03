package hirehive.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false)));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different isChange value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true)));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, false, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, false, false)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different isChange value -> returns differet hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true));

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false, false, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true, false, false).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", showNote=" + commandResult.isShowNote()
                + ", isChange=" + commandResult.isChange() + "}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void isShowNote() {
        CommandResult commandResult1 = new CommandResult("feedback", false, false, true, false);
        CommandResult commandResult2 = new CommandResult("feedback");
        assertTrue(commandResult1.isShowNote());
        assertFalse(commandResult2.isShowNote());
    }

    @Test
    public void isChange() {
        CommandResult commandResult1 = new CommandResult("feedback");
        CommandResult commandResult2 = new CommandResult("feedback", true);

        // true isChange command result -> return true
        assertFalse(commandResult1.isChange());

        // false isChange command result -> return false
        assertTrue(commandResult2.isChange());
    }

}
