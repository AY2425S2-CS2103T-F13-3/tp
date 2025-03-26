package hirehive.address.logic.commands.queries;

import java.util.List;
import java.util.function.Predicate;

import hirehive.address.logic.commands.queries.exceptions.QueryException;
import hirehive.address.model.Model;

/**
 * Represents a query with hidden predicate to query with
 */
public abstract class Query<T> {
    protected final Predicate<T> predicate;

    /**
     * @param predicate to query a model with to get a result
     */
    public Query(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    /**
     * Queries the model with specified predicate and returns a specific object instance based
     *
     * @param model {@code Model} which the query should operate on.
     * @return queried result from using the predicate
     * @throws QueryException If an error occurs during query.
     */
    public abstract List<T> query(Model model) throws QueryException;
}
