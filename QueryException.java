package exceptions;

/**
 * Exception de base pour les problèmes liés aux requêtes SQL
 */
public class QueryException extends AMSException {
    public QueryException(String message) {
        super(message);
    }
    
    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }
}









