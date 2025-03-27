package hirehive.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.commands.DateCommand;
import hirehive.address.testutil.Assert;

public class InterviewDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new InterviewDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "01-01-2004";
        Assert.assertThrows(IllegalArgumentException.class, () -> new InterviewDate(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> InterviewDate.isValidDate(null));

        // invalid date
        assertFalse(InterviewDate.isValidDate(" ")); // spaces only
        assertFalse(InterviewDate.isValidDate("abcde12345")); // only alphanumeric characters
        assertFalse(InterviewDate.isValidDate("01022004")); // no / separators
        assertFalse(InterviewDate.isValidDate("01-02-2004")); // wrong separators
        // valid date
        assertTrue(InterviewDate.isValidDate("01/02/2004"));
        // empty string
        assertTrue(InterviewDate.isValidDate(""));
    }

    @Test
    public void equals() {
        InterviewDate date = new InterviewDate("01/01/2025");

        // same values -> returns true
        assertTrue(date.equals(new InterviewDate("01/01/2025")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new InterviewDate("01/02/2025")));
    }
}
