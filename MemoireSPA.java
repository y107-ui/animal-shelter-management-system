package Serial;

import data.Animal;

import data.Box;
import data.Famille;
import data.Soin;

import java.io.File;
import java.util.ArrayList;
/**
 * Classe de gestion de la persistance des données de la SPA (Société Protectrice des Animaux).
 * Gère la sauvegarde et le chargement de toutes les entités du système dans des fichiers sérialisés.
 */
public class MemoireSPA {

    private static final String SAVE_DIR = "saves";
    /** Chemins des fichiers de sauvegarde pour chaque type d'entité */
    private static final String FILE_ANIMAUX   = SAVE_DIR + "/animaux.ser";
    private static final String FILE_FAMILLES  = SAVE_DIR + "/familles.ser";
    private static final String FILE_BOXS      = SAVE_DIR + "/boxs.ser";
    private static final String FILE_SOINS     = SAVE_DIR + "/soins.ser";
    

    /** Sauvegarde toutes les listes dans des fichiers .ser */
    public static void save(ArrayList<Animal> animaux,
                            ArrayList<Famille> familles,
                            ArrayList<Box> boxs,
                            ArrayList<Soin> soins
                            ) throws Exception {

        new File(SAVE_DIR).mkdirs();

        SerializationUtil.save(animaux,   FILE_ANIMAUX);
        SerializationUtil.save(familles,  FILE_FAMILLES);
        SerializationUtil.save(boxs,      FILE_BOXS);
        SerializationUtil.save(soins,     FILE_SOINS);
       
    }

    /** Charge toutes les listes (si fichier absent => liste vide) */
    public static Donnees loadAll() throws Exception {
        Donnees d = new Donnees();

        d.animaux   = loadList(FILE_ANIMAUX);
        d.familles  = loadList(FILE_FAMILLES);
        d.boxs      = loadList(FILE_BOXS);
        d.soins     = loadList(FILE_SOINS);
       

        // Si tes entités ont des champs transient (map/values), on reconstruit :
        for (Animal a : d.animaux) a.getStruct();
        for (Famille f : d.familles) f.getStruct();
        for (Box b : d.boxs) b.getStruct();
        for (Soin s : d.soins) s.getStruct();
   

        return d;
    }

    /** Conteneur des 4 listes */
    public static class Donnees {
        public ArrayList<Animal> animaux = new ArrayList<>();
        public ArrayList<Famille> familles = new ArrayList<>();
        public ArrayList<Box> boxs = new ArrayList<>();
        public ArrayList<Soin> soins = new ArrayList<>();
      
    }
    /**
     * Méthode utilitaire pour charger une liste depuis un fichier sérialisé.
     * Si le fichier n'existe pas, retourne une nouvelle liste vide.
     
    
     */
    @SuppressWarnings("unchecked")
    private static <T> ArrayList<T> loadList(String file) throws Exception {
        File f = new File(file);
        if (!f.exists()) return new ArrayList<>();
        return SerializationUtil.load(file);
    }
}
