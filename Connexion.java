package connexion;

import exceptions.ConnexionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
/**
* Classe qui lance et ferme une connexion à la base de données 
 */
public class Connexion {

    public static Connection connect() throws ConnexionException {
        Connection connection = null;

        try {
            // Charger le driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ConnexionException("Driver PostgreSQL non trouvé", e);
        }

        try {
            // Configurations
            String url = "jdbc:postgresql://pedago.univ-avignon.fr:5432/etd";
            Properties props = new Properties();
            props.setProperty("user", "uapv2500276");
            props.setProperty("password", "261005");

            // Connexion
            connection = DriverManager.getConnection(url, props);
            System.out.println("✓ Connexion à la base de données  réussie !");
            
        } catch (Exception e) {
            throw new ConnexionException("Impossible de se connecter à la base de données", e);
        }

        return connection;
    }

    public static void close(Connection cnx) throws ConnexionException {
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("✓ Connexion fermée.");
            }
        } catch (Exception e) {
            throw new ConnexionException("Erreur lors de la fermeture de la connexion", e);
        }
    }
}
