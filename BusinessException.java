package exceptions;

/**
 * Exception de base pour les règles métier spécifiques
 */
public class BusinessException extends AMSException {
    public BusinessException(String message) {
        super(message);
    }
}




