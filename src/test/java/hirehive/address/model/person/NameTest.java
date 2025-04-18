package hirehive.address.model.person;

import static hirehive.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hirehive.address.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertFalse(Name.isValidName("12345")); // numbers only
        assertFalse(Name.isValidName("/rachel.")); // Symbol at the start

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr Second")); // long names
        assertTrue(Name.isValidName("Rachel O'Connor")); //apostrophe
        assertTrue(Name.isValidName("R@chel")); // commercial at
        assertTrue(Name.isValidName("Rachel s/o Connor")); // slash
        assertTrue(Name.isValidName("Rachel (XinYing)")); // brackets
        assertTrue(Name.isValidName("Lee, Rachel")); // comma
        assertTrue(Name.isValidName("Rachel-Natalie")); // hyphen
        assertTrue(Name.isValidName("Lee. Rachel.")); // full stop
        assertTrue(Name.isValidName("R@c/hel, Wa()l-ker."));
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
