package hirehive.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.parser.ParserUtil;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.tag.Tag;
import hirehive.address.testutil.PersonBuilder;

public class PersonContainsTagPredicateTest {
    @Test
    public void equals() {
        Tag firstPredicateTag = Tag.APPLICANT;
        Tag secondPredicateTag = Tag.CANDIDATE;

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
    public void test_tagContainsKeywords_returnsTrue() throws ParseException {
        // One Tag
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate(ParserUtil.parseTag("applicant"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("applicant").build()));

        // Mixed-case tag
        predicate = new PersonContainsTagPredicate(ParserUtil.parseTag("Applicant"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("applicant").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() throws ParseException {
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate(ParserUtil.parseTag("applicant"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("rejected").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonContainsTagPredicate(ParserUtil.parseTag("applicant"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("rejected").build()));
    }

    @Test
    public void toStringMethod() {
        Tag tag = Tag.APPLICANT;
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate(tag);

        String expected = PersonContainsTagPredicate.class.getCanonicalName() + "{tag=" + tag + "}";
        assertEquals(expected, predicate.toString());
    }
}
