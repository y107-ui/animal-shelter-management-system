package exceptions;

/**
 * Exception de base pour les problèmes liés aux données
 */
public class DataException extends AMSException {
    public DataException(String message) {
        super(message);
    }
    
    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}


