package hirehive.address.model.person;

import hirehive.address.commons.util.ToStringBuilder;
import hirehive.address.model.tag.Tag;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Tag}s matches the given Tag.
 */
public class PersonContainsTagPredicate implements Predicate<Person> {
    private final Tag tag;
    public PersonContainsTagPredicate(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .anyMatch(t -> t.equals(tag));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsTagPredicate)) {
            return false;
        }

        PersonContainsTagPredicate otherPersonContainsTagPredicate = (PersonContainsTagPredicate) other;
        return this.tag.equals(otherPersonContainsTagPredicate.tag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tag", this.tag).toString();
    }
}
