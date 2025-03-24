package hirehive.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hirehive.address.testutil.Assert;

public class NoteTest {

    private final String invalidNote = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo"
            + " ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes,"
            + " nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla"
            + " consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim"
            + " justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium."
            + " Integer tincidunt. Cras dapibus";

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Note(invalidNote));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Note.isValidNote(null));

        // invalid name
        assertFalse(Note.isValidNote(invalidNote)); // >500 characters

        // valid name
        assertTrue(Note.isValidNote("Very smart")); // alphabets only
        assertTrue(Note.isValidNote("!@#$%^&*()_+")); // mix of symbols
    }

    @Test
    public void equals() {
        Note note = new Note("Valid");

        // same values -> returns true
        assertTrue(note.equals(new Note("Valid")));

        // same object -> returns true
        assertTrue(note.equals(note));

        // null -> returns false
        assertFalse(note.equals(null));

        // different types -> returns false
        assertFalse(note.equals(5.0f));

        // different values -> returns false
        assertFalse(note.equals(new Note("Also valid")));
    }
}
