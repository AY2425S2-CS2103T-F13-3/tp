package hirehive.address.model.person;

import static java.util.Objects.requireNonNull;

import hirehive.address.commons.util.AppUtil;

/**
 * Represents a Person's note in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS = "Notes can take any values, but are limited to 500 characters";
    public static final String DEFAULT_NOTE = "";

    public final String value;

    /**
     * Constructs an {@code Notes}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        AppUtil.checkArgument(isValidNote(note), MESSAGE_CONSTRAINTS);
        value = note;
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        return test.length() <= 500;
    }

    /**
     * Returns true if the note is empty.
     */
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Note)) {
            return false;
        }

        Note otherNote = (Note) other;
        return value.equals(otherNote.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
