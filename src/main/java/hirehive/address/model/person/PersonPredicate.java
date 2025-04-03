package hirehive.address.model.person;

import java.util.function.Predicate;

/**
 * Interface used for predicates that operate on the Person class
 */
public interface PersonPredicate extends Predicate<Person> {
    public String getSuccessString();
}
