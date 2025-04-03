package hirehive.address.model.person;

import hirehive.address.commons.util.ToStringBuilder;
import hirehive.address.logic.Messages;
import hirehive.address.model.tag.Tag;
import hirehive.address.model.person.Person;

/**
 * Tests that a Person's {@code tag}s do not match the given tag
 */
public class PersonDoesNotContainTagPredicate implements PersonPredicate {
    private final Tag tag;

    public PersonDoesNotContainTagPredicate(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .noneMatch(t -> t.equals(tag));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDoesNotContainTagPredicate)) {
            return false;
        }

        PersonDoesNotContainTagPredicate otherPersonDoesNotContainTagPredicate =
                (PersonDoesNotContainTagPredicate) other;
        return this.tag.equals(otherPersonDoesNotContainTagPredicate.tag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tag", this.tag).toString();
    }

    @Override
    public String getSuccessString() {
        return String.format(Messages.MESSAGE_FILTEROUT_OVERVIEW_TAG, tag);
    }
}
