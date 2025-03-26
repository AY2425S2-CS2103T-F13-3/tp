package hirehive.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import hirehive.address.model.tag.Tag;
import hirehive.address.testutil.PersonBuilder;

public class PersonContainsTagPredicateTest {
    @Test
    public void equals() {
        Tag firstPredicateTag = new Tag("first");
        Tag secondPredicateTag = new Tag("second");

        PersonContainsTagPredicate firstPredicate = new PersonContainsTagPredicate(firstPredicateTag);
        PersonContainsTagPredicate secondPredicate = new PersonContainsTagPredicate(secondPredicateTag);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsTagPredicate firstPredicateCopy = new PersonContainsTagPredicate(firstPredicateTag);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One Tag
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate(new Tag("applicant"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("applicant").build()));

        // Mixed-case tag
        predicate = new PersonContainsTagPredicate(new Tag("Applicant"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("applicant").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate(new Tag("applicant"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("rejected").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonContainsTagPredicate(new Tag("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("rejected").build()));
    }

    @Test
    public void toStringMethod() {
        Tag tag = new Tag("test");
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate(tag);

        String expected = PersonContainsTagPredicate.class.getCanonicalName() + "{tag=" + tag + "}";
        assertEquals(expected, predicate.toString());
    }
}
