package connexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import exceptions.ConnexionException;

/**
 * Classe qui lance et ferme une connexion à la base de données.
 */
public class Connexion {

    private static final String CONFIG_PATH = "config/database.properties";

    public static Connection connect() throws ConnexionException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ConnexionException("Driver PostgreSQL non trouvé", e);
        }

        Properties config = new Properties();

        try (FileInputStream input = new FileInputStream(CONFIG_PATH)) {
            config.load(input);

            String url = config.getProperty("db.url");
            String user = config.getProperty("db.user");
            String password = config.getProperty("db.password");

            if (url == null || user == null || password == null) {
                throw new ConnexionException("Configuration de base de données incomplète.");
            }

            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);

            Connection connection = DriverManager.getConnection(url, props);
            System.out.println("✓ Connexion à la base de données réussie !");
            return connection;

        } catch (IOException e) {
            throw new ConnexionException("Fichier de configuration introuvable : " + CONFIG_PATH, e);
        } catch (Exception e) {
            throw new ConnexionException("Impossible de se connecter à la base de données", e);
        }
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