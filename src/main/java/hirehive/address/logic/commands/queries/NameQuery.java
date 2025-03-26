package hirehive.address.logic.commands.queries;

import static hirehive.address.logic.Messages.MESSAGE_NO_SUCH_PERSON;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import hirehive.address.logic.commands.queries.exceptions.QueryException;
import hirehive.address.model.Model;
import hirehive.address.model.person.Person;

/**
 * Queries for a person by a given name.
 */
public class NameQuery extends Query<Person> {
    /**
     * @param predicate to query a model with to get a person
     */
    public NameQuery(Predicate<Person> predicate) {
        super(predicate);
    }

    @Override
    public Person query(Model model) throws QueryException {
        model.updateFilteredPersonList(this.predicate);
        List<Person> filteredList = model.getFilteredPersonList();
        if (filteredList.isEmpty()) {
            throw new QueryException(MESSAGE_NO_SUCH_PERSON);
        }
        return filteredList.get(0);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        NameQuery nameQuery = (NameQuery) other;
        return Objects.equals(this.predicate, nameQuery.predicate);
    }
}
