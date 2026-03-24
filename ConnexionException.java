package exceptions;
/**
 * Exception de base pour les problèmes de connexion à la base de données
 */
public class ConnexionException extends AMSException {
   
    
    public ConnexionException(String message) {
        super(message);
    }
    
    public ConnexionException(String message, Throwable cause) {
        super(message, cause);
    }
}



