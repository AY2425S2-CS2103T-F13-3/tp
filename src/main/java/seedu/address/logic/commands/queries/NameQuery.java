package seedu.address.logic.commands.queries;

import static seedu.address.logic.Messages.MESSAGE_NO_SUCH_PERSON;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.queries.exceptions.QueryException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

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
}
