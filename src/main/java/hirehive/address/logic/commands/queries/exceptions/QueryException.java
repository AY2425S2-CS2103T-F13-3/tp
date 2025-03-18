package hirehive.address.logic.commands.queries.exceptions;

/**
 * Represents an error encountered by from a query.
 */
public class QueryException extends Exception {
    public QueryException(String message) {
        super(message);
    }

    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
