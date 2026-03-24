package exceptions;


/**
 * Exception racine du système AMS (Animal Management System)
 */
public class AMSException extends Exception {
    public AMSException(String message) {
        super(message);
    }
    
    public AMSException(String message, Throwable cause) {
        super(message, cause);
    }
}







