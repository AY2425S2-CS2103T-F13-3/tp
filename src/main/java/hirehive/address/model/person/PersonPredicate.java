package hirehive.address.model.person;

import java.util.function.Predicate;

public interface PersonPredicate extends Predicate<Person> {
    public String getSuccessString();
}
