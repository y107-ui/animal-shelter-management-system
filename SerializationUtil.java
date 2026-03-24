package Serial;

import java.io.*;
/**
 * Classe utilitaire pour la sérialisation et désérialisation d'objets.
 * Fournit des méthodes statiques pour sauvegarder et charger des objets
 * depuis des fichiers en utilisant la sérialisation Java standard.
 */
public class SerializationUtil {
	/**
     * Sauvegarde un objet dans un fichier via la sérialisation.
     * 
     * @param obj L'objet à sérialiser et sauvegarder
     * @param file Le chemin du fichier où sauvegarder l'objet
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de l'écriture
     *                     ou si le fichier ne peut pas être créé/ouvert
     */
    public static void save(Object obj, String file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(obj);
        }
    }
    /**
     * Charger un objet depuis un  fichier via la sérialisation.
     
     */
    @SuppressWarnings("unchecked")
    public static <T> T load(String file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (T) ois.readObject();
        }
    }
}
