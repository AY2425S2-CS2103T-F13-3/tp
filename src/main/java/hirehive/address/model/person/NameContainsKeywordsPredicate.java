package hirehive.address.model.person;

import hirehive.address.commons.util.StringUtil;
import hirehive.address.commons.util.ToStringBuilder;
import hirehive.address.logic.Messages;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keyword given.
 */
public class NameContainsKeywordsPredicate implements PersonPredicate {
    private final String keyword;

    public NameContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keyword.equals(otherNameContainsKeywordsPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keyword).toString();
    }

    @Override
    public String getSuccessString() {
        return String.format(Messages.MESSAGE_FILTER_OVERVIEW_NAME, keyword);
    }
}
